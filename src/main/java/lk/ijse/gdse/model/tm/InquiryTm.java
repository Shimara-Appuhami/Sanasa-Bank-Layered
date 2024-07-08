package lk.ijse.gdse.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InquiryTm implements Comparable<InquiryTm>{
    private String in_id;
    private String nic;
    private String c_id;
    private String in_type;
    private String in_date;
    private String response_date;
    @Override
    public int compareTo(InquiryTm o) {
        return in_id.compareTo(o.getIn_id());
    }


}
