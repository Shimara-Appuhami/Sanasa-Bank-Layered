package lk.ijse.gdse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reminder implements Serializable {
    private String rId;
    private String nic;
    private String loanType;
    private String rMessage;
    private String rType;
    private String rDate;
    private String rStatus;

//    public Reminder(String reminderId, String nic, String loanType, String massage, String type, String date, String status) {
//    }
}
