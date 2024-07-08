package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBO;
import lk.ijse.gdse.entity.InterestRate;
import lk.ijse.gdse.model.LoanDTO;

import java.sql.SQLException;
import java.util.List;

public interface LoanBo extends SuperBO {
    boolean saveLoan(LoanDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateLoan(LoanDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteLoan(String loanId) throws SQLException, ClassNotFoundException;

    LoanDTO searchByIdLoan(String loanId) throws SQLException, ClassNotFoundException;

    List<LoanDTO> getAllLoans() throws SQLException, ClassNotFoundException;

    String generateNewLoanID() throws SQLException, ClassNotFoundException;

    LoanDTO searchByNic(String nic) throws SQLException, ClassNotFoundException;

    public String getCustomerId(String nic) throws SQLException, ClassNotFoundException;
    InterestRate searchByLoanType(String loanType) throws SQLException, ClassNotFoundException;
}
