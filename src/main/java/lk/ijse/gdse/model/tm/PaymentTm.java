package lk.ijse.gdse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTm implements Comparable<PaymentTm>{
    private String p_invoice_no;
    private String nic;
    private String loan_id;
    private String rate_id;
    private String p_method;
    private String p_amount;
    private String p_date;
    private String loan_type;
    private String late_fee;
    @Override
    public int compareTo(PaymentTm o) {
        return p_invoice_no.compareTo(o.getP_invoice_no());
    }
}
