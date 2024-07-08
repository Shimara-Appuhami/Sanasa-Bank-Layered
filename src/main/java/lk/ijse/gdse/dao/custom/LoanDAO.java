package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.InterestRate;
import lk.ijse.gdse.entity.Loan;

import java.sql.SQLException;

public interface LoanDAO extends CrudDAO<Loan> {
    Loan searchByNic(String nic) throws SQLException, ClassNotFoundException;
    String getCustomerId(String nic) throws SQLException, ClassNotFoundException;
    public InterestRate searchByLoanType(String type)throws SQLException,ClassNotFoundException;
}
