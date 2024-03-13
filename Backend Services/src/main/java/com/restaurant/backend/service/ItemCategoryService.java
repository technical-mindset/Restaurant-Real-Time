package com.restaurant.backend.service;

import com.restaurant.backend.dao.ItemCategoryRepository;
import com.restaurant.backend.exception.ResourceExist;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.helper.PaginationResponse;
import com.restaurant.backend.model.ItemCategory;
import com.restaurant.backend.payloads.ItemCategoryDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
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
public class ItemCategoryService {
    @Autowired
    private ItemCategoryRepository itemCategoryRepository;
    @Autowired
    ModelMapper modelMapper;

    // Add Category
    public ItemCategoryDTO addCategory(ItemCategoryDTO itemCategoryDTO){
        
        if (this.itemCategoryRepository.findById(itemCategoryDTO.getId()).isPresent() ) {
            throw new ResourceExist("Item Category","Id", itemCategoryDTO.getId());
        }
        else if (this.itemCategoryRepository.findByName(itemCategoryDTO.getName()).isPresent() ) {
            throw new ResourceExist("Item Category","Name", itemCategoryDTO.getName());
        }
        ItemCategory itemCategory = this.modelMapper.map(itemCategoryDTO, ItemCategory.class);
        itemCategory.setCreatedBy("Zaidi");
        itemCategory.setUpdatedBy("Asad Zaidi");
        itemCategory.setCreatedAt(LocalDateTime.now());
        itemCategory.setUpdatedAt(LocalDateTime.now());
        ItemCategory itemCategory1 = itemCategoryRepository.save(itemCategory);
        ItemCategoryDTO itemCategoryDTO1 = this.modelMapper.map(itemCategory1, ItemCategoryDTO.class);
        return  itemCategoryDTO1;
    }

    // Get All Pageable Item Categories
    public PaginationResponse getAllCategories(int pageNumber, int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page page = this.itemCategoryRepository.findAll(pageable);
        List<ItemCategory> itemCategories = page.getContent();

        PaginationResponse paginationResponse = new PaginationResponse();

        paginationResponse.setData(itemCategories
                .stream()
                .map(e -> this.modelMapper.map(e, ItemCategoryDTO.class))
                .collect(Collectors.toList())
        );

        paginationResponse.setPageNumber(page.getNumber());
        paginationResponse.setPageSize(page.getSize());
        paginationResponse.setTotalElements(page.getTotalElements());
        paginationResponse.setTotalPages(page.getTotalPages());
        paginationResponse.setLastPage(page.isLast());

        return paginationResponse;

    }

    // Update category case
    public ItemCategoryDTO updateCategory(ItemCategoryDTO itemCategoryDTO){
        ItemCategory itemCategory = this.itemCategoryRepository.findById(itemCategoryDTO.getId())
                .orElseThrow(() -> new ResourceNotFound("Item Category", "'Item Id'", itemCategoryDTO.getId()));

        // converting the Model to DTO for update the changes in DTO
        ItemCategoryDTO itemCategoryDto = this.modelMapper.map(itemCategory, ItemCategoryDTO.class);
        itemCategoryDto.setName(itemCategoryDTO.getName());
        itemCategoryDto.setDescription(itemCategoryDTO.getDescription());
        itemCategoryDto.setUpdatedBy("Asad Zaidi");
        itemCategoryDto.setUpdatedAt(LocalDateTime.now());

        // converting the DTO to Model after updating the changes in DTO required
        ItemCategory item_category = this.modelMapper.map(itemCategoryDto, ItemCategory.class);
        return this.modelMapper.map(this.itemCategoryRepository.save(item_category), ItemCategoryDTO.class);

    }

    public void deleteCategory(ItemCategoryDTO itemCategoryDTO){
        ItemCategory itemCategory = this.itemCategoryRepository
                .findById(itemCategoryDTO.getId())
                .orElseThrow(() -> new ResourceNotFound("Item Category",
                        "'Item Id'", itemCategoryDTO.getId()));
    }
}
