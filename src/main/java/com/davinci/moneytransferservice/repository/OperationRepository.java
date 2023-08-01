package com.davinci.moneytransferservice.repository;

import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;

import java.util.Optional;

public interface OperationRepository {
    Optional<String> saveTransfer(Transfer transfer);

    Optional<String> saveConfirmation(Confirmation confirmation);

    boolean containsTransfer(String id);

    Transfer findTransfer(String id);

    Transfer deleteTransfer(String id);
}
