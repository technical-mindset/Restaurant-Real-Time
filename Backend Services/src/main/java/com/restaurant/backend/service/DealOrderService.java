package com.restaurant.backend.service;

import com.restaurant.backend.dao.DealOrderRepository;
import com.restaurant.backend.dao.DealRepository;
import com.restaurant.backend.dao.OrderRepository;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.model.Deal;
import com.restaurant.backend.model.DealOrder;
import com.restaurant.backend.model.Order;
import com.restaurant.backend.payloads.DealOrderDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@Service
public class DealOrderService extends BaseService<DealOrder, DealOrderDTO, DealOrderRepository> {
    @Autowired
    private DealRepository dealRepository;
    @Autowired
    private OrderRepository orderRepo;
    public DealOrderService(DealOrderRepository repository) {
        super(repository);
    }


    // Add Case
    public DealOrderDTO addUpdate(DealOrderDTO dto, long id){
        DealOrder dealOrder;
        Optional<DealOrder> optionalItemOrder = this.repository.findById(dto.getId());

        /** for add case */
        if (dto.getId() == 0 && optionalItemOrder.isEmpty()) {
            // adding | updating new Deal-Order for existing Order
            dto.setCreatedAt(LocalDateTime.now());
            dto.setCreatedBy(this.getUserName());
        }
        /** for update case */
        else if (dto.getId() > 0) {
            if (optionalItemOrder.isPresent()) {
                dealOrder = optionalItemOrder.get();

                // adding | updating new Deal-Order for existing Order
                dto.setCreatedAt(dealOrder.getCreatedAt());
                dto.setCreatedBy(dealOrder.getCreatedBy());
            }
            else {
                throw new ResourceNotFound("Deal Order", "Id", dto.getId());
            }
        }

        dto.setOrder(id);
        dealOrder = this.mapDtoToEntity(dto);

        DealOrder savedDealOrder = this.repository.save(dealOrder);
        return this.mapEntityToDto(savedDealOrder);
    }

    // Update case
    public DealOrderDTO updateDealOrder(DealOrderDTO dto, long id){
        dto.setOrder(id);

        if (this.repository.findById(dto.getId()).isPresent()) {
            // if someone edit the old deal-Order
            DealOrder dealOrder = this.repository.findById(dto.getId()).get();

            dto.setCreatedAt(dealOrder.getCreatedAt());
            dto.setCreatedBy(dealOrder.getCreatedBy());

            DealOrder dealOrder0 = this.mapDtoToEntity(dto);
            DealOrder dealOrderSave = this.repository.save(dealOrder0);

            return this.mapEntityToDto(dealOrderSave);
        }

        // either it adds new deal-order into order
        return this.addUpdate(dto, id);
    }

    public void deleteDealOrder(long orderId, long id){
        DealOrder dealOrder = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Deal-Order", "Id", id));

        if (dealOrder.getOrder().getId() == orderId) { // confirmation that it contains the same orderId which they belong too.
            this.repository.deleteById(id);
        }
        else {
            throw new ResourceNotFound("Deal-Order", "Id", id);
        }
    }

    // Delete case would be executing from Order service (through order)
    // Get All Deal-Order case would be fetched from Order service


    // ------------- Override methods ------------------

    @Override
    public DealOrderDTO mapEntityToDto(DealOrder entity) {
        DealOrderDTO dto = new DealOrderDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setOrder(entity.getOrder().getId());
        dto.setDeals(entity.getDeals().getId());
        return dto;
    }

    @Override
    public DealOrder mapDtoToEntity(DealOrderDTO dto) {
        DealOrder entity = new DealOrder();
        BeanUtils.copyProperties(dto, entity);

        Deal deal = this.dealRepository.findById(dto.getDeals())
                .orElseThrow(() -> new ResourceNotFound("Deal", "Id", dto.getDeals()));
        Order order = this.orderRepo.findById(dto.getOrder())
                .orElseThrow(() -> new ResourceNotFound("Order", "Id", dto.getOrder()));

        entity.setDeals(deal);
        entity.setPrice(deal.getDiscountedPrice() * dto.getQuantity());
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

