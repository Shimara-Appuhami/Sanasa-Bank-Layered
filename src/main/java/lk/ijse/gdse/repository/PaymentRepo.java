//package lk.ijse.gdse.repository;
//
//import javafx.scene.control.Alert;
//import lk.ijse.gdse.db.DbConnection;
//import lk.ijse.gdse.model.PaymentDTO;
//
//import java.sql.*;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PaymentRepo {

//    public static boolean savePayment(PaymentDTO paymentDTO) throws SQLException {
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        boolean isSaved = false;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "INSERT INTO payment (p_invoice_no,nic, loan_id, rate_id, p_method, p_amount, p_date, loan_type, late_fee) " +
//                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, paymentDTO.getPInvoiceNo());
//            pstm.setString(2, paymentDTO.getNic());
//            pstm.setString(3, paymentDTO.getLoanId());
//            pstm.setString(4, paymentDTO.getRateId());
//            pstm.setString(5, paymentDTO.getPaymentMethod());
//            pstm.setString(6, paymentDTO.getPaymentAmount());
//            pstm.setString(7, paymentDTO.getPaymentDate());
//            pstm.setString(8, paymentDTO.getLoanType());
//            pstm.setString(9, paymentDTO.getLateFee());
//
//            isSaved = pstm.executeUpdate() > 0;
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
//        }
//
//        return isSaved;
//    }
//
//
//    public static List<PaymentDTO> getAll() throws SQLException {
//        String sql = "SELECT * FROM payment";
//
//        PreparedStatement pstm = DbConnection.getInstance().getConnection()
//                .prepareStatement(sql);
//
//        ResultSet resultSet = pstm.executeQuery();
//
//        List<PaymentDTO> paymentDTOList = new ArrayList<>();
//
//        while (resultSet.next()) {
//
//            String invoiceNo = resultSet.getString("p_invoice_no");
//            String nic = resultSet.getString("nic");
//            String loanId = resultSet.getString("loan_id");
//            String rateId = resultSet.getString("rate_id");
//            String paymentMethod = resultSet.getString("p_method");
//            String paymentAmount = resultSet.getString("p_amount");
//            String paymentDate = resultSet.getString("p_date");
//            String loanType = resultSet.getString("loan_type");
//            String lateFee = resultSet.getString("late_fee");
//            PaymentDTO paymentDTO = new PaymentDTO(invoiceNo, nic, loanId, rateId, paymentMethod, paymentAmount, paymentDate, loanType, lateFee);
//            paymentDTOList.add(paymentDTO);
//        }
//        return paymentDTOList;
//
//    }
//
//    public static boolean update(PaymentDTO paymentDTO) throws SQLException {
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        boolean isUpdated = false;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "UPDATE payment SET p_invoice_no=?,loan_id=?, rate_id=?, p_method=?, p_amount=?, p_date=?, loan_type=?, late_fee=? " +
//                    "WHERE nic=?";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, paymentDTO.getPInvoiceNo());
//            pstm.setString(9, paymentDTO.getNic());
//            pstm.setString(2, paymentDTO.getLoanId());
//            pstm.setString(3, paymentDTO.getRateId());
//            pstm.setString(4, paymentDTO.getPaymentMethod());
//            pstm.setString(5, paymentDTO.getPaymentAmount());
//            pstm.setString(6, paymentDTO.getPaymentDate());
//            pstm.setString(7, paymentDTO.getLoanType());
//            pstm.setString(8, paymentDTO.getLateFee());
//
//
//            isUpdated = pstm.executeUpdate() > 0;
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
//        }
//
//        return isUpdated;
//    }
//
//    public static PaymentDTO searchById(String nic) throws SQLException {
//        String sql = "SELECT * FROM payment WHERE nic = ?";
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        ResultSet resultSet = null;
//        PaymentDTO paymentDTO = null;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, nic);
//            resultSet = pstm.executeQuery();
//
//            if (resultSet.next()) {
//                String invoiceNo = resultSet.getString("p_invoice_no");
//                 nic = resultSet.getString("nic");
//                String loanId = resultSet.getString("loan_id");
//                String rateId = resultSet.getString("rate_id");
//                String paymentMethod = resultSet.getString("p_method");
//                String paymentAmount = resultSet.getString("p_amount");
//                String paymentDate = resultSet.getString("p_date");
//                String loanType = resultSet.getString("loan_type");
//                String lateFee = resultSet.getString("late_fee");
//                paymentDTO = new PaymentDTO(invoiceNo, nic, loanId, rateId, paymentMethod, paymentAmount, paymentDate, loanType, lateFee);
//
//            }
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
//        }
//
//        return paymentDTO;
//    }
//
//    public static boolean delete(String nic) throws SQLException {
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        boolean isDeleted = false;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "DELETE FROM payment WHERE nic=?";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, nic);
//
//            isDeleted = pstm.executeUpdate() > 0;
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
//        }
//
//        return isDeleted;
//    }
//
//    public static String getLastPaymentId() throws SQLException {
//        String sql = "SELECT p_invoice_no FROM payment ORDER BY p_invoice_no DESC LIMIT 1";
//
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        ResultSet resultSet = null;
//        String lastId = null;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            pstm = connection.prepareStatement(sql);
//            resultSet = pstm.executeQuery();
//
//            if (resultSet.next()) {
//                lastId = resultSet.getString("p_invoice_no");
//            }
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
//        }
//
//        return lastId;
//    }
//
//    public static List<String> getLoIds() throws SQLException {
//        String sql = "SELECT in_id FROM inquiries";
//        PreparedStatement pstm = DbConnection.getInstance().getConnection()
//                .prepareStatement(sql);
//
//        List<String> idList = new ArrayList<>();
//
//        ResultSet resultSet = pstm.executeQuery();
//        while (resultSet.next()) {
//            String id = resultSet.getString(1);
//            idList.add(id);
//        }
//        return idList;
//    }
//
//    public static List<String> getIds() throws SQLException {
//        String sql = "SELECT in_id FROM inquiries";
//        PreparedStatement pstm = DbConnection.getInstance().getConnection()
//                .prepareStatement(sql);
//
//        List<String> idList = new ArrayList<>();
//
//        ResultSet resultSet = pstm.executeQuery();
//        while (resultSet.next()) {
//            String id = resultSet.getString(1);
//            idList.add(id);
//        }
//        return idList;
//    }
//
//    public static List<String> getLoanIds() throws SQLException {
//        String sql = "SELECT loan_id FROM loan";
//        PreparedStatement pstm = DbConnection.getInstance().getConnection()
//                .prepareStatement(sql);
//
//        List<String> idList = new ArrayList<>();
//
//        ResultSet resultSet = pstm.executeQuery();
//        while (resultSet.next()) {
//            String id = resultSet.getString(1);
//            idList.add(id);
//        }
//        return idList;
//    }
//
//
//
//    public static PaymentDTO searching(String nic) throws SQLException {
//        String sql = "SELECT l.loan_id " +
//                "FROM customer c " +
//                "INNER JOIN loan l ON c.c_id = l.c_id " +
//                "WHERE c.nic = ?";
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        ResultSet resultSet = null;
//        PaymentDTO paymentDTO = null;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, nic);
//            resultSet = pstm.executeQuery();
//
//            if (resultSet.next()) {
//                String invoiceNo = resultSet.getString("p_invoice_no");
//                String nic1 = resultSet.getString("nic");
//                String loanId = resultSet.getString("loan_id");
//                String rateId = resultSet.getString("rate_id");
//                String paymentMethod = resultSet.getString("p_method");
//                String paymentAmount = resultSet.getString("p_amount");
//                String paymentDate = resultSet.getString("p_date");
//                String loanType = resultSet.getString("loan_type");
//                String lateFee = resultSet.getString("late_fee");
//                paymentDTO = new PaymentDTO(invoiceNo, nic, loanId, rateId, paymentMethod, paymentAmount, paymentDate, loanType, lateFee);
//            }
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.CONFIRMATION, e.getMessage()).show();
//        }
//        return paymentDTO;
//    }
//
//
//   /* public static String getRateID(String nic) {
//        String rateId = null;
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = " SELECT ir.rate_id FROM customer c JOIN customer_loan_details cl ON c.c_id = cl.c_id JOIN loan l ON cl.loan_id = l.loan_id JOIN interest_rate ir ON l.loan_type = ir.loan_type WHERE c.nic =?";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, nic);
//            resultSet = pstm.executeQuery();
//
//            if (resultSet.next()) {
//                rateId = resultSet.getString("rate_id");
//            }
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
//            e.printStackTrace(); // For debugging purposes
//        }
//
//        return rateId;
//    }*/
//   public static String getRateID(String nic) {
//       Connection connection = null;
//       PreparedStatement pstm = null;
//       ResultSet resultSet = null;
//       String rateId = null;
//
//       try {
//           connection = DbConnection.getInstance().getConnection();
//           String sql = "SELECT ir.rate_id FROM customer c JOIN customer_loan_details cl ON c.c_id = cl.c_id JOIN loan l ON cl.loan_id = l.loan_id JOIN interest_rate ir ON l.loan_type = ir.loan_type WHERE c.nic = ?";
//           pstm = connection.prepareStatement(sql);
//           pstm.setString(1, nic);
//           resultSet = pstm.executeQuery();
//
//           if (resultSet.next()) {
//               rateId = resultSet.getString("rate_id");
//           } else {
//               System.err.println("No rate ID found for NIC: " + nic);
//           }
//
//       } catch (SQLException e) {
//           System.err.println("Error fetching rate ID: " + e.getMessage());
//           e.printStackTrace();
//       }
//
//       return rateId;
//   }
//    public static String getLoanIdByTypeAndRateId(String loanType, String rateId) {
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        ResultSet resultSet = null;
//        String loanId = null;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "SELECT cl.loan_id FROM customer_loan_details cl " +
//                    "JOIN loan l ON cl.loan_id = l.loan_id " +
//                    "JOIN interest_rate ir ON l.loan_type = ir.loan_type " +
//                    "WHERE l.loan_type = ? AND ir.rate_id = ?";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, loanType);
//            pstm.setString(2, rateId);
//            resultSet = pstm.executeQuery();
//
//            if (resultSet.next()) {
//                loanId = resultSet.getString("loan_id");
//            } else {
//                System.err.println("No loan ID found for Loan Type: " + loanType + " and Rate ID: " + rateId);
//            }
//
//        } catch (SQLException e) {
//            System.err.println("Error fetching loan ID by Type and Rate ID: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return loanId;
//    }


