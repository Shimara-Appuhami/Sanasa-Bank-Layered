package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.SuperDAO;

import java.io.IOException;
import java.sql.SQLException;

public interface UserDAO extends SuperDAO {
    public  boolean updatePasswordByNIC(String nic, String newPassword) throws SQLException ,ClassNotFoundException;
    public boolean checkCredential(String nic) throws SQLException, IOException, ClassNotFoundException;

}
