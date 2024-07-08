package lk.ijse.gdse.entity;


import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CustomerLoan implements Serializable {
    private String loanId;
    private String customerId;


}
