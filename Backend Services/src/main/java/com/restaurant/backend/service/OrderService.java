package com.restaurant.backend.service;

import com.restaurant.backend.dao.OrderRepository;
import com.restaurant.backend.dao.TableSittingRepository;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.model.Item;
import com.restaurant.backend.model.Order;
import com.restaurant.backend.model.TableSitting;
import com.restaurant.backend.payloads.ItemDTO;
import com.restaurant.backend.payloads.OrderDTO;
import com.restaurant.backend.utils.Constants;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    // Get All Order of 24-hours
    public ApiResponse getOrderOf_24hrs() {
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Order> orders = this.orderRepository.findAllByCreatedAtBetween(localDateTime.minusDays(1), localDateTime);

        List<Object> list = new ArrayList<>();
        list.add(orders);

        // converting LocalDateTime into Date
        list.add("Start-Date: "+ Date.from(localDateTime.minusDays(1).atZone(ZoneId.systemDefault()).toInstant()));
        list.add("End-Date: "+ Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));

        ApiResponse response = new ApiResponse(
                Constants.MESSAGE_FETCHED,
                list,
                true
        );

        return response;
    }

    // Add case
    public OrderDTO addOrder(OrderDTO dto){
       Order order = this.mapDtoToEntity(dto);

       Order entity = this.orderRepository.save(order);
       return this.mapEntityToDto(entity);
    }

    // Update case
    public OrderDTO updateItem(OrderDTO dto){

        Order order = this.orderRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFound("Order", "'Id'", dto.getId()));

        dto.setCreatedAt(order.getCreatedAt());
        dto.setCreatedBy(order.getCreatedBy());

        Order order0 = this.mapDtoToEntity(dto);
        Order entity = this.orderRepository.save(order0);

        return this.mapEntityToDto(entity);
    }

    // Delete case
    public void deleteItem(long id){
        Order order = this.orderRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Order",
                        "Id", id));
        this.orderRepository.delete(order);
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
