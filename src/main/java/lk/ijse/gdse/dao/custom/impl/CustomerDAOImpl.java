package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.SQLUtil;
import lk.ijse.gdse.dao.custom.CustomerDAO;
import lk.ijse.gdse.entity.Customer;
import lk.ijse.gdse.model.CustomerDTO;
import lk.ijse.gdse.model.EmployeeDTO;
import lk.ijse.gdse.model.tm.CustomerTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?,?)",  customer.getCId(), customer.getCName(), customer.getCEmail(), customer.getCContact(),
                customer.getCAddress(), customer.getCAge(), customer.getDateOfBirth(), customer.getNic(),
                customer.getRegistrationDate(), customer.getAnnualIncome());
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "UPDATE customer SET c_name=?, c_email=?, c_contact=?, c_address=?, c_age=?, date_of_birth=?,nic=?, registration_date=?, annual_income=? WHERE c_id=?",   customer.getCName(), customer.getCEmail(), customer.getCContact(),
                customer.getCAddress(), customer.getCAge(), customer.getDateOfBirth(), customer.getNic(),
                customer.getRegistrationDate(), customer.getAnnualIncome(),customer.getCId());
    }

    @Override
    public boolean delete(String cId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE c_id = ?",cId);
    }

    @Override
    public Customer searchById(String cId) throws SQLException, ClassNotFoundException {
        ResultSet rst= SQLUtil.execute("SELECT * FROM customer WHERE c_id = ?");
        if(rst.next()){
            return new Customer(rst.getString("cId"), rst.getString("cName"), rst.getString("cEmail"), rst.getString("cContact"), rst.getString("cAddress"), rst.getString("cAge"), rst.getString("dateOfBirth"), rst.getString("nic"), rst.getString("registrationDate"), rst.getString("annualIncome"));
        }
        return null;

    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer");
        while (rst.next()) {
            Customer customerDTO = new Customer(rst.getString("c_id"), rst.getString("c_name"), rst.getString("c_email"), rst.getString("c_contact"), rst.getString("c_address"), rst.getString("c_age"), rst.getString("date_of_birth"), rst.getString("nic"), rst.getString("registration_date"), rst.getString("annual_income"));
            allCustomers.add(customerDTO);
        }
        return allCustomers;
    }



    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT c_id FROM customer ORDER BY c_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("c_id");
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        } else {
            return "C001";
        }
    }

    @Override
    public Customer searchByNic(String nic) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE nic=?", nic );
        resultSet.next();
        return new Customer(
                resultSet.getString("cId"),
                resultSet.getString("cName"),
                resultSet.getString("cEmail"),
                resultSet.getString("cContact"),
                resultSet.getString("cAddress"),
                resultSet.getString("cAge"),
                resultSet.getString("dateOfBirth"),
                resultSet.getString("nic"),
                resultSet.getString("registrationDate"),
                resultSet.getString("annualIncome")
        );
    }


}

