package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBO;
import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.Reminder;
import lk.ijse.gdse.model.PaymentDTO;
import lk.ijse.gdse.model.ReminderDTO;

import java.sql.SQLException;
import java.util.List;

public interface ReminderBo extends SuperBO {
    boolean saveReminder(ReminderDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateReminder(ReminderDTO dto) throws SQLException, ClassNotFoundException;
    boolean deleteReminder(String rId) throws SQLException, ClassNotFoundException;
    ReminderDTO searchByIdReminder(String rId) throws SQLException, ClassNotFoundException;
    List<ReminderDTO> getAllReminders() throws SQLException, ClassNotFoundException;
    String generateNewReminderID() throws SQLException, ClassNotFoundException;
    ReminderDTO searchByNic(String nic) throws SQLException, ClassNotFoundException;
    public  String getLoanType(String nic)throws SQLException,ClassNotFoundException;
    public  String getEmail(String nic) throws SQLException,ClassNotFoundException;

}
