package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.SQLUtil;
import lk.ijse.gdse.dao.custom.LoanDAO;
import lk.ijse.gdse.entity.InterestRate;
import lk.ijse.gdse.entity.Loan;
import lk.ijse.gdse.model.tm.InterestRateTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanDAOImpl implements LoanDAO {
    @Override
    public boolean save(Loan loan) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO loan (loan_id, c_id, application, loan_amount, loan_type, loan_term, collateral, purpose, percentage,nic,date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)",
                loan.getLoanId(), loan.getApplication(), loan.getLoanAmount(), loan.getLoanType(),
                loan.getLoanTerm(), loan.getCollateral(), loan.getPurpose(), loan.getCustomerId(),
                loan.getPercentage(), loan.getNic(), loan.getDate());
    }

    @Override
    public boolean update(Loan loan) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE loan SET c_id=?, application=?, loan_amount=?, loan_type=?, loan_term=?, collateral=?, purpose=?, percentage=?, nic=?, date=? WHERE loan_id=?",
                loan.getCustomerId(),loan.getApplication(), loan.getLoanAmount(), loan.getLoanType(), loan.getLoanTerm(),
                loan.getCollateral(), loan.getPurpose(), loan.getPercentage(),
                loan.getNic(), loan.getDate(), loan.getLoanId());
    }

    @Override
    public boolean delete(String loanId) throws SQLException, ClassNotFoundException {
        String disableForeignKeyChecks = "SET FOREIGN_KEY_CHECKS=0";
        String enableForeignKeyChecks = "SET FOREIGN_KEY_CHECKS=1";

        String deleteChildRows = "DELETE FROM customer_loan_details WHERE loan_id = ?";
        String deleteParentRow = "DELETE FROM loan WHERE loan_id = ?";
        return false;
    }

    @Override
    public Loan searchById(String loanId) throws SQLException, ClassNotFoundException {
        return null;
    }
    @Override
    public List<Loan> getAll() throws SQLException, ClassNotFoundException {
        List<Loan> allLoans = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM loan");
        while (rst.next()) {
            Loan loan = new Loan(
                    rst.getString("loan_id"),
                    rst.getString("c_id"),
                    rst.getString("application"),
                    rst.getString("loan_amount"),
                    rst.getString("loan_type"),
                    rst.getString("loan_term"),
                    rst.getString("collateral"),
                    rst.getString("purpose"),
                    rst.getString("percentage"),
                    rst.getString("nic"),
                    rst.getString("date")
            );
            allLoans.add(loan);
        }
        return allLoans;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT loan_id FROM loan ORDER BY loan_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("loan_id");
            int newLoanId = Integer.parseInt(id.replace("L", "")) + 1;
            return String.format("L%03d", newLoanId);
        } else {
            return "L001";
        }
    }



    @Override
    public Loan searchByNic(String nic) throws SQLException, ClassNotFoundException {
            ResultSet rst = SQLUtil.execute("SELECT * from loan where nic=?", nic);
            if (rst.next()) {
                return new Loan(
                        rst.getString("loan_id"),
                        rst.getString("application"),
                        rst.getString("loan_amount"),
                        rst.getString("loan_type"),
                        rst.getString("loan_term"),
                        rst.getString("collateral"),
                        rst.getString("purpose"),
                        rst.getString("c_id"),
                        rst.getString("percentage"),
                        rst.getString("nic"),
                        rst.getString("date")
                );
            }
            return null;

    }

    @Override
    public String getCustomerId(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT c_id FROM customer WHERE nic=?", nic);
        if (rst.next()) {
            return rst.getString("c_id");
        }
            return null;
    }

    @Override
    public InterestRate searchByLoanType(String type) throws SQLException, ClassNotFoundException {
        InterestRate interestRate;
        ResultSet rst = SQLUtil.execute("SELECT percentage FROM interest_rate WHERE loan_type=?", type);
        if (rst.next()) {
            return new InterestRate(
                    rst.getDouble("percentage")
            );
            // double percentage = rst.getDouble("percentage");
            //  interestRate = new InterestRate(type, percentage);

        }
        return null;
    }
}