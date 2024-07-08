package lk.ijse.gdse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanTm  implements Comparable<LoanTm>{
    private String loan_id;
    private String application;
    private String loan_amount;
    private String loan_type;
    private String loan_term;
    private String collateral;
    private String purpose;
    private String c_id;
    private double percentage;
    private String nic;
    private String date;




    public LoanTm(double percentage) {
    }
    @Override
    public int compareTo(LoanTm o) {
        return loan_id.compareTo(o.getLoan_id());
    }


}