//    public static String getLoanId(String nic) {
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        ResultSet resultSet = null;
//        String loanId = null;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "SELECT cl.loan_id FROM customer_loan_details cl " +
//                    "JOIN customer c ON cl.c_id = c.c_id " +
//                    "WHERE c.nic = ?";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, nic);
//            resultSet = pstm.executeQuery();
//
//            if (resultSet.next()) {
//                loanId = resultSet.getString("loan_id");
//            } else {
//                System.err.println("No loan ID found for NIC: " + nic);
//            }
//
//        } catch (SQLException e) {
//            System.err.println("Error fetching loan ID by NIC: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return loanId;
//    }
//    public static LocalDate getLoanDate(String loanId) throws SQLException {
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        ResultSet resultSet = null;
//        LocalDate loanDate = null;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "SELECT date FROM loan WHERE loan_id = ?";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, loanId);
//            resultSet = pstm.executeQuery();
//
//            if (resultSet.next()) {
//                loanDate = resultSet.getDate("date").toLocalDate();
//            }
//        } catch (SQLException e) {
//            // Handle or log the exception appropriately
//            throw e;
//        }
//
//        return loanDate;
//    }
//    public static String getLoanIdByNIC(String nic) throws SQLException {
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        ResultSet resultSet = null;
//        String loanId = null;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "SELECT loan_id FROM customer_loan_details WHERE c_id = (SELECT c_id FROM customer WHERE nic = ?)";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, nic);
//            resultSet = pstm.executeQuery();
//
//            if (resultSet.next()) {
//                loanId = resultSet.getString("loan_id");
//            }
//        } catch (SQLException e) {
//            System.err.println("Error fetching loan ID by NIC: " + e.getMessage());
//            e.printStackTrace();
//            throw e;
//        }
//
//        return loanId;
//    }
//    public static List<PaymentDTO> getPaymentsByNIC(String nic) throws SQLException {
//        List<PaymentDTO> paymentDTOS = new ArrayList<>();
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "SELECT * FROM payment WHERE nic = ?";
//            statement = connection.prepareStatement(sql);
//            statement.setString(1, nic);
//            resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                PaymentDTO paymentDTO = new PaymentDTO();
//                paymentDTO.setPInvoiceNo(resultSet.getString("p_invoice_no"));
//                paymentDTO.setNic(resultSet.getString("nic"));
//                paymentDTO.setLoanId(resultSet.getString("loan_id"));
//                paymentDTO.setLoanType(resultSet.getString("loan_type"));
//                paymentDTO.setPaymentAmount(String.valueOf(resultSet.getDouble("p_amount")));
//                paymentDTO.setPaymentMethod(resultSet.getString("p_method"));
//                paymentDTO.setLateFee(String.valueOf(resultSet.getDouble("late_fee")));
//                paymentDTO.setPaymentDate(resultSet.getString("p_date"));
//                paymentDTOS.add(paymentDTO);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return paymentDTOS;
//    }
//
//
//}
