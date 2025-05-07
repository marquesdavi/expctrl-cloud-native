package com.financial.feature.user.service;

import com.financial.feature.user.dto.UserDTO;
import com.financial.feature.user.entity.User;
import com.financial.feature.user.repository.UserRepository;
import com.financial.feature.user.service.contract.UserServiceContract;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.time.Instant;
import java.util.List;


@RequestScoped
@RequiredArgsConstructor
public class UserService implements UserServiceContract {
    private final UserRepository userRepository;

    @Override
    public List<UserDTO> list() {
        return userRepository.listAll().stream()
                .map(u -> new UserDTO(u.id, u.name, u.email, u.createdAt))
                .toList();
    }

    @Override
    public UserDTO get(Long id) {
        User u = findByID(id);
        return new UserDTO(u.id, u.name, u.email, u.createdAt);
    }

    @Override
    @Transactional
    public Response create(UserDTO dto) {
        User u = new User();
        u.name = dto.name();
        u.email = dto.email();
        u.createdAt = Instant.now();
        u.persist();
        return Response.created(URI.create("/users/" + u.id)).build();
    }

    @Override
    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        var u = findByID(id);
        u.name = dto.name();
        u.email = dto.email();
        return new UserDTO(u.id, u.name, u.email, u.createdAt);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User instance = findByID(id);
        userRepository.delete(instance);
    }

    @Override
    public User findByID(Long id) {
        return userRepository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
    }
}
