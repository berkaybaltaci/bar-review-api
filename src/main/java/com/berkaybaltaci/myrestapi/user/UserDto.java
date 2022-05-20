package com.berkaybaltaci.myrestapi.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private Long id;

    @NotNull
    private String name;
}
