package lk.ijse.gdse.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReminderDTO {
    private String rId;
    private String nic;
    private String loanType;
    private String rMessage;
    private String rType;
    private String rDate;
    private String rStatus;
    private String email;

    public ReminderDTO(String reminderId, String nic, String loanType, String massage, String type, String date, String status) {
    }
}
