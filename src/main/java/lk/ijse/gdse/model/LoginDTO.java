package lk.ijse.gdse.model;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDTO {
    private String userId;
    private String name;
    private String nic;
    private String password;

}
