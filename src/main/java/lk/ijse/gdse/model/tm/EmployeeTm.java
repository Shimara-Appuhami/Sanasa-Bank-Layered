package lk.ijse.gdse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTm implements Comparable<EmployeeTm>{
    private String e_id;
    private String e_name;
    private String e_contact;
    private String e_address;
    private String e_salary;
    private String position;
    @Override
    public int compareTo(EmployeeTm o) {
        return e_id.compareTo(o.getE_id());
    }


}
