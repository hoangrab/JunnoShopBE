package com.juno.service;

import com.juno.DTO.UserDTO;
import com.juno.entity.User;
import com.juno.model.UserModel;
import com.juno.request.GoogleRequest;
import com.juno.request.LoginRequest;
import com.juno.request.RegisterRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IUserService {
    Map<String, Object> login(LoginRequest loginRequest);
    void register(RegisterRequest registerRequest);
    Map<String, Object> loginAuth2(GoogleRequest googleRequest);
    List<UserModel> getAll(Pageable pageable);
    UserModel getById(Long id);
    void createUser(UserDTO userDTO) throws IOException;
    void updateUser(UserDTO userDTO, Long id) throws IOException;
    void deleteUser(Long id);
    void convertDtoToEntity(UserDTO userDTO, User user) throws IOException;
    UserModel convertEntityToModel(User user);
}
