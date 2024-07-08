package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.Inquiry;

import java.sql.SQLException;

public interface InquiryDAO extends CrudDAO<Inquiry> {
    Inquiry searchByNic(String nic) throws SQLException, ClassNotFoundException;
    String getCustomerId(String nic) throws SQLException, ClassNotFoundException;

}
