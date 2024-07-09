package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.SuperDAO;

import java.sql.SQLException;

public interface LoginDAO extends SuperDAO {
    public  String getLastEnteredUsername() throws SQLException, ClassNotFoundException;
}
