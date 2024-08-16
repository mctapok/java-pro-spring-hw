package ru.flamexander.transfer.service.core.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Schema(description = "ДТО результат выполнения перевода")
public class ExecuteTransferDtoResult {
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private LocalDateTime transferDate;
    private BigDecimal amount;
    private String status;

    public ExecuteTransferDtoResult(String sourceAccountNumber, String targetAccountNumber, LocalDateTime transferDate, BigDecimal amount, String status) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.targetAccountNumber = targetAccountNumber;
        this.transferDate = transferDate;
        this.amount = amount;
        this.status = status;
    }
}
