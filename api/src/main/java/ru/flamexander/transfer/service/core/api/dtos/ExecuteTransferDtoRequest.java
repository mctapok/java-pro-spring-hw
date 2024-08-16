package ru.flamexander.transfer.service.core.api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Schema(description = "ДТО запроса выполнения перевода")
public class ExecuteTransferDtoRequest {
    @NotNull
    private String sourceAccountNumber;
    @NotNull
    private String targetAccountNumber;
    @NotNull
    private Long targetId;
    @NotNull
    private BigDecimal sourceBalance;
    @NotNull
    private BigDecimal amount;
}
