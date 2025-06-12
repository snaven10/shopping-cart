package com.cart.auth.service;

import com.cart.auth.dto.UserDto;
import com.cart.auth.dto.UserResponseDto;
import com.cart.auth.entity.UserEntity;
import com.cart.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto createUser(UserDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(dto.getEnabled());

        userRepository.save(user);

        return new UserResponseDto(user.getUsername());
    }

    public UserResponseDto updateUser(Long id, UserDto dto) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }
    
        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
    
        user.setEnabled(dto.getEnabled());
    
        userRepository.save(user);
    
        return new UserResponseDto(user.getUsername());
    }

}
