package lk.ijse.gdse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterestRateTm implements Comparable<InterestRateTm>{
    private String rate_id;
    private String loan_type;
    private String percentage;

    public InterestRateTm(String type, double percentage) {
        this.loan_type = type;
        this.percentage = String.valueOf(percentage);
    }
    @Override
    public int compareTo(InterestRateTm o) {
        return rate_id.compareTo(o.getRate_id());
    }


}
