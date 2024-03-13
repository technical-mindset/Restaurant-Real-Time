package com.restaurant.backend.service;


import com.restaurant.backend.dao.ItemRepository;
import com.restaurant.backend.model.Item;
import com.restaurant.backend.payloads.ItemDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Add Item
    public ItemDTO addItem(ItemDTO ItemDTO){
        ItemDTO.setCreatedBy("Zaidi");
        ItemDTO.setUpdatedBy("Asad Zaidi");
        ItemDTO.setCreatedAt(LocalDateTime.now());
        ItemDTO.setUpdatedAt(LocalDateTime.now());

        Item Item = this.modelMapper.map(ItemDTO, Item.class);
        Item Item1 = itemRepository.save(Item);
        ItemDTO ItemDTO1 = this.modelMapper.map(Item1, ItemDTO.class);

        return  ItemDTO1;
    }
}
