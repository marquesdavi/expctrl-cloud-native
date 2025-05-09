package com.financial.feature.user.service.contract;

import com.financial.feature.user.dto.UserDTO;
import com.financial.feature.user.dto.UserRequest;
import com.financial.feature.user.entity.User;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface UserServiceContract {
    List<UserDTO> list();
    UserDTO get(Long id);
    Response create(UserRequest dto);
    UserDTO update(Long id, UserDTO dto);
    void delete(Long id);
    User findByID(Long id);
}
