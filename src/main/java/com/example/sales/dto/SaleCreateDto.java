package com.example.sales.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleCreateDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long productId;
    @NotNull private Long channelId;
    @NotNull @Min(1) private Integer quantity;
    private LocalDateTime saleDatetime; // null ise now
    private String note;
}
