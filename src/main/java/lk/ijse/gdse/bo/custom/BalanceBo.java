package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBO;
import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.Balance;
import lk.ijse.gdse.model.BalanceDTO;
import lk.ijse.gdse.model.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface BalanceBo extends SuperBO {

    boolean saveBalance(BalanceDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateBalance(BalanceDTO dto) throws SQLException, ClassNotFoundException;
    boolean deleteBalance(String id) throws SQLException, ClassNotFoundException;
    BalanceDTO searchByIdBalance(String id) throws SQLException, ClassNotFoundException;
    List<BalanceDTO> getAllBalances() throws SQLException, ClassNotFoundException;
    String generateNewBalanceId() throws SQLException, ClassNotFoundException;
    public BalanceDTO searchByNicBalance(String nic) throws SQLException, ClassNotFoundException;
    BalanceDTO calculateBalance(String nic, double loanAmount, double interestRate) throws SQLException, ClassNotFoundException;
    public String getAmount(String nic) throws SQLException, ClassNotFoundException;
    String getPayBalance(String nic) throws SQLException, ClassNotFoundException;
    public String getTotalBalance(String nic) throws SQLException, ClassNotFoundException;
    public String getLastPaymentDate(String nic) throws SQLException, ClassNotFoundException;
    public String getLoanId(String nic) throws SQLException, ClassNotFoundException;



}
