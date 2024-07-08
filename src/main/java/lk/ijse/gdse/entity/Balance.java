package lk.ijse.gdse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Balance implements Serializable {
    private String bId;
    private String loanId;
    private String principalBalance;
    private String interestBalance;
    private String totalBalance;
    private String lastUpdatedDate;
}
