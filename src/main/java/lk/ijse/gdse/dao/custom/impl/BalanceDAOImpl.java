package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.SQLUtil;
import lk.ijse.gdse.dao.custom.BalanceDAO;
import lk.ijse.gdse.entity.Balance;
import lk.ijse.gdse.model.BalanceDTO;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BalanceDAOImpl implements BalanceDAO {
    @Override
    public boolean save(Balance dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO balance (b_id, loan_id, principal_balance, interest_balance, total_balance, last_updated_date) VALUES (?, ?, ?, ?, ?, ?)",dto.getBId(),dto.getLoanId(),dto.getPrincipalBalance(),dto.getInterestBalance(),dto.getTotalBalance(),dto.getLastUpdatedDate());

    }

    @Override
    public boolean update(Balance dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE balance SET loan_id=?,principal_balance=?, interest_balance=?, total_balance=?, last_updated_date=? WHERE b_id=?",dto.getLoanId(),dto.getPrincipalBalance(),dto.getInterestBalance(),dto.getTotalBalance(),dto.getLastUpdatedDate(),dto.getBId());

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM balance WHERE b_id=?",id);
    }

    @Override
    public Balance searchById(String id) throws SQLException, ClassNotFoundException {

        return null;


    }

    @Override
    public List<Balance> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Balance> allBalance= new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM balance");
        while (rst.next()) {
            Balance balance = new Balance(rst.getString("b_id"), rst.getString("loan_id"), rst.getString("principal_balance"), rst.getString("interest_balance"), rst.getString("total_balance"), rst.getString("last_updated_date"));
        allBalance.add(balance);
        }
        return allBalance;
    }
    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT b_id FROM balance ORDER BY b_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("b_id");
            int newCustomerId = Integer.parseInt(id.replace("B", "")) + 1;
            return String.format("B%03d", newCustomerId);
        } else {
            return "B001";
        }
    }




    @Override
    public Balance searchByNic(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT b.b_id, b.loan_id, b.principal_balance, b.interest_balance, b.total_balance, b.last_updated_date FROM balance b JOIN loan l ON b.loan_id = l.loan_id JOIN customer c ON c.c_id = l.c_id WHERE c.nic = ?",nic);

        if (rst.next()) {
            return new Balance(
                    rst.getString("b_id"),
                    rst.getString("loan_id"),
                    rst.getString("principal_balance"),
                    rst.getString("interest_balance"),
                    rst.getString("total_balance"),
                    rst.getString("last_updated_date")
            );
        }
        return null;
    }

    @Override
    public BalanceDTO calculatePaymentAmount(String nic, double loanAmount, double interestRate) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT l.loan_amount, SUM(p.p_amount),p.p_date AS payment_count\n" +
                "FROM loan l\n" +
                "JOIN payment p ON l.loan_id = p.loan_id\n" +
                "WHERE p.nic = ?\n" +
                "GROUP BY l.loan_amount;",nic,loanAmount,interestRate);
        if (rst.next()) {
            String id = rst.getString("b_id");
            nic = rst.getString("nic");
            String principalBalance = rst.getString("principal_balance");
            String interestBalance = rst.getString("interest_balance");
            String totalBalance = rst.getString("total_balance");
            String lastUpdatedDate = rst.getString("last_updated_date");
            BalanceDTO balance = new BalanceDTO(id, nic, principalBalance, interestBalance, totalBalance, lastUpdatedDate);
            return balance;
        }else{
            return null;
        }
    }

    @Override
    public String getAmount(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT loan_amount FROM loan WHERE nic = ?", nic);
        if (rst.next()) {
            return rst.getString("loan_amount");
        }
        return null;
    }

    @Override
    public String getPayBalance(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT SUM(p_amount) from payment where nic=?", nic);
        if (rst.next()) {
            return rst.getString("SUM(p_amount)");
        }
        return null;
    }

    @Override
    public String getTotalBalance(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute(  "SELECT (SELECT loan_amount FROM loan WHERE nic = ?) - COALESCE((SELECT SUM(p_amount) FROM payment WHERE nic = ?), 0) AS total_balance",nic, nic);
        if (rst.next()) {
            return rst.getString("total_balance");
        }
        return null;
    }

    @Override
    public String getLastPaymentDate(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT MAX(p_date) AS last_payment_date FROM payment WHERE nic=?",nic);
        if (rst.next()) {
            return rst.getString("last_payment_date");
        }
        return null;
    }

    @Override
    public String getLoanId(String nic) throws SQLException, ClassNotFoundException {
      ResultSet rst = SQLUtil.execute("SELECT loan_id FROM loan WHERE nic=?", nic);
      if (rst.next()) {
          return rst.getString("loan_id");
      }
      return null;
    }

}
