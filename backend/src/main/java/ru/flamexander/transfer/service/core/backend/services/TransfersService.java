package ru.flamexander.transfer.service.core.backend.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flamexander.transfer.service.core.api.dtos.ExecuteTransferDtoRequest;
import ru.flamexander.transfer.service.core.backend.entities.Account;
import ru.flamexander.transfer.service.core.backend.entities.Transfer;
import ru.flamexander.transfer.service.core.backend.errors.AppLogicException;
import ru.flamexander.transfer.service.core.backend.errors.FieldsValidationException;
import ru.flamexander.transfer.service.core.backend.repositories.TransfersRepository;
import ru.flamexander.transfer.service.core.backend.validators.ExecuteTransferValidator;


@Service
@RequiredArgsConstructor
public class TransfersService {
    private final AccountsService accountsService;
    private final TransfersRepository transfersRepository;
    private final ExecuteTransferValidator executeTransferValidator;
    private static final Logger logger = LoggerFactory.getLogger(TransfersService.class.getName());

    @Transactional
    public Transfer execute(ExecuteTransferDtoRequest executeTransferDtoRequest, Long clientId) {
        Transfer transfer = new Transfer(clientId,
                executeTransferDtoRequest.getSourceAccountNumber(),
                executeTransferDtoRequest.getTargetId(),
                executeTransferDtoRequest.getTargetAccountNumber(),
                executeTransferDtoRequest.getAmount()
        );
        Transfer savedTransfer = transfersRepository.save(transfer);

        try {
            executeTransferValidator.validate(executeTransferDtoRequest);
            Account sourceAccount = accountsService.getByClientIdAndAccNumber(clientId, executeTransferDtoRequest.getSourceAccountNumber())
                    .orElseThrow(() -> new AppLogicException("TRANSFER_SOURCE_ACCOUNT_NOT_FOUND", "Перевод невозможен поскольку не существует счет отправителя"));
            Account targetAccount = accountsService.getByClientIdAndAccNumber(executeTransferDtoRequest.getTargetId(), executeTransferDtoRequest.getTargetAccountNumber())
                    .orElseThrow(() -> new AppLogicException("TRANSFER_TARGET_ACCOUNT_NOT_FOUND", "Перевод невозможен поскольку не существует счет получателя"));
            sourceAccount.setBalance(sourceAccount.getBalance().subtract(executeTransferDtoRequest.getAmount()));
            targetAccount.setBalance(targetAccount.getBalance().add(executeTransferDtoRequest.getAmount()));
            savedTransfer.setTransferStatus("SUCCESS");
        } catch (FieldsValidationException e) {
            savedTransfer.setTransferStatus("FAILED");
            logger.error("transfer execute failed - {}\n{}", savedTransfer,e.getFields());
            throw e;
        }catch (Exception e){
            savedTransfer.setTransferStatus("FAILED");
            logger.error("transfer execute unexpected error {}\n{}", savedTransfer, e.getMessage());
            throw new AppLogicException("UNKNOWN ERROR", "transfer FAILED");
        }
        transfersRepository.save(savedTransfer);
        return savedTransfer;
    }

    public Page<Transfer> getAllTransfers(Long clientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transfersRepository.findAllByClientIdOrderByTransferDateDesc(clientId, pageable);
    }
}
