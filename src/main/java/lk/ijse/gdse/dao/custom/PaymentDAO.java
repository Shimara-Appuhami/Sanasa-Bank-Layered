package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.Payment;
import lk.ijse.gdse.model.PaymentDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {
    Payment searchByNic(String nic) throws SQLException, ClassNotFoundException;
    public  String getLoanId(String nic)throws SQLException,ClassNotFoundException;
    public  LocalDate getLoanDate(String loanId) throws SQLException,ClassNotFoundException;
    public  String getLoanIdByNIC(String nic) throws SQLException ,ClassNotFoundException;
    public  List<PaymentDTO> getPaymentsByNIC(String nic) throws SQLException ,ClassNotFoundException;
    public  String getLoanType(String nic) throws SQLException,ClassNotFoundException;



    }
