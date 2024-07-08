package lk.ijse.gdse.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CustomerLoanDTO {
    private String loanId;
    private String customerId;


}
