package com.restaurant.backend.service;

import com.restaurant.backend.dao.RestaurantRepositroy;
import com.restaurant.backend.dao.TableSittingRepository;
import com.restaurant.backend.exception.ResourceExist;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.helper.PaginationResponse;
import com.restaurant.backend.model.Restaurant;
import com.restaurant.backend.model.TableSitting;
import com.restaurant.backend.payloads.TableSittingAdminDTO;
import com.restaurant.backend.payloads.TableSittingDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class TableSittingService extends BaseService<TableSitting, TableSittingAdminDTO, TableSittingRepository> {
    @Autowired
    private RestaurantRepositroy restaurantRepositroy;


    public TableSittingService(TableSittingRepository repository) {
        super(repository);
    }



    // Get All Tables for Normal and Admin case
    public PaginationResponse getAllTables(int pageNumber, int pageSize, String sortBy){

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page page = this.repository.findAll(pageable);
        List<TableSitting> tables = page.getContent();

       return this.pageToPagination(tables, page);
    }


    // Update case: for placing order
    public TableSittingDTO isReserved(TableSittingDTO dto){
        TableSitting tableSitting = this.repository.findById(dto.getId())
                .orElseThrow(()-> new ResourceNotFound("Table", "Id", dto.getId()));

        // setting the reserved flag as true or false
        tableSitting.setReserved(dto.isReserved());

        TableSittingDTO ts = new TableSittingDTO();
        BeanUtils.copyProperties(this.mapEntityToDto(tableSitting), ts); // converting the adminDto to normal Dto
        return ts;
    }


    // =========================== Methods for admin use only ===========================
    public TableSittingAdminDTO addTable(TableSittingAdminDTO dto){
        if (this.repository.findById(dto.getId()).isPresent()) {
            throw new ResourceExist("Table", "Id", dto.getId());
        }
        else if (this.repository.findByTableCode(dto.getTableCode()).isPresent()) {
            throw new ResourceExist("Table", "code", dto.getTableCode());
        }
        TableSitting table = this.mapDtoToEntity(dto);
        System.out.println(table.getTableCode());
        System.out.println(table.getRestaurant().getCity());

        TableSitting tableSitting = this.repository.save(table);
        return  this.mapEntityToDto(tableSitting);
    }


    // update case
    public TableSittingAdminDTO updateTable(TableSittingAdminDTO dto){
        if (this.repository.findByTableCode(dto.getTableCode()).isPresent()) {
            throw new ResourceExist("Table", "code", dto.getTableCode());
        }

        TableSitting tableSitting = this.repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFound("Table", "Id", dto.getId()));

        dto.setCreatedAt(tableSitting.getCreatedAt());
        dto.setCreatedBy(tableSitting.getCreatedBy());

        TableSitting table = this.mapDtoToEntity(dto);
        TableSitting sitting = this.repository.save(table);
        return this.mapEntityToDto(sitting);
    }


    // delete case
    public void deleteTable(long id){
        TableSitting tableSitting = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Table", "Id", id));

        this.repository.delete(tableSitting);
    }




    // -------------- Over Ride Methods -------------------

    @Override
    public TableSittingAdminDTO mapEntityToDto(TableSitting entity) {
        TableSittingAdminDTO dto = new TableSittingAdminDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setRestaurantId(entity.getRestaurant().getId());

        return dto;
    }

    @Override
    public TableSitting mapDtoToEntity(TableSittingAdminDTO dto) {
        TableSitting entity = new TableSitting();
        BeanUtils.copyProperties(dto, entity);

        Restaurant restaurant = this.restaurantRepositroy.findById(dto.getRestaurantId())
                .orElseThrow(()->new ResourceNotFound("Restaurant", "Id", dto.getRestaurantId()));
        entity.setRestaurant(restaurant);

        if (dto.getId() > 0) {
            entity.setCreatedAt(dto.getCreatedAt());
            entity.setCreatedBy(dto.getCreatedBy());
        }
        else {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setCreatedBy(this.getUserName());
            entity.setUpdatedBy(this.getUserName());

        }
        entity.setUpdatedBy(this.getUserName());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }


}
