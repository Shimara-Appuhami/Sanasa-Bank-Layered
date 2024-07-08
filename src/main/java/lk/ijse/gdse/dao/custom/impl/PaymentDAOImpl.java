package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.SQLUtil;
import lk.ijse.gdse.dao.custom.PaymentDAO;
import lk.ijse.gdse.entity.Payment;
import lk.ijse.gdse.model.PaymentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean save(Payment dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO payment (p_invoice_no,nic, loan_id, rate_id, p_method, p_amount, p_date, loan_type, late_fee) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?);",dto.getPInvoiceNo(),dto.getNic(),dto.getLoanId(),dto.getRateId(),dto.getPaymentMethod(),dto.getPaymentAmount(),dto.getPaymentDate(),dto.getLoanType(),dto.getLateFee());
    }

    @Override
    public boolean update(Payment dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE payment SET nic=?,loan_id=?, rate_id=?, p_method=?, p_amount=?, p_date=?, loan_type=?, late_fee=? WHERE p_invoice_no=?;",dto.getNic(),dto.getLoanId(),dto.getRateId(),dto.getPaymentMethod(),dto.getPaymentAmount(),dto.getPaymentDate(),dto.getLoanType(),dto.getLateFee(),dto.getPInvoiceNo());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM payment WHERE nic=?",id);
    }

    @Override
    public Payment searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Payment> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Payment> allPayments = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM payment");
        while (rst.next()) {
            Payment payment = new Payment(
                    rst.getString("p_invoice_no"),
                    rst.getString("nic"),
                    rst.getString("loan_id"),
                    rst.getString("rate_id"),
                    rst.getString("p_method"),
                    rst.getString("p_amount"),
                    rst.getString("p_date"),
                    rst.getString("loan_type"),
                    rst.getString("late_fee")
            );
            allPayments.add(payment);
        }
        return allPayments;
        }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT p_invoice_no FROM payment ORDER BY p_invoice_no DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("p_invoice_no");
            int newCustomerId = Integer.parseInt(id.replace("P", "")) + 1;
            return String.format("P%03d", newCustomerId);
        } else {
            return "P001";
        }
        }



    @Override
    public Payment searchByNic(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM payment WHERE nic = ?;",nic);
        if (rst.next()) {
            return new Payment(
                    rst.getString("p_invoice_no"),
                    rst.getString("nic"),
                    rst.getString("loan_id"),
                    rst.getString("rate_id"),
                    rst.getString("p_method"),
                    rst.getString("p_amount"),
                    rst.getString("p_date"),
                    rst.getString("loan_type"),
                    rst.getString("late_fee")
            );
        }
        return null;
    }

    @Override
    public String getLoanId(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT cl.loan_id FROM customer_loan_details cl JOIN customer c ON cl.c_id = c.c_id WHERE c.nic = ?", nic);
        if (rst.next()) {
            return rst.getString("loan_id");
        }
        return null;
    }

    @Override
    public LocalDate getLoanDate(String loanId) throws SQLException,ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT date FROM loan WHERE loan_id = ?", loanId);
        if (rst.next()) {
            return LocalDate.parse(rst.getString("date"));
        }
        return null;
    }

    @Override
    public String getLoanIdByNIC(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT loan_id FROM customer_loan_details WHERE c_id = (SELECT c_id FROM customer WHERE nic = ?)");
        if (rst.next()) {
            return rst.getString("loan_id");
        }
        return null;
    }

    @Override
    public List<PaymentDTO> getPaymentsByNIC(String nic) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM payment WHERE nic = ?;", nic);
        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        while (resultSet.next()) {
                PaymentDTO paymentDTO = new PaymentDTO();
                paymentDTO.setPInvoiceNo(resultSet.getString("p_invoice_no"));
                paymentDTO.setNic(resultSet.getString("nic"));
                paymentDTO.setLoanId(resultSet.getString("loan_id"));
                paymentDTO.setLoanType(resultSet.getString("loan_type"));
                paymentDTO.setPaymentAmount(String.valueOf(resultSet.getDouble("p_amount")));
                paymentDTO.setPaymentMethod(resultSet.getString("p_method"));
                paymentDTO.setLateFee(String.valueOf(resultSet.getDouble("late_fee")));
                paymentDTO.setPaymentDate(resultSet.getString("p_date"));
                paymentDTOS.add(paymentDTO);

        }
        return null;
    }

    @Override
    public String getLoanType(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT ir.loan_type FROM customer c \" +\n" +
                "//                    \"JOIN customer_loan_details cl ON c.c_id = cl.c_id \" +\n" +
                "//                    \"JOIN loan l ON cl.loan_id = l.loan_id \" +\n" +
                "//                    \"JOIN interest_rate ir ON l.loan_type = ir.loan_type \" +\n" +
                "//                    \"WHERE c.nic = ?",nic);
        if (rst.next()) {
            return rst.getString("loan_type");
        }
        return null;
    }

}