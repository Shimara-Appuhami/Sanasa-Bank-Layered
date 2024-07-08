//package lk.ijse.gdse.repository;
//
//import javafx.scene.control.Alert;
//import lk.ijse.gdse.db.DbConnection;
//import lk.ijse.gdse.model.AccountDTO;
//
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AccountRepo {
//    public static boolean save(AccountDTO accountDTO) throws SQLException {
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        boolean isSaved = false;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "INSERT INTO account VALUES (?, ?, ?, ?, ?, ?)";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, accountDTO.getANo());
//            pstm.setString(2, accountDTO.getCId());
//            pstm.setString(3, accountDTO.getAType());
//            pstm.setString(4, accountDTO.getABalance());
//            pstm.setString(5, accountDTO.getOpenDate());
//            pstm.setString(6, accountDTO.getStatus());
//
//            isSaved = pstm.executeUpdate() > 0;
//        } catch (SQLException e){
//            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
//        }
//
//        return isSaved;
//    }



//    public static boolean update(AccountDTO accountDTO) throws SQLException {
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        boolean isUpdated = false;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "UPDATE account SET c_id=?, a_type=?, a_balance=?, open_date=?, status=? WHERE a_no=?";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(6, accountDTO.getANo());
//            pstm.setString(1, accountDTO.getCId());
//            pstm.setString(2, accountDTO.getAType());
//            pstm.setString(3, accountDTO.getABalance());
//            pstm.setString(4, accountDTO.getOpenDate());
//            pstm.setString(5, accountDTO.getStatus());
//
//            isUpdated = pstm.executeUpdate() > 0;
//        } catch (SQLException e){
//            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
//
//        }
//
//        return isUpdated;
//    }
//
//    public static boolean delete(String aNo) throws SQLException {
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        boolean isDeleted = false;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "DELETE FROM account WHERE a_no = ?";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, aNo);
//
//            isDeleted = pstm.executeUpdate() > 0;
//        } catch (SQLException e){
//            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
//        }
//
//        return isDeleted;
//    }

//    public static AccountDTO searchById(String nic) throws SQLException {
//        String sql = "SELECT l.a_no, l.c_id, l.a_type, l.a_balance, l.open_date, l.status " +
//                "FROM account l " +
//                "JOIN customer c ON l.c_id = c.c_id " +
//                "WHERE c.nic = ?";
//
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        ResultSet resultSet = null;
//        AccountDTO accountDTO = null;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, nic);
//            resultSet = pstm.executeQuery();
//
//            if (resultSet.next()) {
//                String aNo1 = resultSet.getString("a_no");
//                String cId = resultSet.getString("c_id");
//                String aType = resultSet.getString("a_type");
//                String aBalance = resultSet.getString("a_balance");
//                String openDate = resultSet.getString("open_date");
//                String status = resultSet.getString("status");
//
//
//                accountDTO = new AccountDTO(aNo1, cId, aType, aBalance, openDate, status);
//            } else {
//                System.out.println("Account not found for ID: " + nic);
//            }
//        } catch (SQLException e){
//            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
//        }
//
//        return accountDTO;
//    }




    /* public static void load(TableView<Inquiry> tableView) {
        String sql = "SELECT * FROM inquiries";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String id = resultSet.getString("in_id");
                String type = resultSet.getString("in_type");
                String inquiryDate = resultSet.getString("in_date");
                String responseDate = resultSet.getString("response_date");
                Inquiry inquiry = new Inquiry(id, type, inquiryDate, responseDate);
                tableView.getItems().add(inquiry);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching inquiries: " + e.getMessage());
        }
    }*/
//    public static List<AccountDTO> getAll() throws SQLException {
//        String sql = "SELECT * FROM account";
//
//        PreparedStatement pstm = DbConnection.getInstance().getConnection()
//                .prepareStatement(sql);
//
//        ResultSet resultSet = pstm.executeQuery();
//
//        List<AccountDTO> accountDTOList = new ArrayList<>();
//
//        while (resultSet.next()) {
//            String aNo=resultSet.getString("a_no");
//            String cId = resultSet.getString("c_id");
//            String aType = resultSet.getString("a_type");
//            String aBalance = resultSet.getString("a_balance");
//            String openDate = resultSet.getString("open_date");
//            String status = resultSet.getString("status");
//            AccountDTO accountDTO = new AccountDTO(aNo,cId, aType,aBalance, openDate, status);
//
//            accountDTOList.add(accountDTO);
//        }
//        return accountDTOList;
//
//    }
//    public static String getLastAccountId() throws SQLException {
//        String sql = "SELECT a_no FROM account ORDER BY a_no DESC LIMIT 1";
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
//                lastId = resultSet.getString("a_no");
//            }
//        }catch (SQLException e){
//            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
//        }
//
//        return lastId;
//    }
//    public static List<String> getIds() throws SQLException {
//        String sql = "SELECT c_id FROM customer";
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
//}