package lk.ijse.gdse.entity;

import lombok.*;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account implements Serializable {
    private String aNo;
    private String cId;
    private String aType;
    private String aBalance;
    private String openDate;
    private String status;


}
