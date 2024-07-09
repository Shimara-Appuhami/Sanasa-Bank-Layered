package lk.ijse.gdse.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    private String userId;
    private String name;
    private String nic;
    private String password;
}
