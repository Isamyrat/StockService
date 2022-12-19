package com.example.Task.service;

import com.example.Task.dto.UserDto;
import com.example.Task.dto.UserEditRequestDTO;
import com.example.Task.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserDto> findAll(final Integer pageNumber,final Integer rowPerPage,final String keyword);

    void saveUser(UserDto userDto);

    void updateUser(UserEditRequestDTO userDto, Integer id);

    UserDto findById(final Integer id);

    Long count();

    String getRoleByUserName(final String userName);

    UserEntity fillingInUserData(UserDto userDto);
}
