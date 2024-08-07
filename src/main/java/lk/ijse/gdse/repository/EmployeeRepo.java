//package lk.ijse.gdse.repository;
//
//import javafx.scene.control.Alert;
//import lk.ijse.gdse.db.DbConnection;
//import lk.ijse.gdse.model.EmployeeDTO;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class EmployeeRepo {
//
//    public static boolean save(EmployeeDTO employeeDTO) throws SQLException {
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        boolean isSaved = false;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "INSERT INTO employee (e_id, e_name, e_contact, e_address, e_salary, position) VALUES (?, ?, ?, ?, ?, ?)";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, employeeDTO.getEmployeeId());
//            pstm.setString(2, employeeDTO.getEmployeeName());
//            pstm.setString(3, employeeDTO.getEmployeeContact());
//            pstm.setString(4, employeeDTO.getEmployeeAddress());
//            pstm.setString(5, employeeDTO.getEmployeeSalary());
//            pstm.setString(6, employeeDTO.getPosition());
//
//            isSaved = pstm.executeUpdate() > 0;
//        }catch (SQLException e){
//            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
//        }
//
//        return isSaved;
//    }
//
//
//    public static boolean update(EmployeeDTO employeeDTO) throws SQLException {
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        boolean isUpdated = false;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "UPDATE employee SET e_name=?, e_contact=?, e_address=?, e_salary=?, position=? WHERE e_id=?";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, employeeDTO.getEmployeeName());
//            pstm.setString(2, employeeDTO.getEmployeeContact());
//            pstm.setString(3, employeeDTO.getEmployeeAddress());
//            pstm.setString(4, employeeDTO.getEmployeeSalary());
//            pstm.setString(5, employeeDTO.getPosition());
//            pstm.setString(6, employeeDTO.getEmployeeId());
//
//            isUpdated = pstm.executeUpdate() > 0;
//        }catch (SQLException e){
//            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
//        }
//
//        return isUpdated;
//    }
//
//    public static boolean delete(String eId) throws SQLException {
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        boolean isDeleted = false;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "DELETE FROM employee WHERE e_id=?";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, eId);
//
//            isDeleted = pstm.executeUpdate() > 0;
//        }catch (SQLException e){
//            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
//        }
//        return isDeleted;
//    }
//
//    public static EmployeeDTO searchById(String eId) throws SQLException {
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        ResultSet resultSet = null;
//        EmployeeDTO employeeDTO = null;
//
//        try {
//            connection = DbConnection.getInstance().getConnection();
//            String sql = "SELECT * FROM employee WHERE e_id=?";
//            pstm = connection.prepareStatement(sql);
//            pstm.setString(1, eId);
//            resultSet = pstm.executeQuery();
//
//            if (resultSet.next()) {
//                String eName = resultSet.getString("e_name");
//                String eContact = resultSet.getString("e_contact");
//                String eAddress = resultSet.getString("e_address");
//                String eSalary = resultSet.getString("e_salary");
//                String position = resultSet.getString("position");
//
//                employeeDTO = new EmployeeDTO(eId, eName, eContact, eAddress, eSalary, position);
//            }
//        }catch (SQLException e){
//            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
//        }
//
//        return employeeDTO;
//    }
//
//
//
//    public static List<EmployeeDTO> getAll() throws SQLException {
//        String sql = "SELECT * FROM employee";
//
//        PreparedStatement pstm = DbConnection.getInstance().getConnection()
//                .prepareStatement(sql);
//
//        ResultSet resultSet = pstm.executeQuery();
//
//        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
//
//        while (resultSet.next()) {
//            String eId=resultSet.getString("e_id");
//            String eName = resultSet.getString("e_name");
//            String eContact = resultSet.getString("e_contact");
//            String eAddress = resultSet.getString("e_address");
//            String eSalary = resultSet.getString("e_salary");
//            String position = resultSet.getString("position");
//
//            EmployeeDTO employeeDTO = new EmployeeDTO(eId, eName, eContact, eAddress, eSalary, position);
//
//            employeeDTOList.add(employeeDTO);
//        }
//        return employeeDTOList;
//
//    }
//    public static String getLastEmployeeId() throws SQLException {
//        String sql = "SELECT e_id FROM employee ORDER BY e_id DESC LIMIT 1";
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
//                lastId = resultSet.getString("e_id");
//            }
//        }catch (SQLException e){
//            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
//        }
//
//        return lastId;
//    }
//
//    public static List<String> getIds() throws SQLException {
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
//}
