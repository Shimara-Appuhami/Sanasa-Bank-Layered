package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.InterestRate;

import java.sql.SQLException;

public interface InterestRateDAO extends CrudDAO<InterestRate> {
    InterestRate searchByNic(String loanType) throws SQLException, ClassNotFoundException;

}
