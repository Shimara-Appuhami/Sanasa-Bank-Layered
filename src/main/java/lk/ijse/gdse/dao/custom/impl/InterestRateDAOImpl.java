package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.SQLUtil;
import lk.ijse.gdse.dao.custom.InterestRateDAO;
import lk.ijse.gdse.entity.InterestRate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InterestRateDAOImpl implements InterestRateDAO {
    @Override
    public boolean save(InterestRate rate) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO interest_rate  VALUES (?, ?, ?)",rate.getRateId(),rate.getLoanType(),rate.getPercentage());
    }

    @Override
    public boolean update(InterestRate rate) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "UPDATE interest_rate SET loan_type=?, percentage=? WHERE rate_id=?", rate.getLoanType(),rate.getPercentage(),rate.getRateId());
    }

    @Override
    public boolean delete(String cId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM interest_rate WHERE rate_id=?",cId);
    }

    @Override
    public InterestRate searchById(String cId) throws SQLException, ClassNotFoundException {
    return null;

    }

    @Override
    public List<InterestRate> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<InterestRate> allRates = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM interest_rate");
        while (rst.next()) {
            InterestRate interestRate = new InterestRate(rst.getString("rate_id"), rst.getString("loan_type"), rst.getString("percentage"));
            allRates.add(interestRate);
        }
        return allRates;
    }



    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT rate_id FROM interest_rate ORDER BY rate_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("rate_id");
            int newCustomerId = Integer.parseInt(id.replace("INR", "")) + 1;
            return String.format("INR%03d", newCustomerId);
        } else {
            return "INR001";
        }
    }


    @Override
    public InterestRate searchByNic(String loanType) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM interest_rate WHERE loan_type = ?",loanType);
        if (rst.next()) {
            return new InterestRate(
                    rst.getString("rate_id"),
                    rst.getString("loan_type"),
                    rst.getString("percentage")
            );
        }
        return null;
    }

}
