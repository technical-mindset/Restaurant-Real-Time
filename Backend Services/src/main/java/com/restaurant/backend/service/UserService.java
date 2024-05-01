package com.restaurant.backend.service;

import com.restaurant.backend.dao.RestaurantRepositroy;
import com.restaurant.backend.dao.RolesRepository;
import com.restaurant.backend.dao.UserRepository;
import com.restaurant.backend.model.Restaurant;
import com.restaurant.backend.model.Roles;
import com.restaurant.backend.model.User;
import com.restaurant.backend.payloads.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<User, UserDTO, UserRepository> {
    @Autowired
    private RestaurantRepositroy restaurantRepositroy;
    @Autowired
    private RolesRepository roles;


    public UserService(UserRepository repository) {
        super(repository);
    }
    






    @Override
    public UserDTO mapEntityToDto(User entity) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setRestaurant(entity.getRestaurant().getId());
        dto.setRole(entity.getRole());
        return dto;
    }

    @Override
    public User mapDtoToEntity(UserDTO dto) {
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);

        List<Roles> role = this.roles.findAllById(dto.getRole()
                .stream()
                .map(e -> e.getId())
                .collect(Collectors.toList()));
        Restaurant restaurant = this.restaurantRepositroy.findById(dto.getRestaurant()).get();

        entity.setRestaurant(restaurant);
        entity.setRole(role);

        if (dto.getId() > 0) {
            entity.setCreatedAt(dto.getCreatedAt());
            entity.setCreatedBy(dto.getCreatedBy());
        }
        else {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setCreatedBy(this.getUserName());
        }
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(this.getUserName());
        return entity;
    }
}
