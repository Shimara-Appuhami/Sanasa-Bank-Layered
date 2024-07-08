package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDAO extends CrudDAO<Account> {
    Account searchByNic(String nic) throws SQLException, ClassNotFoundException;

    List<String> getId() throws SQLException, ClassNotFoundException;
    public  String getCustomerId(String nic) throws SQLException,ClassNotFoundException;
}
