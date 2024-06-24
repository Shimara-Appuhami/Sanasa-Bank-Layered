package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBO;
import lk.ijse.gdse.model.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBo extends SuperBO {
        public  boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;
        public  boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

        public  boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
         public CustomerDTO searchById(String id) throws SQLException, ClassNotFoundException;
        public List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
        public  String generateNewCustomerID() throws SQLException, ClassNotFoundException;
        public CustomerDTO searchByNicCustomer(String nic) throws SQLException, ClassNotFoundException;




    }
