package lk.ijse.gdse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
    private String pInvoiceNo;
    private String nic;
    private String loanId;
    private String rateId;
    private String paymentMethod;
    private String paymentAmount;
    private String paymentDate;
    private String loanType;
    private String lateFee;


    public Payment(String loanType, String rateId) {
    }
}
