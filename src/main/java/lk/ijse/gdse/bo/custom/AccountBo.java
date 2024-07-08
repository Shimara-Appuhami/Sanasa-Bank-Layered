package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBO;
import lk.ijse.gdse.model.AccountDTO;
import lk.ijse.gdse.model.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface AccountBo extends SuperBO {
    boolean saveAccount(AccountDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateAccount(AccountDTO dto) throws SQLException, ClassNotFoundException;
    boolean deleteAccount(String id) throws SQLException, ClassNotFoundException;
    AccountDTO searchByIdAccount(String id) throws SQLException, ClassNotFoundException;
    List<AccountDTO> getAllAccounts() throws SQLException, ClassNotFoundException;

    String generateNewAccountId() throws SQLException, ClassNotFoundException;

    public AccountDTO searchByNicAccount(String nic) throws SQLException, ClassNotFoundException;
    public  List<String> getIdsAccount() throws SQLException, ClassNotFoundException;
    String getCustomerId(String nic) throws SQLException, ClassNotFoundException;


}
