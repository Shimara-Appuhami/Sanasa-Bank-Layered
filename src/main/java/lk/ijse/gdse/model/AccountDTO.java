package lk.ijse.gdse.model;

import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDTO extends BalanceDTO {
    private String aNo;
    private String cId;
    private String aType;
    private String aBalance;
    private String openDate;
    private String status;


}
