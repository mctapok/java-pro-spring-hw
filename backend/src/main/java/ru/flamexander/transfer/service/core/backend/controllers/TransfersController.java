package ru.flamexander.transfer.service.core.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.flamexander.transfer.service.core.api.dtos.ExecuteTransferDtoRequest;
import ru.flamexander.transfer.service.core.api.dtos.ExecuteTransferDtoResult;
import ru.flamexander.transfer.service.core.backend.entities.Transfer;
import ru.flamexander.transfer.service.core.backend.services.TransfersService;

import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
public class TransfersController {
    private final TransfersService transfersService;

    private Function<Transfer, ExecuteTransferDtoResult> entityToDto = transferResult -> new ExecuteTransferDtoResult(
            transferResult.getSourceAccountNumber(),
            transferResult.getTargetAccountNumber(),
            transferResult.getTransferDate(),
            transferResult.getAmount(),
            transferResult.getTransferStatus()
    );

    @PostMapping("/execute")
    public ExecuteTransferDtoResult executeTransfer(@Valid @RequestBody ExecuteTransferDtoRequest executeTransferDtoRequest, @RequestHeader Long clientId) {
        return entityToDto.apply(transfersService.execute(executeTransferDtoRequest, clientId));
    }

    @GetMapping("/{clientId}")
    public Page<ExecuteTransferDtoResult> getAllTransfers(@PathVariable Long clientId,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
        Page<Transfer> transfersPage = transfersService.getAllTransfers(clientId, page,size);
        return transfersPage.map(entityToDto);
    }
}
