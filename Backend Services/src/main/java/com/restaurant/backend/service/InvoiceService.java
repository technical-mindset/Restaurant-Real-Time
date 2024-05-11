package com.restaurant.backend.service;

import com.restaurant.backend.dao.InvoiceRepository;
import com.restaurant.backend.dao.OrderRepository;
import com.restaurant.backend.dao.UserRepository;
import com.restaurant.backend.exception.ResourceExist;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.model.Invoice;
import com.restaurant.backend.model.Order;
import com.restaurant.backend.model.User;
import com.restaurant.backend.payloads.InvoiceDTO;
import com.restaurant.backend.payloads.InvoiceRequestDTO;
import com.restaurant.backend.payloads.OrderInvoiceDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
public class InvoiceService {
    @Autowired
    InvoiceRepository repository;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    // Generate Invoice
    public InvoiceDTO generateInvoice(InvoiceRequestDTO dto, long order_id){
        if (this.repository.existsById(dto.getId())) {
            throw new ResourceExist("Invoice", "Id", dto.getId());
        }
        else if (this.repository.existsByOrderId(dto.getOrder_id()) || this.repository.existsByOrderId(order_id)) {
            throw new ResourceExist("Kindly delete the Invoice", "Order-Id", dto.getOrder_id());
        }

        Invoice inv = this.mapDtoToEntity(dto);

        Invoice invoice = this.repository.save(inv);
        return this.mapEntityToDto(invoice);
    }

    // for delete the existing invoice
    public void delete(long id){
        Invoice inv = this.repository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Invoice", "Id", id));
        this.repository.deleteById(inv.getId());
    }

    // ------   Over Ride Methods    ------- //

    public InvoiceDTO mapEntityToDto(Invoice entity) {
        InvoiceDTO dto = new InvoiceDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setOrder_id(entity.getOrder().getId());
        List<OrderInvoiceDTO> deal  = null;
        List<OrderInvoiceDTO> item  = null;

        if (entity.getOrder().getDealOrders() != null)
            item = entity.getOrder().getItemOrders()
                    .stream()
                    .map(e-> new OrderInvoiceDTO(e.getItem().getName(), e.getQuantity(), e.getPrice()))
                    .collect(Collectors.toList());
        if (entity.getOrder().getItemOrders() != null)
            deal = entity.getOrder().getDealOrders()
                    .stream()
                    .map(e-> new OrderInvoiceDTO(e.getDeals().getName(), e.getQuantity(), e.getPrice()))
                    .collect(Collectors.toList());


        dto.setItemOrders(item);
        dto.setDealOrders(deal);
        dto.setTableCode(entity.getOrder().getTableSitting().getTableCode());
        dto.setTotal(entity.getOrder().getBill());
        return dto;
    }

    public Invoice mapDtoToEntity(InvoiceRequestDTO dto) {
        Invoice entity = new Invoice();
        BeanUtils.copyProperties(dto, entity);
        System.out.println("-----------------------------------------");
        Order order = this.orderRepository.findById(dto.getOrder_id())
                .orElseThrow(()-> new ResourceNotFound("Order", "Id", dto.getOrder_id()));

        if (dto.getId() > 0) {
            entity.setCreatedAt(dto.getCreatedAt());
            entity.setCreatedBy(dto.getCreatedBy());
        }
        else {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setCreatedBy(this.getUserName());
        }

        entity.setOrder(order);
        return entity;
    }

    public String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userRepository.findByUsername(authentication.getName()).get();
        return user.getUsername();
    }



}
