package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.SQLUtil;
import lk.ijse.gdse.dao.custom.ReminderDAO;
import lk.ijse.gdse.entity.Reminder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReminderDAOImpl implements ReminderDAO {
    @Override
    public boolean save(Reminder dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO reminder VALUES (?, ?, ?, ?, ?, ?,?)",dto.getRId(),dto.getNic(),dto.getLoanType(),dto.getRMessage(),dto.getRType(),dto.getRDate(),dto.getRStatus());

    }

    @Override
    public boolean update(Reminder dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE reminder SET nic=? ,loan_type=?, r_message=?, r_type=?, r_date=?, r_status=? WHERE r_id=?",dto.getNic(),dto.getLoanType(),dto.getRMessage(),dto.getRType(),dto.getRDate(),dto.getRStatus(),dto.getRId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM reminder WHERE r_id=?",id);
    }

    @Override
    public Reminder searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Reminder> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Reminder> allReminders = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM reminder");
        while (rst.next()) {
            Reminder reminder = new Reminder(rst.getString("rId"), rst.getString("nic"), rst.getString("loanType"), rst.getString("rMessage"), rst.getString("rType"), rst.getString("rDate"), rst.getString("rStatus"));
            allReminders.add(reminder);
        }
        return allReminders;
    }
    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT r_id FROM reminder ORDER BY r_id DESC LIMIT 1 ");
        if (rst.next()) {
            String id = rst.getString("r_id");
            int newCustomerId = Integer.parseInt(id.replace("R", "")) + 1;
            return String.format("R%03d", newCustomerId);
        } else {
            return "R001";
        }

    }



    @Override
    public Reminder searchByNic(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT c_email from customer where nic = ?",nic);
        if (rst.next()) {
                    rst.getString("c_email");


        }
        return null;
    }

    @Override
    public String getLoanType(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT ir.loan_type FROM customer c JOIN customer_loan_details cl ON c.c_id = cl.c_id JOIN loan l ON cl.loan_id = l.loan_id JOIN interest_rate ir ON l.loan_type = ir.loan_type WHERE c.nic = ?", nic);
        if (rst.next()) {
            return rst.getString("loan_type");

    }
    return null;
    }

    @Override
    public String getEmail(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT c_email from customer where nic = ?",nic);
        if (rst.next()) {
            return rst.getString("c_email");
        }
        return null;
    }
}
