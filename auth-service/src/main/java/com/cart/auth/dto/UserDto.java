package com.cart.auth.dto;

import com.cart.common.validation.ValidationGroups.OnCreate;
import com.cart.common.validation.ValidationGroups.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {

    @NotBlank(groups = OnCreate.class)
    private String username;

    @NotBlank(groups = OnCreate.class)
    private String password;

    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    private Boolean enabled;
}
