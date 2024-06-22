package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.custom.CustomerBo;
import lk.ijse.gdse.dao.DAOFactory;
import lk.ijse.gdse.dao.custom.CustomerDAO;
import lk.ijse.gdse.entity.Customer;
import lk.ijse.gdse.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBo {
    CustomerDAO customerDAO= (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    public  boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException{
        return customerDAO.save(new Customer(dto.getCId(), dto.getCName(), dto.getCEmail(), dto.getCContact(), dto.getCAddress(), dto.getCAge(), dto.getDateOfBirth(), dto.getNic(), dto.getRegistrationDate(), dto.getAnnualIncome()));
    }
    public  boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException{
        return customerDAO.update(new Customer(dto.getCId(), dto.getCName(), dto.getCEmail(), dto.getCContact(), dto.getCAddress(), dto.getCAge(), dto.getDateOfBirth(), dto.getNic(), dto.getRegistrationDate(), dto.getAnnualIncome()));
    }
    public  boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException{
        return customerDAO.delete(id);

    }
    public CustomerDTO searchById(String id) throws SQLException, ClassNotFoundException{
        return customerDAO.searchById(id);

    }
    public List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException{
        List<Customer> customers=customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTOS=new ArrayList<>();
        for (Customer c:customers){
            CustomerDTO customerDTO=new CustomerDTO(c.getCId(), c.getCName(), c.getCEmail(), c.getCContact(), c.getCAddress(), c.getCAge(), c.getDateOfBirth(), c.getRegistrationDate(), c.getAnnualIncome(),c.getNic());
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }
    public  String getLastIdCustomer() throws SQLException, ClassNotFoundException{
        return customerDAO.getLastId();
    }

    @Override
    public CustomerDTO searchByNicCustomer(String nic) throws SQLException, ClassNotFoundException {
        return customerDAO.searchByNic("SELECT * FROM customer WHERE nic = ?");
    }


}
