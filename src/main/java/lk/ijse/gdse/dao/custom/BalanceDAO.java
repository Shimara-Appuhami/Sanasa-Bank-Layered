package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.Balance;
import lk.ijse.gdse.model.BalanceDTO;

import java.sql.SQLException;

public interface BalanceDAO extends CrudDAO<Balance> {
    Balance searchByNic(String nic) throws SQLException, ClassNotFoundException;
    BalanceDTO calculatePaymentAmount(String nic, double loanAmount, double interestRate) throws SQLException, ClassNotFoundException;
    public String getAmount(String nic) throws SQLException, ClassNotFoundException;
    String getPayBalance(String nic) throws SQLException, ClassNotFoundException;
    public String getTotalBalance(String nic) throws SQLException, ClassNotFoundException;
    public String getLastPaymentDate(String nic) throws SQLException, ClassNotFoundException;
    public String getLoanId(String nic) throws SQLException, ClassNotFoundException;



}
