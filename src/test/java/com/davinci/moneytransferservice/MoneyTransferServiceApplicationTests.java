package com.davinci.moneytransferservice;

import com.davinci.moneytransferservice.exception.InvalidData;
import com.davinci.moneytransferservice.logger.TransferLogger;
import com.davinci.moneytransferservice.model.Amount;
import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import com.davinci.moneytransferservice.repository.OperationRepository;
import com.davinci.moneytransferservice.service.StaticCodeTransferService;
import com.davinci.moneytransferservice.service.TransferService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MoneyTransferServiceApplicationTests {

    @Autowired
    OperationRepository or;
    @Autowired
    TransferService ts;

    @Test
    @Order(1)
    void basicTransferTest() {
        Transfer t = new Transfer("1234123412341234",
                "12/23",
                "123",
                "0987098709870987",
                new Amount(123123, "rubbles"));
        or.transferMoney(t);
    }

    @Test
    @Order(2)
    void invalidCardFromNumber() {

        try {
            new Transfer("123412342341234",
                "12/23",
                "123",
                "0987098709870987",
                new Amount(123123, "rubbles"));
        } catch (InvalidData id) {
            Assertions.assertEquals("Sender's card number invalid", id.getMessage());
        }
    }

    @Test
    @Order(3)
    void cardValidity() {
        try {
            Transfer t = new Transfer("1234123412341234",
                    "12/22",
                    "123",
                    "0987098709870987",
                    new Amount(123123, "rubbles"));
        } catch (InvalidData id) {
            Assertions.assertEquals("The validity period of the card is over", id.getMessage());
        }
    }

    @Test
    @Order(4)
    void cardCVV() {
        try {
            Transfer t = new Transfer("1234123412341234",
                    "12/23",
                    "1111",
                    "0987098709870987",
                    new Amount(123123, "rubbles"));
        } catch (InvalidData id) {
            Assertions.assertEquals("CVV code is invalid", id.getMessage());
        }
    }

    @Test
    @Order(5)
    void cardToNumber() {
        try {
            Transfer t = new Transfer("1234123412341234",
                    "12/23",
                    "123",
                    "098709870990909870987",
                    new Amount(123123, "rubbles"));
        } catch (InvalidData id) {
            Assertions.assertEquals("Receiver's card number is invalid", id.getMessage());
        }
    }

    @Test
    @Order(6)
    void basicConfirm() {
        String opId = or.confirmOperation(new Confirmation("0", "0000")).get();
        Assertions.assertEquals("0", opId);
    }


    @Test
    @Order(8)
    void transferServiceOKWithMock() {
        OperationRepository testOr = Mockito.mock(OperationRepository.class);
        Mockito.doReturn(Optional.of("0")).when(testOr).transferMoney(Mockito.any(Transfer.class));
        StaticCodeTransferService testTs = new StaticCodeTransferService(testOr, TransferLogger.getInstance());
        Transfer t = new Transfer("1234123412341234",
                "12/23",
                "123",
                "0987098709870987",
                new Amount(123123, "rubbles"));
        Assertions.assertEquals("0", testTs.doTransfer(t));
    }

    @Test
    @Order(9)
    void serviceConfirmationTestWithMock() {
        OperationRepository testOr = Mockito.mock(OperationRepository.class);
        Mockito.doReturn(Optional.of("0")).when(testOr).confirmOperation(Mockito.any());
        Mockito.doReturn(new Transfer("1234123412341234",
                "12/23",
                "123",
                "0987098709870987",
                new Amount(123123, "rubbles"))).when(testOr).getTransfer(Mockito.any());
        Mockito.doReturn(new Transfer("1234123412341234",
                "12/23",
                "123",
                "0987098709870987",
                new Amount(123123, "rubbles"))).when(testOr).removeTransfer(Mockito.any());
        Mockito.doReturn(true).when(testOr).containsTransfer(Mockito.any());
        StaticCodeTransferService testTs = new StaticCodeTransferService(testOr, TransferLogger.getInstance());

        Assertions.assertEquals("0", testTs.confirmOperation(new Confirmation("0", "0000")));
    }

    @Test
    @Order(10)
    void serviceTransfer() {
        Transfer t = new Transfer("1234123412341234",
                "12/23",
                "123",
                "0987098709870987",
                new Amount(123123, "rubbles"));
        Assertions.assertEquals("1", ts.doTransfer(t));
    }

    @Test
    @Order(11)
    void serviceConfirmation() {
        Assertions.assertEquals("1", ts.confirmOperation(new Confirmation("1", "0000")));
    }

}
