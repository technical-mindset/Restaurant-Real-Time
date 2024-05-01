package com.restaurant.backend.service;


import com.restaurant.backend.dao.ItemOrderRepository;
import com.restaurant.backend.dao.ItemRepository;
import com.restaurant.backend.dao.OrderRepository;
import com.restaurant.backend.exception.ResourceExist;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.model.Item;
import com.restaurant.backend.model.ItemOrder;
import com.restaurant.backend.model.Order;
import com.restaurant.backend.payloads.ItemDTO;
import com.restaurant.backend.payloads.ItemOrderDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class ItemOrderService extends BaseService<ItemOrder, ItemOrderDTO, ItemOrderRepository> {
    @Autowired
    private ItemRepository itemRepo;
    @Autowired
    private OrderRepository orderRepo;

    public ItemOrderService(ItemOrderRepository repository) {
        super(repository);
    }


    // Add Case
    public ItemOrderDTO addItemOrder(ItemOrderDTO dto, long id){
        if (this.repository.findById(dto.getId()).isPresent()) {
            throw new ResourceExist("Item-Order", "Id", dto.getId());
        }
        dto.setOrder(id);
        ItemOrder itemOrder = this.mapDtoToEntity(dto);

        ItemOrder itemOrder0 = this.repository.save(itemOrder);
        return this.mapEntityToDto(itemOrder0);
    }

    // Update case
    public ItemOrderDTO updateItemOrder(ItemOrderDTO dto, long id){
        dto.setOrder(id);

        if (this.repository.findById(dto.getId()).isPresent()) {
            // if someone edit the old item-Order
            ItemOrder itemOrder = this.repository.findById(dto.getId()).get();

            dto.setCreatedAt(itemOrder.getCreatedAt());
            dto.setCreatedBy(itemOrder.getCreatedBy());

            ItemOrder itemOrder0 = this.mapDtoToEntity(dto);
            ItemOrder itemOrderSave = this.repository.save(itemOrder0);

            return this.mapEntityToDto(itemOrderSave);
        }

        // either it adds new item-order into order
        return this.addItemOrder(dto, id);
    }

    // Delete case would be executing from Order service (through order)
    // Get All Item-Order case would be fetched from Order service


    // ------------- Override methods ------------------
    public ItemOrderDTO mapEntityToDto(ItemOrder entity) {
        ItemOrderDTO dto = new ItemOrderDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setOrder(entity.getOrder().getId());
        dto.setItem(dto.getItem());
        return dto;
    }

    public ItemOrder mapDtoToEntity(ItemOrderDTO dto) {
        ItemOrder entity = new ItemOrder();
        BeanUtils.copyProperties(dto, entity);

        Item item = this.itemRepo.findById(dto.getItem())
                .orElseThrow(() -> new ResourceNotFound("Item", "Id", dto.getItem()));
        Order order = this.orderRepo.findById(dto.getOrder())
                        .orElseThrow(() -> new ResourceNotFound("Order", "Id", dto.getOrder()));

        entity.setItem(item);
        entity.setOrder(order);

        if (dto.getId() > 0) {
            entity.setCreatedAt(dto.getCreatedAt());
            entity.setCreatedBy(dto.getCreatedBy());
        }
        else {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setCreatedBy(this.getUserName());
        }
        return entity;
    }
}
