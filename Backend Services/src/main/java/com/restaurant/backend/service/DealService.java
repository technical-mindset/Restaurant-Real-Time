package com.restaurant.backend.service;

import com.restaurant.backend.dao.DealRepository;
import com.restaurant.backend.dao.ItemRepository;
import com.restaurant.backend.exception.ResourceExist;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.helper.PaginationResponse;
import com.restaurant.backend.model.Deal;
import com.restaurant.backend.model.Item;
import com.restaurant.backend.payloads.DealDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DealService extends BaseService<Deal, DealDTO, DealRepository> {
    @Autowired
    private ItemRepository itemRepository;

    public DealService(DealRepository repository) {
        super(repository);
    }


    // Add case
    public DealDTO addDeal(DealDTO dealDTO){

        if (this.repository.findById(dealDTO.getId()).isPresent()) {
            throw new ResourceExist("Deal", "Id", dealDTO.getId());
        }
        else if (this.itemRepository.findByName(dealDTO.getName()).isPresent()) {
            throw new ResourceExist("deal", "name", dealDTO.getName());
        }
        Deal deal = this.mapDtoToEntity(dealDTO);

        Deal deal0 = this.repository.save(deal);
        return  this.mapEntityToDto(deal0);

    }


    // Update case
    public DealDTO updateDeal(DealDTO dealDTO){

        Deal deal = this.repository.findById(dealDTO.getId())
                .orElseThrow(() -> new ResourceNotFound("Deal", "'deal Id'", dealDTO.getId()));

        dealDTO.setCreatedAt(deal.getCreatedAt());
        dealDTO.setCreatedBy(deal.getCreatedBy());

        Deal deal0 = this.mapDtoToEntity(dealDTO);
        Deal deal00 = this.repository.save(deal0);

        return this.mapEntityToDto(deal00);
    }


    // Get All Pageable Item Categories
    public PaginationResponse getAllDeals(int pageNumber, int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page page = this.repository.findAll(pageable);
        List<Deal> deals = page.getContent();

        return this.pageToPagination(deals, page);
    }


    // Delete item
    public void deleteDeal(long id){
        Deal deal = this.repository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Deal",
                        "Deal Id", id));
        this.repository.delete(deal);
    }



    // -------------- Over Ride Methods -------------------
    @Override
    public DealDTO mapEntityToDto(Deal entity) {

        DealDTO dto = new DealDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setItems(entity.getItems()
                .stream()
                .map(e-> e.getId())
                .collect(Collectors.toList()));

        return dto;
    }

    @Override
    public Deal mapDtoToEntity(DealDTO dto) {
        Deal entity = new Deal();
        BeanUtils.copyProperties(dto, entity);

        List<Item> items = this.itemRepository.findAllById(dto.getItems());

        if (items.isEmpty()) {
            throw new ResourceNotFound("Items", "Ids", dto.getItems());
        }
        entity.setItems(items);

        if (dto.getId() > 0) {
            entity.setCreatedAt(dto.getCreatedAt());
            entity.setCreatedBy(dto.getCreatedBy());
        }
        else {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setCreatedBy(this.getUserName());
        }

        entity.setUpdatedBy(this.getUserName());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }
}
