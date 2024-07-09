package lk.ijse.gdse.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.gdse.dao.SQLUtil;
import lk.ijse.gdse.dao.custom.UserDAO;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean updatePasswordByNIC(String nic, String newPassword) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE users SET password = ? where NIC = ?", newPassword, nic);
    }

    @Override
    public boolean checkCredential(String nic) throws SQLException, IOException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT NIC FROM users WHERE NIC = ?", nic);
        if (rst.next()) {
            String dbNic = rst.getString("NIC");
            return nic.equals(dbNic);
        }
        return false;
    }

}
