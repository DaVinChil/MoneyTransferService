package com.davinci.moneytransferservice;

import com.davinci.moneytransferservice.model.Amount;
import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import com.davinci.moneytransferservice.repository.OperationRepository;
import com.davinci.moneytransferservice.service.TransferService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MoneyTransferServiceApplicationTests {

	@Test
	void basicTransferTest() {
		OperationRepository or = new OperationRepository();
		Transfer t = new Transfer();
		t.setCardFromNumber("1234123412341234");
		t.setCardFromValidTill("1223");
		t.setCardFromCVV("123");
		t.setAmount(new Amount(123123, "rubbles"));
		t.setCardToNumber("0987098709870987");
		or.transferMoney(t);
	}

	@Test
	void basicConfirm(){
		OperationRepository or = new OperationRepository();
		or.confirmOperation(new Confirmation("0", "123"));
	}

}
