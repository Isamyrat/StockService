package com.example.Task.service.impl;

import com.example.Task.dto.UserDto;
import com.example.Task.dto.UserEditRequestDTO;
import com.example.Task.entity.RoleEntity;
import com.example.Task.entity.UserEntity;
import com.example.Task.exception.BadRequestException;
import com.example.Task.exception.NotFoundException;
import com.example.Task.repository.RoleRepository;
import com.example.Task.repository.UserRepository;
import com.example.Task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByUserName(username);
    }

    @Override
    public void saveUser(UserDto userDto) {
        if (isUsernameExist(userDto.getUsername())) {
            throw new BadRequestException("Username is occupied. Please change it");
        }
        UserEntity user = fillingInUserData(userDto);
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserEditRequestDTO userEditRequestDTO, Integer id) {
        UserEntity user = findUserById(id);

        if (!user.getUsername().equals(userEditRequestDTO.getUsername())) {
            if (isUsernameExist(userEditRequestDTO.getUsername())) {
                throw new BadRequestException("Username is occupied. Please change it");
            }
        }
        UserEntity userEntity = fillInUserDataForUpdate(userEditRequestDTO);

        userEntity.setId(user.getId());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole());
        userRepository.save(userEntity);
    }
    private UserEntity fillInUserDataForUpdate(UserEditRequestDTO userEditRequestDTO) {
        UserEntity user = new UserEntity();
        user.setUsername(userEditRequestDTO.getUsername());
        user.setFullName(userEditRequestDTO.getFullName());
        return user;
    }
    public UserEntity fillingInUserData(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setFullName(userDto.getFullName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(getRole("ROLE_USER"));
        return user;
    }

    private UserDto mapUserToDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFullName(user.getFullName());
        return userDto;
    }

    private boolean isUsernameExist(String userName) {
        return userRepository.existsByUsername(userName);
    }

    private RoleEntity getRole(String role) {
        return roleRepository.findByName(role)
            .orElseThrow(() -> new BadRequestException("Role:" + role + " not found."));
    }

    @Override
    public UserDto findById(Integer id) {
        UserEntity userEntity = findUserById(id);
        return mapUserToDto(userEntity);
    }


    @Override
    public List<UserDto> findAll(final Integer pageNumber, final Integer rowPerPage, final String keyword) {
        Pageable pageable = PageRequest.of(pageNumber - 1, rowPerPage, Sort.by("id").ascending());
        List<UserEntity> userList =
            new ArrayList<>(userRepository.findAllByFullNameLikeAndUsernameLike(keyword, pageable));
        return userList
            .stream()
            .map(this::mapUserToDto)
            .collect(Collectors.toList());
    }

    private UserEntity findUserById(final Integer id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Can not find user with this id: " + id));
    }
    private UserEntity findUserByUserName(final String userName) {
        return userRepository.findByUsername(userName)
            .orElseThrow(() -> new NotFoundException("Can not find user with this userName: " + userName));
    }
    @Override
    public Long count() {
        return userRepository.count();
    }

    @Override
    public String getRoleByUserName(final String userName) {
        return findUserByUserName(userName).getRole().getName();
    }
}
