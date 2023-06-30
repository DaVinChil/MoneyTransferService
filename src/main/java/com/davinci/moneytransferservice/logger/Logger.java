package com.davinci.moneytransferservice.logger;

import com.davinci.moneytransferservice.exception.OperationFail;
import com.davinci.moneytransferservice.model.Transfer;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger INSTANCE;
    private String operationPath;
    private String errorPath;
    private DateTimeFormatter dateFormat;

    public static Logger getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Logger();
        }

        return INSTANCE;
    }


    private Logger(){
        operationPath = Paths.get("./src/main/resources/operations.log").toAbsolutePath().toString();
        errorPath = Paths.get("./src/main/resources/error.log").toAbsolutePath().toString();
        dateFormat = DateTimeFormatter.ofPattern("[dd-MM-yyyy HH:mm:ss]");
    }

    public String getOperationPath(){
        return operationPath;
    }

    public String getErrorPath(){
        return errorPath;
    }

    public void logException(Exception e, HttpStatus status) {
        try(FileWriter fw = new FileWriter(errorPath)) {
            fw.write(String.format("%s %-6s%s\n", LocalDateTime.now().format(dateFormat), status.value(), e.getMessage()));
            fw.flush();
        } catch (IOException ex) {
            throw new OperationFail("Logger error");
        }
    }

    public void logTransfer(Transfer transfer) {
        try(FileWriter fw = new FileWriter(operationPath)) {
            fw.write(String.format("%s %s (%s) %s %s (%s) %d %s %s %s\n",
                    LocalDateTime.now().format(dateFormat), transfer.getOperationId(),
                    transfer.getCardFromNumber(),
                    transfer.getCardFromValidTill(), transfer.getCardFromCVV(),
                    transfer.getCardToNumber(), transfer.getAmount().getValue(),
                    transfer.getAmount().getCurrency(), "WAITING.."));
            fw.flush();
        } catch (IOException ex) {
            throw new OperationFail("Logger error");
        }
    }
}
