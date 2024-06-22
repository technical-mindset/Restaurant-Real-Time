package com.restaurant.backend.service;

import com.restaurant.backend.dao.RestaurantRepositroy;
import com.restaurant.backend.dao.RolesRepository;
import com.restaurant.backend.dao.UserRepository;
import com.restaurant.backend.exception.ResourceExist;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.helper.PaginationResponse;
import com.restaurant.backend.model.Restaurant;
import com.restaurant.backend.model.Roles;
import com.restaurant.backend.model.User;
import com.restaurant.backend.payloads.RoleDTO;
import com.restaurant.backend.payloads.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<User, UserDTO, UserRepository> {
    @Autowired
    private RestaurantRepositroy restaurantRepositroy;
    @Autowired
    private RolesRepository roles;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserService(UserRepository repository) {
        super(repository);
    }

    // get All users
    public PaginationResponse getAllUsers(int pageNumber, int pageSize, String sortBy){

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return this.pageToPagination(pageable);
    }

    // create user
    public UserDTO addUser(UserDTO dto){
        if (this.repository.findById(dto.getId()).isPresent()) {
            throw new ResourceExist("User", "Id", dto.getId());
        }
        else if (this.repository.existsByUsername(dto.getUsername())) {
            throw new ResourceExist("User", "User-name", dto.getUsername());
        }
        User user = this.mapDtoToEntity(dto);

        return this.mapEntityToDto(this.repository.save(user));
    }

    // update user
    public UserDTO updateUser(UserDTO dto, long id) {
        User entity = this.repository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("User", "Id", id));

        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());

        User user = this.repository.save(this.mapDtoToEntity(dto));
        return this.mapEntityToDto(user);
    }

    // delete user
    public void deleteUser(long id){
        User user = this.repository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("User", "Id", id));

        this.repository.delete(user);
    }

    @Override
    public UserDTO mapEntityToDto(User entity) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(entity, dto);

//        for (Roles role: entity.getRole()){
//            dto.setRole();
//        }
        dto.setRestaurant(entity.getRestaurant().getId());
        dto.setRole(entity.getRole().stream().map(e-> e.getId()).collect(Collectors.toList()));
        return dto;
    }

    @Override
    public User mapDtoToEntity(UserDTO dto) {
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);
        // encode password
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        List<Roles> role = this.roles.findAllById(dto.getRole());
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
