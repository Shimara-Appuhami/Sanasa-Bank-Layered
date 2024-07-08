package lk.ijse.gdse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterestRate implements Serializable {
    private String rateId;
    private String loanType;
    private String percentage;


    public InterestRate(String rateId, String loanType, double percentage) {

    }

    public InterestRate(String type, double percentage) {

    }

    public InterestRate(double percentage) {
        this.percentage = String.valueOf(percentage);
    }
}
