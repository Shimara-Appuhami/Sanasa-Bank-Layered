package lk.ijse.gdse.model;
import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String cId;
    private String cName;
    private String cEmail;
    private String cContact;
    private String cAddress;
    private String cAge;
    private String dateOfBirth;
    private String nic;
    private String registrationDate;
    private String annualIncome;


    public CustomerDTO(String name, String email, String contact, String address, String age, String birth, String registrationDate, String income, String inId) {


    }

    public CustomerDTO(String cId) {

    }
}
