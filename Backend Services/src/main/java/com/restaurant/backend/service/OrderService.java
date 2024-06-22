package com.restaurant.backend.service;

import com.restaurant.backend.dao.SaleReportRepository;
import com.restaurant.backend.dao.OrderRepository;
import com.restaurant.backend.dao.TableSittingRepository;
import com.restaurant.backend.exception.ResourceExist;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.helper.PaginationResponse;
import com.restaurant.backend.model.*;
import com.restaurant.backend.payloads.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    @Autowired
    private SaleReportRepository spr;

    public OrderService(OrderRepository repository) {
        super(repository);
    }

    // ---------------------------------------------------------------------------------------
    /** This method is used for only generating the daily Report and Email */
    // Get All Order of 24-hours
    public PaginationResponse getOrdersAccordingToDate(int date, int pageNumber, int pageSize, String sortBy) {
        LocalDateTime localDateTime = LocalDateTime.now();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page page = this.repository.findAllByCreatedAtBetween(localDateTime.minusDays(date), localDateTime, pageable);
        List<Order> entities = page.getContent();

        PaginationResponse paginationResponse = new PaginationResponse();

        paginationResponse.setData(entities
                .stream()
                .map(e -> this.mapEntityToDto(e))
                .collect(Collectors.toList()));


        paginationResponse.setPageNumber(page.getNumber());
        paginationResponse.setPageSize(page.getSize());
        paginationResponse.setTotalElements(page.getTotalElements());
        paginationResponse.setTotalPages(page.getTotalPages());
        paginationResponse.setLastPage(page.isLast());

//        List<Order> orders = this.repository.findAllByCreatedAtBetween(localDateTime.minusDays(date), localDateTime);
//        List<Object> list = new ArrayList<>();
//        list.add(orders);

        // converting LocalDateTime into Date
//        list.add("Start-Date: "+ Date.from(localDateTime.minusDays(100).atZone(ZoneId.systemDefault()).toInstant()));
//        list.add("End-Date: "+ Date.from(localDateTime.minusDays(100).atZone(ZoneId.systemDefault()).toInstant()));

        return paginationResponse;
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

        List<ItemOrderDTO> itemOrderDTOS = null;
        List<DealOrderDTO> dealOrderDTOS = null;

        if (dto.getItemOrder() != null) {
            itemOrderDTOS = dto.getItemOrder()
                    .stream()
                    .map(e->this.itemOrderService.addItemOrder(e, entity.getId()))
                    .collect(Collectors.toList());

        }

        if (dto.getDealOrder() != null) {
            dealOrderDTOS = dto.getDealOrder()
                    .stream()
                    .map(e->this.dealOrderService.addDealOrder(e, entity.getId()))
                    .collect(Collectors.toList());
        }

        OrderDTO orderDTO0 = this.mapEntityToDto(entity);
        return  new CompileOrderDTO(orderDTO0.getId(), orderDTO0.getBill(), orderDTO0.getTableSitting(),
                itemOrderDTOS, dealOrderDTOS);
    }

    // Update case
    public OrderDTO updateOrder(CompileOrderDTO dto){
        System.out.println("========= In Order Update");
        System.out.println("========= In Order Update");
        System.out.println("========= In Order Update");

        Order order = this.repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFound("Order", "'Id'", dto.getId()));

        OrderDTO orderDTO = new OrderDTO(dto.getId(), dto.getBill(), dto.getTableSitting());
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setCreatedBy(order.getCreatedBy());

        Order order0 = this.mapDtoToEntity(orderDTO);
        Order entity = this.repository.save(order0);

        if (dto.getItemOrder() != null)
//        List<ItemOrderDTO> itemOrder =
                dto.getItemOrder()
                .stream()
                .map(e->this.itemOrderService.updateItemOrder(e, entity.getId()))
                .collect(Collectors.toList());

        if (dto.getDealOrder() != null)
//            List<DealOrderDTO> dealOrder =
                    dto.getDealOrder()
                    .stream()
                    .map(e->this.dealOrderService.updateDealOrder(e, entity.getId()))
                    .collect(Collectors.toList());


        return this.mapEntityToDto(entity);
    }

    // Delete case
    public void deleteCompleteOrder(long id) {
        Order order = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Order",
                        "Id", id));
        this.repository.delete(order);
    }

    // For deleting single orders related to item or deal
    public void deleteOrder(long orderId, long oid, long did){
        if (oid > 0) {
            this.itemOrderService.deleteItemOrder(orderId, oid);
        }
        if (did > 0) {
            this.dealOrderService.deleteDealOrder(orderId, did);
        }
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
        // if the table already reserved
        if (ts.isReserved()) {
            throw new ResourceExist("Table-Reserved", "Id", dto.getTableSitting());
        }
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
