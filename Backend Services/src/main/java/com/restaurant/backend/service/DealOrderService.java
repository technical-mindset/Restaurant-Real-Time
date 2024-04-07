package com.restaurant.backend.service;

import com.restaurant.backend.dao.DealOrderRepository;
import com.restaurant.backend.dao.DealRepository;
import com.restaurant.backend.dao.OrderRepository;
import com.restaurant.backend.exception.ResourceExist;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.model.Deal;
import com.restaurant.backend.model.DealOrder;
import com.restaurant.backend.model.ItemOrder;
import com.restaurant.backend.model.Order;
import com.restaurant.backend.payloads.DealOrderDTO;
import com.restaurant.backend.payloads.ItemOrderDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public DealOrderDTO addDealOrder(DealOrderDTO dto, long id){
        if (this.repository.findById(dto.getId()).isPresent()) {
            throw new ResourceExist("Deal-Order", "Id", dto.getId());
        }
        dto.setOrder(id);
        DealOrder dealOrder = this.mapDtoToEntity(dto);

        DealOrder dealOrder0 = this.repository.save(dealOrder);
        return this.mapEntityToDto(dealOrder0);
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
        return this.addDealOrder(dto, id);
    }

    // Delete case would be executing from Order service (through order)
    // Get All Deal-Order case would be fetched from Order service


    // ------------- Override methods ------------------

    @Override
    public DealOrderDTO mapEntityToDto(DealOrder entity) {
        DealOrderDTO dto = new DealOrderDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setOrder(entity.getOrder().getId());
        dto.setDeals(dto.getDeals());
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

