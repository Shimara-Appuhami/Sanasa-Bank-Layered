package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBO;

import java.sql.SQLException;

public interface LoginBo extends SuperBO {
    public  String getLastEnteredUsername() throws SQLException, ClassNotFoundException;

}
