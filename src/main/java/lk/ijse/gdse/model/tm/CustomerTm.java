package lk.ijse.gdse.model.tm;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerTm implements Comparable<CustomerTm> {
    private String c_id;
    private String c_name;
    private String c_email;
    private String c_contact;
    private String c_address;
    private String c_age;
    private String date_of_birth;

    private String annual_income;
    private String nic;
    private String registration_date;



    @Override
    public int compareTo(CustomerTm o) {
        return c_id.compareTo(o.getC_id());    }
}
