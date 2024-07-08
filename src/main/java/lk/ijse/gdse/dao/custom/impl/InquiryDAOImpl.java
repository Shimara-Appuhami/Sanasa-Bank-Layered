package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.SQLUtil;
import lk.ijse.gdse.dao.custom.InquiryDAO;
import lk.ijse.gdse.entity.Inquiry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InquiryDAOImpl implements InquiryDAO {
    @Override
    public boolean save(Inquiry inquiry) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO inquiries VALUES (?, ?, ?, ?,?,?)",inquiry.getInId(),inquiry.getNic(),inquiry.getCId(),inquiry.getInType(),inquiry.getInDate(),inquiry.getResponseDate());
    }

    @Override
    public boolean update(Inquiry inquiry) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "UPDATE inquiries SET nic=?,c_id=?,in_type=?, in_date=?, response_date=? WHERE in_id=?",  inquiry.getInId(),inquiry.getNic(),inquiry.getCId(),inquiry.getInType(),inquiry.getInDate(),inquiry.getResponseDate());
    }

    @Override
    public boolean delete(String iId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM inquiries WHERE in_id = ?",iId);
    }

    @Override
    public Inquiry searchById(String iId) throws SQLException, ClassNotFoundException {
        return null;

    }

    @Override
    public List<Inquiry> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Inquiry> allInquiries = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM inquiries");
        while (rst.next()) {
            Inquiry inquiry = new Inquiry(rst.getString("in_id"), rst.getString("nic"), rst.getString("c_id"), rst.getString("in_type"), rst.getString("in_date"), rst.getString("response_date"));
                    allInquiries.add(inquiry);
        }
        return allInquiries;
    }



    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT in_id FROM inquiries ORDER BY in_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("in_id");
            int newCustomerId = Integer.parseInt(id.replace("I", "")) + 1;
            return String.format("I%03d", newCustomerId);
        } else {
            return "I001";
        }
    }


    @Override
    public Inquiry searchByNic(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT c_id FROM customer WHERE nic = ?",nic);
        if (rst.next()) {
            return new Inquiry(
                    rst.getString("inId"),
                    rst.getString("nic"),
                    rst.getString("cId"),
                    rst.getString("inType"),
                    rst.getString("inDate"),
                    rst.getString("responseDate")

            );
        }
        return null;
    }

}
