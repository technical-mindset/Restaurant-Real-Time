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
import java.util.Optional;

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


    /** Add & Update Category **/
    public ItemOrderDTO addUpdate(ItemOrderDTO dto, long id){
        ItemOrder itemOrder;
        Optional<ItemOrder> optionalItemOrder = this.repository.findById(dto.getId());

        /** for add case */
        if (dto.getId() == 0 && optionalItemOrder.isEmpty()) {
            // adding | updating new Item-Order for existing Order
            dto.setCreatedAt(LocalDateTime.now());
            dto.setCreatedBy(this.getUserName());
        }
        /** for update case */
        else if (dto.getId() > 0) {
            if (optionalItemOrder.isPresent()) {
                itemOrder = optionalItemOrder.get();

                // adding | updating new Item-Order for existing Order
                dto.setCreatedAt(itemOrder.getCreatedAt());
                dto.setCreatedBy(itemOrder.getCreatedBy());
            }
            else {
                throw new ResourceNotFound("Item Order", "Id", dto.getId());
            }
        }

        dto.setOrder(id);
        itemOrder = this.mapDtoToEntity(dto);

        /** Save the entity (either new or updated) and return the DTO */
        ItemOrder savedItemOrder = this.repository.save(itemOrder);
        return this.mapEntityToDto(savedItemOrder);
    }


    public void deleteItemOrder(long orderId, long id){
        ItemOrder itemOrder = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Item-Order", "Id", id));

        if (itemOrder.getOrder().getId() == orderId) { // confirmation that it contains the same orderId which they belongs too.
            this.repository.deleteById(id);
        }
        else {
            throw new ResourceNotFound("Item-Order", "Id", id);
        }
    }

    // Delete case would be executing from Order service (through order)
    // Get All Item-Order case would be fetched from Order service


    // ------------- Override methods ------------------
    public ItemOrderDTO mapEntityToDto(ItemOrder entity) {
        ItemOrderDTO dto = new ItemOrderDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setOrder(entity.getOrder().getId());
        dto.setItem(entity.getItem().getId());

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
        entity.setPrice(item.getPrice() * dto.getQuantity()); // setting the total price of each item-order
        entity.setOrder(order);

        if (dto.getId() == 0) {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setCreatedBy(this.getUserName());
        }

        entity.setUpdatedBy(this.getUserName());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }
}
