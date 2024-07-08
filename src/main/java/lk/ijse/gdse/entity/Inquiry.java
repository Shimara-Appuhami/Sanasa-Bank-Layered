package lk.ijse.gdse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inquiry implements Serializable {
    private String inId;
    private String nic;
    private String cId;
    private String inType;
    private String inDate;
    private String responseDate;


}
