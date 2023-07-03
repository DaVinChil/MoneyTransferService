package com.davinci.moneytransferservice;

import com.davinci.moneytransferservice.exception.InvalidData;
import com.davinci.moneytransferservice.model.Amount;
import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import com.davinci.moneytransferservice.repository.OperationRepository;
import com.davinci.moneytransferservice.service.TransferService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MoneyTransferServiceApplicationTests {

    OperationRepository or = new OperationRepository();
    TransferService ts = new TransferService(or);

    @Test
    @Order(1)
    void basicTransferTest() {
        Transfer t = new Transfer();
        t.setCardFromNumber("1234123412341234");
        t.setCardFromValidTill("1223");
        t.setCardFromCVV("123");
        t.setAmount(new Amount(123123, "rubbles"));
        t.setCardToNumber("0987098709870987");
        or.transferMoney(t);
    }

    @Test
    @Order(2)
    void invalidCardFromNumber() {
        Transfer t = new Transfer();
        try {
            t.setCardFromNumber("324");
        } catch (InvalidData id) {
            Assertions.assertEquals("Sender's card number invalid", id.getMessage());
        }
    }

    @Test
    @Order(3)
    void cardValidity() {
        Transfer t = new Transfer();
        try {
            t.setCardFromValidTill("0623");
        } catch (InvalidData id) {
            Assertions.assertEquals("The validity period of the card is over", id.getMessage());
        }
    }

    @Test
    @Order(4)
    void cardCVV() {
        Transfer t = new Transfer();
        try {
            t.setCardFromCVV("00");
        } catch (InvalidData id) {
            Assertions.assertEquals("CVV code is invalid", id.getMessage());
        }
    }

    @Test
    @Order(5)
    void cardToNumber() {
        Transfer t = new Transfer();
        try {
			t.setCardToNumber("38412983091");
        } catch (InvalidData id){
			Assertions.assertEquals("Receiver's card number is invalid", id.getMessage());
		}
    }

    @Test
    @Order(6)
    void basicConfirm() {
        or.confirmOperation(new Confirmation("0", "0000"));
    }


    @Test
    @Order(8)
    void transferServiceOKWithMock() {
		OperationRepository testOr = Mockito.spy(OperationRepository.class);
		Mockito.doReturn(Optional.of("0")).when(testOr).transferMoney(Mockito.any(Transfer.class));
		TransferService testTs = new TransferService(testOr);
		Transfer t = new Transfer();
		t.setCardFromNumber("1234123412341234");
		t.setCardFromValidTill("1223");
		t.setCardFromCVV("123");
		t.setAmount(new Amount(123123, "rubbles"));
		t.setCardToNumber("0987098709870987");
		t.setOperationId("0");
		Assertions.assertEquals("0", testTs.doTransfer(t));
    }

	@Test
    @Order(9)
	void serviceConfirmationTestWithMock(){
		OperationRepository testOr = Mockito.spy(OperationRepository.class);
		Mockito.doReturn(Optional.of("0")).when(testOr).confirmOperation(Mockito.any(Confirmation.class));
		TransferService testTs = new TransferService(testOr);
		Assertions.assertEquals("0", testTs.confirmOperation(new Confirmation("0", "0000")));
	}

	@Test
    @Order(10)
	void serviceTransfer(){
		Transfer t = new Transfer();
		t.setCardFromNumber("1234123412341234");
		t.setCardFromValidTill("1223");
		t.setCardFromCVV("123");
		t.setAmount(new Amount(123123, "rubbles"));
		t.setCardToNumber("0987098709870987");
		Assertions.assertEquals("1", ts.doTransfer(t));
	}

	@Test
    @Order(11)
	void serviceConfirmation(){
		Assertions.assertEquals("1", ts.confirmOperation(new Confirmation("1", "0000")));
	}

}
