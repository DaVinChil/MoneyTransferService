package com.davinci.moneytransferservice.model;

import com.davinci.moneytransferservice.exception.InvalidData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {

    @NotBlank
    @Length(min=16, max=16)
    private String cardFromNumber;
    @NotBlank
    @Pattern(regexp = "^(0[7-9]|1[0-2])(2[3-9]|[3-9][0-9])$")
    private String cardFromValidTill;
    @NotBlank
    @Length(min=3, max=3)
    private String cardFromCVV;
    @NotBlank
    @Length(min=16, max=16)
    private String cardToNumber;
    @NotEmpty
    private Amount amount;
    private String operationId;

    public void setCardFromNumber(String cardFromNumber){
        if(cardFromNumber.length() != 16){
            throw new InvalidData("Sender's card number invalid");
        }

        this.cardFromNumber = cardFromNumber;
    }

    public void setCardFromValidTill(String cardFromValidTill){
        int month = Integer.parseInt(cardFromValidTill.substring(0,2));
        int year = Integer.parseInt(cardFromValidTill.substring(2));
        int curYear = Year.now().getValue();
        int curMonth = LocalDate.now().getMonthValue();

        if (year < (curYear % 100)){
            throw new InvalidData("The validity period of the card is over");
        } else if(year == curYear && month < curMonth) {
            throw new InvalidData("The validity period of the card is over");
        }

        this.cardFromValidTill = cardFromValidTill;
    }

    public void setCardFromCVV(String cardFromCVV){
        if(cardFromCVV.length() != 3){
            throw new InvalidData("CVV code is invalid");
        }

        this.cardFromCVV = cardFromCVV;
    }

    public void setCardToNumber(String cardToNumber){
        if(cardToNumber.length() != 16){
            throw new InvalidData("Receiver's card number is invalid");
        }

        this.cardToNumber = cardToNumber;
    }

    public void setAmount(Amount amount){
        this.amount = amount;
    }

    public void setOperationId(String operationId){
        this.operationId = operationId;
    }
}
