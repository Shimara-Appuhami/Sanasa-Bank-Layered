package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.custom.LoginBo;
import lk.ijse.gdse.dao.DAOFactory;
import lk.ijse.gdse.dao.custom.LoginDAO;

import java.sql.SQLException;

public class LoginBoImpl implements LoginBo {
    LoginDAO loginDAO= (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOGIN);

    @Override
    public String getLastEnteredUsername() throws SQLException, ClassNotFoundException {
        return loginDAO.getLastEnteredUsername();
    }
}
