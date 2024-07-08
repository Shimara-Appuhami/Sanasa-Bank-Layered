package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBO;
import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.Payment;
import lk.ijse.gdse.model.LoanDTO;
import lk.ijse.gdse.model.PaymentDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface PaymentBo extends SuperBO {
    boolean savePayment(PaymentDTO dto) throws SQLException, ClassNotFoundException;
    boolean updatePayment(PaymentDTO dto) throws SQLException, ClassNotFoundException;
    boolean deletePayment(String pInvoiceNo) throws SQLException, ClassNotFoundException;
    PaymentDTO searchByIdPayment(String pInvoiceNo) throws SQLException, ClassNotFoundException;
    List<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException;
    String generateNewPaymentID() throws SQLException, ClassNotFoundException;
    PaymentDTO searchByNic(String nic) throws SQLException, ClassNotFoundException;
    public  String getLoanId(String nic)throws SQLException,ClassNotFoundException;
    LocalDate getLoanDate(String loanId) throws SQLException,ClassNotFoundException;
    public  String getLoanIdByNIC(String nic) throws SQLException,ClassNotFoundException;
    public  List<PaymentDTO> getPaymentsByNIC(String nic) throws SQLException ,ClassNotFoundException;
    public  String getLoanType(String nic) throws SQLException,ClassNotFoundException;


    }
