package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBO;

import java.io.IOException;
import java.sql.SQLException;

public interface UserBo extends SuperBO {
    public  boolean updatePasswordByNIC(String nic, String newPassword) throws SQLException, ClassNotFoundException;
    public  boolean checkCredential(String nic) throws SQLException, ClassNotFoundException, IOException;
}
