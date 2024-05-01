package com.restaurant.backend.service;

import com.restaurant.backend.dao.OrderRepository;
import com.restaurant.backend.dao.TableSittingRepository;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.model.*;
import com.restaurant.backend.payloads.*;
import com.restaurant.backend.utils.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
public class OrderService extends BaseService<Order, OrderDTO, OrderRepository>{
    @Autowired
    private TableSittingRepository tableSittingRepository;
    @Autowired
    private ItemOrderService itemOrderService;
    @Autowired
    private DealOrderService dealOrderService;

    public OrderService(OrderRepository repository) {
        super(repository);
    }

    // ---------------------------------------------------------------------------------------
    /** This method is used for only generating the daily Report and Email */
    // Get All Order of 24-hours
    public List<OrderDTO> getOrderOf_24hrs() {
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Order> orders = this.repository.findAllByCreatedAtBetween(localDateTime.minusDays(1), localDateTime);

        List<Object> list = new ArrayList<>();
        list.add(orders);

        // converting LocalDateTime into Date
        list.add("Start-Date: "+ Date.from(localDateTime.minusDays(2).atZone(ZoneId.systemDefault()).toInstant()));
        list.add("End-Date: "+ Date.from(localDateTime.minusDays(1).atZone(ZoneId.systemDefault()).toInstant()));

        ApiResponse response = new ApiResponse(
                Constants.MESSAGE_FETCHED,
                list,
                true
        );
        return orders.stream().map(e->this.mapEntityToDto(e)).collect(Collectors.toList());
    }
    // ---------------------------------------------------------------------------------------


    // Get case
    public CompileOrderDTO getOrderById(long id){



        Order order = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Order", "Id", id));

        // Get all item orders
        List<ItemOrderDTO> itemOrderDTOS = order.getItemOrders()
                .stream()
                .map(e->this.itemOrderService.mapEntityToDto(e))
                .collect(Collectors.toList());

        // Get all deal orders
        List<DealOrderDTO> dealOrderDTOS = order.getDealOrders()
                .stream()
                .map(e->this.dealOrderService.mapEntityToDto(e))
                .collect(Collectors.toList());

        OrderDTO orderDTO = this.mapEntityToDto(order);
        return new CompileOrderDTO(orderDTO.getId(), orderDTO.getBill(), orderDTO.getTableSitting(), itemOrderDTOS, dealOrderDTOS);
    }


    // Add case
    public CompileOrderDTO addOrder(CompileOrderDTO dto){
        if (this.repository.findById(dto.getId()).isPresent()) {
            throw new ResourceNotFound("Order", "Id", dto.getId());
        }

        OrderDTO orderDTO = new OrderDTO(dto.getId(), dto.getBill(), dto.getTableSitting());
        Order order = this.mapDtoToEntity(orderDTO);
        Order entity = this.repository.save(order);


        List<ItemOrderDTO> itemOrderDTOS = dto.getItemOrder()
                .stream()
                .map(e->this.itemOrderService.addItemOrder(e, entity.getId()))
                .collect(Collectors.toList());

        List<DealOrderDTO> dealOrderDTOS = dto.getDealOrder()
                .stream()
                .map(e->this.dealOrderService.addDealOrder(e, entity.getId()))
                .collect(Collectors.toList());


        OrderDTO orderDTO0 = this.mapEntityToDto(entity);
        return  new CompileOrderDTO(orderDTO0.getId(), orderDTO0.getBill(), orderDTO0.getTableSitting(),
                itemOrderDTOS, dealOrderDTOS);
    }

    // Update case
    public OrderDTO updateOrder(CompileOrderDTO dto){

        Order order = this.repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFound("Order", "'Id'", dto.getId()));

        OrderDTO orderDTO = new OrderDTO(dto.getId(), dto.getBill(), dto.getTableSitting());
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setCreatedBy(order.getCreatedBy());

        Order order0 = this.mapDtoToEntity(orderDTO);
        Order entity = this.repository.save(order0);

        List<ItemOrderDTO> itemOrder = dto.getItemOrder()
                .stream()
                .map(e->this.itemOrderService.updateItemOrder(e, entity.getId()))
                .collect(Collectors.toList());

        List<DealOrderDTO> dealOrder = dto.getDealOrder()
                .stream()
                .map(e->this.dealOrderService.updateDealOrder(e, entity.getId()))
                .collect(Collectors.toList());

        return this.mapEntityToDto(entity);
    }

    // Delete case
    public void deleteOrder(long id) {
        Order order = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Order",
                        "Id", id));
        this.repository.delete(order);
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
        Order entity = new Order();
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
            entity.setCreatedBy(this.getUserName());
        }
        return entity;
    }
}
