package com.restaurant.backend.service;

import com.restaurant.backend.dao.OrderRepository;
import com.restaurant.backend.dao.TableSittingRepository;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.model.Order;
import com.restaurant.backend.model.TableSitting;
import com.restaurant.backend.payloads.OrderDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Transactional
@Service
public class OrderService extends BaseService<Order, OrderDTO, OrderRepository>{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TableSittingRepository tableSittingRepository;
    public OrderService(OrderRepository repository) {
        super(repository);
    }















    // -------------- Over Ride Methods -------------------

    @Override
    public OrderDTO mapEntityToDto(Order entity) {
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setTableSitting(entity.getTableSitting().getId());
        return dto;
    }

    @Override
    public Order mapDtoToEntity(OrderDTO dto) {
        Order entity = this.orderRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFound("Order", "Id", dto.getId()));
        BeanUtils.copyProperties(dto, entity);

        TableSitting ts = this.tableSittingRepository.findById(dto.getTableSitting())
                .orElseThrow(() -> new ResourceNotFound("Table", "Id", dto.getTableSitting()));
        entity.setTableSitting(ts);

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
