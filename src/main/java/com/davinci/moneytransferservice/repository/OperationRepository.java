package com.davinci.moneytransferservice.repository;

import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;

import java.util.Optional;

public interface OperationRepository {
    Optional<String> transferMoney(Transfer transfer);

    Optional<String> confirmOperation(Confirmation confirmation);

    boolean containsTransfer(String id);

    Transfer getTransfer(String id);

    Transfer removeTransfer(String id);
}
