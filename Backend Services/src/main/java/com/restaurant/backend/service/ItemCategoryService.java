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
    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryService(ItemCategoryRepository repository) {
        super(repository);
    }

    // Add Category
    public ItemCategoryDTO addCategory(ItemCategoryDTO itemCategoryDTO){

        if (this.itemCategoryRepository.findById(itemCategoryDTO.getId()).isPresent() ) {
            throw new ResourceExist("Item Category","Id", itemCategoryDTO.getId());
        }
        else if (this.itemCategoryRepository.findByName(itemCategoryDTO.getName()).isPresent() ) {
            throw new ResourceExist("Item Category","Name", itemCategoryDTO.getName());
        }
        ItemCategory itemCategory = this.mapDtoToEntity(itemCategoryDTO);

        ItemCategory itemCategory0 = itemCategoryRepository.save(itemCategory);
        return this.mapEntityToDto(itemCategory0);
    }


    // Get All Pageable Item Categories
    public PaginationResponse getAllCategories(int pageNumber, int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page page = this.itemCategoryRepository.findAll(pageable);
        List<ItemCategory> itemCategories = page.getContent();

        return this.pageToPagination(itemCategories, page);
    }


    // Update category case
    public ItemCategoryDTO updateCategory(ItemCategoryDTO itemCategoryDTO){

        ItemCategory itemCategory = this.itemCategoryRepository.findById(itemCategoryDTO.getId())
                .orElseThrow(() -> new ResourceNotFound("Item Category", "'Item Id'", itemCategoryDTO.getId()));

        itemCategoryDTO.setCreatedAt(itemCategory.getCreatedAt());
        itemCategoryDTO.setCreatedBy(itemCategory.getCreatedBy());

        ItemCategory itemCategory0 = this.mapDtoToEntity(itemCategoryDTO);
        ItemCategory itemCategory00 = this.itemCategoryRepository.save(itemCategory0);

        return this.mapEntityToDto(itemCategory00);

    }


    // Delete category case
    public void deleteCategory(ItemCategoryDTO itemCategoryDTO){
        ItemCategory itemCategory = this.itemCategoryRepository
                .findById(itemCategoryDTO.getId())
                .orElseThrow(() -> new ResourceNotFound("Item Category",
                        "'Item Id'", itemCategoryDTO.getId()));
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

        if (dto.getId() > 0) {
            entity.setCreatedAt(dto.getCreatedAt());
            entity.setCreatedBy(dto.getCreatedBy());
            entity.setUpdatedBy("Zaidi");
        }
        else {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setCreatedBy("Ali Akbar");
            entity.setUpdatedBy("Ali Akbar");

        }
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }
}
