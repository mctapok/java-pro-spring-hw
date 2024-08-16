package ru.flamexander.transfer.service.core.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clientId;
    private String sourceAccountNumber;
    private Long targetClientId;
    private String targetAccountNumber;
    private BigDecimal amount;
    private String transferStatus;
    @CreationTimestamp
    private LocalDateTime transferDate;
    @CreationTimestamp
    private LocalDateTime updatedAt;

    public Transfer(Long clientId,
                    String sourceAccountNumber,
                    Long targetClientId,
                    String targetAccountNumber,
                    BigDecimal amount) {
        this.clientId = clientId;
        this.sourceAccountNumber = sourceAccountNumber;
        this.targetClientId = targetClientId;
        this.targetAccountNumber = targetAccountNumber;
        this.amount = amount;
        this.transferStatus = "IN PROGRESS";
    }
}
