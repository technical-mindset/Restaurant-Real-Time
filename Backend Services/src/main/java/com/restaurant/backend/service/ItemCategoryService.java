package com.restaurant.backend.service;

import com.restaurant.backend.dao.ItemCategoryRepository;
import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.helper.PaginationResponse;
import com.restaurant.backend.model.Item_category;
import com.restaurant.backend.payloads.ItemCategoryDTO;
import com.restaurant.backend.utils.Constants;
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
public class ItemCategoryService {
    @Autowired
    private ItemCategoryRepository itemCategoryRepository;
    @Autowired
    ModelMapper modelMapper;

    // Add Category
    public ItemCategoryDTO addCategory(ItemCategoryDTO itemCategoryDTO){
        Item_category itemCategory = this.modelMapper.map(itemCategoryDTO, Item_category.class);
        itemCategory.setCreatedBy("Zaidi");
        itemCategory.setUpdatedBy("Asad Zaidi");
        itemCategory.setCreatedAt(LocalDateTime.now());
        itemCategory.setUpdatedAt(LocalDateTime.now());
        Item_category itemCategory1 = itemCategoryRepository.save(itemCategory);
        ItemCategoryDTO itemCategoryDTO1 = this.modelMapper.map(itemCategory1, ItemCategoryDTO.class);
        return  itemCategoryDTO1;
    }

    // Get All Pageable Item Categories
    public PaginationResponse getAllCategories(int pageNumber, int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page page = this.itemCategoryRepository.findAll(pageable);
        List<Item_category> itemCategories = page.getContent();

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
}
