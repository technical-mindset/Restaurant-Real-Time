package com.restaurant.backend.service;


import com.restaurant.backend.dao.ItemCategoryRepository;
import com.restaurant.backend.dao.ItemRepository;
import com.restaurant.backend.exception.ResourceExist;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.helper.PaginationResponse;
import com.restaurant.backend.model.Item;
import com.restaurant.backend.model.ItemCategory;
import com.restaurant.backend.payloads.ItemDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Transactional
public class ItemService extends BaseService<Item, ItemDTO, ItemRepository>{
    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    public ItemService(ItemRepository repository) {
        super(repository);
    }


    /** Add & Update Item */

    public ItemDTO addUpdate(ItemDTO dto) {
        Item item;
        Optional<Item> itemByName = this.repository.findByName(dto.getName());

        if (dto.getId() == 0 && itemByName.isPresent()) {
            throw new ResourceExist("Item", "Name", dto.getName());
        }
        // If ID is provided (indicating an update), check if the entity exists
        else if (dto.getId() > 0) {
            item = this.repository.findById(dto.getId())
                    .orElseThrow(() -> new ResourceNotFound("Item", "Id", dto.getId()));

            /** for handling the unique or same name case */
            if (itemByName.isPresent() && itemByName.get().getId() != item.getId()) {
                throw new ResourceExist("Item", "Name", dto.getName());
            }

            dto.setCreatedAt(item.getCreatedAt());
            dto.setCreatedBy(item.getCreatedBy());
        }

        item = this.mapDtoToEntity(dto);

        /** Save the entity (either new or updated) and return the DTO */
        Item savedItem = repository.save(item);
        return this.mapEntityToDto(savedItem);
    }


    // Get All Pageable Item Categories
    public PaginationResponse getAllItems(int pageNumber, int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return this.pageToPagination(pageable);
    }


    // Delete item
    public void deleteItem(long id){
        Item item = this.repository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Item",
                        "Item Id", id));
        this.repository.delete(item);
    }


    /**  --------------- Data mapping methods  -------------------  */
    @Override
    public ItemDTO mapEntityToDto(Item entity) {
        ItemDTO dto = new ItemDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setItemCategory(entity.getItemCategory().getId());
        return dto;
    }

    @Override
    public Item mapDtoToEntity(ItemDTO dto) {
        Item entity = new Item();
        BeanUtils.copyProperties(dto, entity);

        ItemCategory itemCategory = this.itemCategoryRepository.findById(dto.getItemCategory())
                .orElseThrow(() -> new ResourceNotFound("ItemCategory", "id", dto.getItemCategory()));
        entity.setItemCategory(itemCategory);

        if (dto.getId() == 0) {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setCreatedBy(this.getUserName());
        }

        entity.setUpdatedBy(this.getUserName());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }
}
