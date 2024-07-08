package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.Reminder;

import java.sql.SQLException;

public interface ReminderDAO extends CrudDAO<Reminder> {
    Reminder searchByNic(String nic) throws SQLException, ClassNotFoundException;
    public  String getLoanType(String nic)throws SQLException,ClassNotFoundException;
    public  String getEmail(String nic)throws SQLException,ClassNotFoundException;
}
