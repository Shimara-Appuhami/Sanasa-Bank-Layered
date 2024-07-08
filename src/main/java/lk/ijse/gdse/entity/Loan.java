package lk.ijse.gdse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Loan implements Serializable {
    private String loanId;
    private String application;
    private String loanAmount;
    private String loanType;
    private String loanTerm;
    private String collateral;
    private String purpose;
    private String customerId;
    private double percentage;
    private String nic;
    private String date;

    public Loan(String loanId, String application, String loanAmount, String loanType,
                String loanTerm, String collateral, String purpose, String customerId,
                String percentage, String nic, String date) {
        this.loanId = loanId;
        this.application = application;
        this.loanAmount = loanAmount;
        this.loanType = loanType;
        this.loanTerm = loanTerm;
        this.collateral = collateral;
        this.purpose = purpose;
        this.customerId = customerId;
        this.percentage = Double.parseDouble(percentage);
        this.nic = nic;
        this.date=date;


    }

    public Loan(String application, String loanAmount, String loanType, String loanTerm, String collateral, String purpose, String customerId, double percentage, String nic, String date, String loanId) {

    }

    // Getters and setters
    // ...
}