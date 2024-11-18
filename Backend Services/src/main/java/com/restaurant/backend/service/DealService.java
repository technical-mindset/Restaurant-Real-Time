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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DealService extends BaseService<Deal, DealDTO, DealRepository> {
    @Autowired
    private ItemRepository itemRepository;

    public DealService(DealRepository repository) {
        super(repository);
    }


    /** Add & Update Item */
    public DealDTO addDeal(DealDTO dealDTO){
        Deal deal;
        Optional<Deal> dealByName = this.repository.findByName(dealDTO.getName());

        if (dealDTO.getId() == 0 && dealByName.isPresent()) {
            throw new ResourceExist("Deal", "name", dealDTO.getName());
        }
        // If ID is provided (indicating an update), check if the entity exists
        else if (dealDTO.getId() > 0) {
            deal = this.repository.findById(dealDTO.getId())
                    .orElseThrow(() -> new ResourceNotFound("Deal", "Id", dealDTO.getId()));

            /** for handling the unique or same name case */
            if (dealByName.isPresent() && dealByName.get().getId() != deal.getId())
                throw new ResourceExist("Deal", "name", dealDTO.getName());

            dealDTO.setCreatedAt(deal.getCreatedAt());
            dealDTO.setCreatedBy(deal.getCreatedBy());
        }

        deal = this.mapDtoToEntity(dealDTO);

        Deal deal0 = this.repository.save(deal);
        return  this.mapEntityToDto(deal0);

    }


    // Get All Pageable Item Categories
    public PaginationResponse getAllDeals(int pageNumber, int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return this.pageToPagination(pageable);
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

        /** For Calculating the actual price of deal with respect to its items. */
        double totalSum = 0;
        for (Item i: items){
            totalSum += i.getPrice();
        }

        entity.setActualPrice(totalSum);
        entity.setItems(items);

        if (dto.getId() == 0) {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setCreatedBy(this.getUserName());
        }

        entity.setUpdatedBy(this.getUserName());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }
}
