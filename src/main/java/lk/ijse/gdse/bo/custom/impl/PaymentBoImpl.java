package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.SuperBO;
import lk.ijse.gdse.bo.custom.AccountBo;
import lk.ijse.gdse.bo.custom.PaymentBo;
import lk.ijse.gdse.dao.DAOFactory;
import lk.ijse.gdse.dao.custom.PaymentDAO;
import lk.ijse.gdse.entity.Employee;
import lk.ijse.gdse.entity.Payment;
import lk.ijse.gdse.model.EmployeeDTO;
import lk.ijse.gdse.model.PaymentDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentBoImpl implements PaymentBo {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public boolean savePayment(PaymentDTO dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new Payment(dto.getPInvoiceNo(), dto.getNic(), dto.getLoanId(), dto.getRateId(),
                dto.getPaymentMethod(), dto.getPaymentAmount(), dto.getPaymentDate(), dto.getLoanType(),
                dto.getLateFee()));
    }

    @Override
    public boolean updatePayment(PaymentDTO dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(new Payment(dto.getNic(), dto.getLoanId(), dto.getRateId(),
                dto.getPaymentMethod(), dto.getPaymentAmount(), dto.getPaymentDate(), dto.getLoanType(),
                dto.getLateFee(), dto.getPInvoiceNo()));
    }

    @Override
    public boolean deletePayment(String pInvoiceNo) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(pInvoiceNo);
    }

    @Override
    public PaymentDTO searchByIdPayment(String pInvoiceNo) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException {
        List<Payment> payments = paymentDAO.getAll();
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (Payment dto : payments) {
            paymentDTOS.add(new PaymentDTO(dto.getPInvoiceNo(), dto.getNic(), dto.getLoanId(), dto.getRateId(),
                    dto.getPaymentMethod(), dto.getPaymentAmount(), dto.getPaymentDate(), dto.getLoanType(),
                    dto.getLateFee()));
        }
        return paymentDTOS;

    }

    @Override
    public String generateNewPaymentID() throws SQLException, ClassNotFoundException {
        return paymentDAO.generateNewID();
    }

    @Override
    public PaymentDTO searchByNic(String nic) throws SQLException, ClassNotFoundException {
        Payment payment = paymentDAO.searchByNic(nic);
        if (payment != null) {
            return new PaymentDTO(payment.getPInvoiceNo(), payment.getNic(), payment.getLoanId(), payment.getRateId(), payment.getPaymentMethod(), payment.getPaymentAmount(), payment.getPaymentDate(), payment.getLoanType(), payment.getLateFee());
        }
        return null;
    }

    @Override
    public String getLoanId(String nic) throws SQLException, ClassNotFoundException {
        return paymentDAO.getLoanId(nic);
    }

    @Override
    public LocalDate getLoanDate(String loanId) throws SQLException, ClassNotFoundException {
        return paymentDAO.getLoanDate(loanId);
    }

    @Override
    public String getLoanIdByNIC(String nic) throws SQLException, ClassNotFoundException {
        return paymentDAO.getLoanIdByNIC(nic);
    }

    @Override
    public List<PaymentDTO> getPaymentsByNIC(String nic) throws SQLException, ClassNotFoundException {
        return paymentDAO.getPaymentsByNIC(nic);
    }

    @Override
    public String getLoanType(String nic) throws SQLException, ClassNotFoundException {
            return paymentDAO.getLoanType(nic);
    }

    @Override
    public String getRateId(String loanType) throws SQLException, ClassNotFoundException {
        return paymentDAO.getRateId(loanType);
    }
}
