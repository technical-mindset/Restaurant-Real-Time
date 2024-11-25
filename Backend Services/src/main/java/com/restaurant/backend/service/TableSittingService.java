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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
       return this.pageToPagination(pageable);
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


    /** Add & Update Table */
    public TableSittingAdminDTO addUpdate(TableSittingAdminDTO dto){
        TableSitting table;
        Optional<TableSitting> tableByCode = this.repository.findByTableCode(dto.getTableCode());

        if (dto.getId() == 0 && tableByCode.isPresent()) {
            throw new ResourceExist("Table", "Id", tableByCode.get().getId());
        }
        // If ID is provided (indicating an update), check if the entity exists
        else if (dto.getId() > 0) {
            table = this.repository.findById(dto.getId())
                    .orElseThrow(() -> new ResourceNotFound("Table", "Id", dto.getId()));;

            /** for handling the unique or same name case */
            if (tableByCode.isPresent() && tableByCode.get().getId() != table.getId()) {
                throw new ResourceExist("Table", "code", dto.getTableCode());
            }

            dto.setCreatedAt(table.getCreatedAt());
            dto.setCreatedBy(table.getCreatedBy());
        }

        table = this.mapDtoToEntity(dto);

        /** Save the entity (either new or updated) and return the DTO */
        TableSitting savedTable = this.repository.save(table);
        return  this.mapEntityToDto(savedTable);
    }


    // delete case
    public void deleteTable(long id){
        TableSitting tableSitting = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Table", "Id", id));

        this.repository.delete(tableSitting);
    }


    // -------------- Mapping methods -------------------

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

        Restaurant restaurant = this.restaurantRepositroy.findById((long) 1)
                .orElseThrow(()->new ResourceNotFound("Restaurant", "Id", dto.getRestaurantId()));
        entity.setRestaurant(restaurant);

        if (dto.getId() == 0) {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setCreatedBy(this.getUserName());
        }

        entity.setUpdatedBy(this.getUserName());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }


}
