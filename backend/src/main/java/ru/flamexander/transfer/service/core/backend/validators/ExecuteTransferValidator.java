package ru.flamexander.transfer.service.core.backend.validators;

import org.springframework.stereotype.Component;
import ru.flamexander.transfer.service.core.api.dtos.ExecuteTransferDtoRequest;
import ru.flamexander.transfer.service.core.backend.errors.FieldValidationError;
import ru.flamexander.transfer.service.core.backend.errors.FieldsValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExecuteTransferValidator {
    public void validate(ExecuteTransferDtoRequest request) {
        List<FieldValidationError> errorFields = new ArrayList<>();

        if(request.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            errorFields.add(new FieldValidationError("amount", "значение не может быть меньше или равно 0" ));
        }
        if(request.getSourceBalance().compareTo(BigDecimal.ZERO) == 0 || request.getSourceBalance().compareTo(request.getAmount()) < 0){
            errorFields.add(new FieldValidationError("balance", "недостаточно средств"));
        }
        if (!errorFields.isEmpty()) {
            throw new FieldsValidationException(errorFields);
        }
    }
}
