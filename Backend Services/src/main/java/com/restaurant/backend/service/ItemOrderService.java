package com.restaurant.backend.service;


import com.restaurant.backend.dao.ItemOrderRepository;
import com.restaurant.backend.dao.ItemRepository;
import com.restaurant.backend.dao.OrderRepository;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.model.Item;
import com.restaurant.backend.model.ItemOrder;
import com.restaurant.backend.model.Order;
import com.restaurant.backend.payloads.ItemOrderDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Transactional
@Service
public class ItemOrderService extends BaseService<ItemOrder, ItemOrderDTO, ItemOrderRepository> {
    @Autowired
    private ItemOrderRepository repository;
    @Autowired
    private ItemRepository itemRepo;
    @Autowired
    private OrderRepository orderRepo;

    public ItemOrderService(ItemOrderRepository repository) {
        super(repository);
    }

    // Add Case










    // ------------- Override methods ------------------
    public ItemOrderDTO mapEntityToDto(ItemOrder entity) {
        ItemOrderDTO dto = new ItemOrderDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setOrder(entity.getOrder().getId());
        dto.setItem(dto.getItem());
        return dto;
    }

    public ItemOrder mapDtoToEntity(ItemOrderDTO dto) {
        ItemOrder entity = this.repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFound("Item-Order", "Id", dto.getId()));
        BeanUtils.copyProperties(dto, entity);

        Item item = this.itemRepo.findById(dto.getItem())
                .orElseThrow(() -> new ResourceNotFound("Item", "Id", dto.getItem()));
        Order order = this.orderRepo.findById(dto.getOrder())
                        .orElseThrow(() -> new ResourceNotFound("Order", "Id", dto.getItem()));


        entity.setItem(item);
        entity.setOrder(order);

        if (dto.getId() > 0) {
            entity.setCreatedAt(dto.getCreatedAt());
            entity.setCreatedBy(dto.getCreatedBy());
        }
        else {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setCreatedBy("Ali Akbar");
        }

        return entity;
    }
}
