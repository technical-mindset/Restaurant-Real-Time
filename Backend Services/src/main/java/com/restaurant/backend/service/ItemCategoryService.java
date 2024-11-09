package com.restaurant.backend.service;

import com.restaurant.backend.dao.ItemCategoryRepository;
import com.restaurant.backend.exception.ResourceExist;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.helper.PaginationResponse;
import com.restaurant.backend.model.ItemCategory;
import com.restaurant.backend.payloads.ItemCategoryDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class ItemCategoryService extends BaseService<ItemCategory, ItemCategoryDTO, ItemCategoryRepository>{

    public ItemCategoryService(ItemCategoryRepository repository) {
        super(repository);
    }

    /** Add & Update Category **/
    public ItemCategoryDTO addCategory(ItemCategoryDTO itemCategoryDTO) {
        ItemCategory itemCategory;

        if (itemCategoryDTO.getId() == 0 && this.repository.findByName(itemCategoryDTO.getName()).isPresent()) {
            throw new ResourceExist("Item Category", "Name", itemCategoryDTO.getName());
        }
        // If ID is provided (indicating an update), check if the entity exists
        else if (itemCategoryDTO.getId() > 0) {
            itemCategory = this.repository.findById(itemCategoryDTO.getId())
                    .orElseThrow(() -> new ResourceNotFound("Item Category", "Id", itemCategoryDTO.getId()));
            /** for handling the unique or same name case */
            ItemCategory categoryByName = this.repository.findByName(itemCategoryDTO.getName()).get();

            if (categoryByName.getId() != itemCategory.getId()) {
                throw new ResourceExist("Item Category", "Name", itemCategoryDTO.getName());
            }

            itemCategoryDTO.setCreatedAt(itemCategory.getCreatedAt());
            itemCategoryDTO.setCreatedBy(itemCategory.getCreatedBy());
        }

        itemCategory = this.mapDtoToEntity(itemCategoryDTO);

        /** Save the entity (either new or updated) and return the DTO */
        ItemCategory savedItemCategory = repository.save(itemCategory);
        return this.mapEntityToDto(savedItemCategory);
    }


    /** Get All Pageable Item Categories */
    public PaginationResponse getAllCategories(int pageNumber, int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return this.pageToPagination(pageable);
    }


    /** Delete category case */
    public void deleteCategory(long id){
        ItemCategory itemCategory = this.repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound("Item Category",
                        "'Item Id'", id));
        this.repository.delete(itemCategory);
    }

    @Override
    public ItemCategoryDTO mapEntityToDto(ItemCategory entity) {

        ItemCategoryDTO dto = new ItemCategoryDTO();
        BeanUtils.copyProperties(entity, dto);

        return dto;
    }

    @Override
    public ItemCategory mapDtoToEntity(ItemCategoryDTO dto) {

        ItemCategory entity = new ItemCategory();
        BeanUtils.copyProperties(dto, entity);

        if (dto.getId() == 0) {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setCreatedBy(this.getUserName());
        }

        entity.setUpdatedBy(this.getUserName());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }
}
