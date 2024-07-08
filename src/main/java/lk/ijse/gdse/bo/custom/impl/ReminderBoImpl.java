package lk.ijse.gdse.bo.custom.impl;


import lk.ijse.gdse.bo.custom.ReminderBo;
import lk.ijse.gdse.dao.DAOFactory;
import lk.ijse.gdse.dao.custom.ReminderDAO;
import lk.ijse.gdse.entity.Employee;
import lk.ijse.gdse.entity.Reminder;
import lk.ijse.gdse.model.EmployeeDTO;
import lk.ijse.gdse.model.ReminderDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReminderBoImpl implements ReminderBo {
    ReminderDAO reminderDAO = (ReminderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REMINDER);

    @Override
    public boolean saveReminder(ReminderDTO dto) throws SQLException, ClassNotFoundException {
        return reminderDAO.save(new Reminder(dto.getRId(), dto.getNic(), dto.getLoanType(), dto.getRMessage(),
                dto.getRType(), dto.getRDate(), dto.getRStatus()));
    }

    @Override
    public boolean updateReminder(ReminderDTO dto) throws SQLException, ClassNotFoundException {
        return reminderDAO.update(new Reminder(dto.getNic(), dto.getLoanType(), dto.getRMessage(),
                dto.getRType(), dto.getRDate(), dto.getRStatus(),dto.getRId()));
    }

    @Override
    public boolean deleteReminder(String rId) throws SQLException, ClassNotFoundException {
        return reminderDAO.delete(rId);
    }

    @Override
    public ReminderDTO searchByIdReminder(String rId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ReminderDTO> getAllReminders() throws SQLException, ClassNotFoundException {
        List<Reminder> reminders = reminderDAO.getAll();
        ArrayList<ReminderDTO> reminderDTOS = new ArrayList<>();
        for (Reminder dto : reminders) {
            reminderDTOS.add(new ReminderDTO(dto.getRId(), dto.getNic(), dto.getLoanType(), dto.getRMessage(),
                    dto.getRType(), dto.getRDate(), dto.getRStatus()));
        }
        return reminderDTOS;
    }

    @Override
    public String generateNewReminderID() throws SQLException, ClassNotFoundException {
        return reminderDAO.generateNewID();
    }

    @Override
    public ReminderDTO searchByNic(String nic) throws SQLException, ClassNotFoundException {
        Reminder reminder = reminderDAO.searchByNic(nic);
        if (reminder != null) {
            return new ReminderDTO(reminder.getRId(), reminder.getNic(), reminder.getLoanType(), reminder.getRMessage(), reminder.getRType(), reminder.getRDate(), reminder.getRStatus());
        }
        return null;
    }

    @Override
    public String getLoanType(String nic) throws SQLException, ClassNotFoundException {
        return reminderDAO.getLoanType(nic);
    }

    @Override
    public String getEmail(String nic) throws SQLException, ClassNotFoundException {
        return reminderDAO.getEmail(nic);
    }

}
