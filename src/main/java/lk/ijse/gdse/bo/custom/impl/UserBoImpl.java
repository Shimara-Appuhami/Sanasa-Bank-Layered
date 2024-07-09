package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.custom.UserBo;
import lk.ijse.gdse.dao.DAOFactory;
import lk.ijse.gdse.dao.custom.UserDAO;

import java.io.IOException;
import java.sql.SQLException;

public class UserBoImpl implements UserBo {
    UserDAO userDAO= (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public boolean updatePasswordByNIC(String nic, String newPassword) throws SQLException, ClassNotFoundException {
        return userDAO.updatePasswordByNIC(nic, newPassword);
    }

    @Override
    public boolean checkCredential(String nic) throws SQLException, ClassNotFoundException, IOException {
        return userDAO.checkCredential(nic);
    }
}
