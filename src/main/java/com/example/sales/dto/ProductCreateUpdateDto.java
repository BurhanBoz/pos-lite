package com.example.sales.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class ProductCreateUpdateDto {

    @NotBlank
    private String name;
    private Boolean active;
}
