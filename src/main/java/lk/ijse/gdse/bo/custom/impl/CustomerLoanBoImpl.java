package lk.ijse.gdse.bo.custom.impl;


import lk.ijse.gdse.bo.custom.CustomerLoanBo;
import lk.ijse.gdse.dao.DAOFactory;
import lk.ijse.gdse.dao.custom.CustomerDAO;
import lk.ijse.gdse.dao.custom.CustomerLoanDAO;
import lk.ijse.gdse.model.CustomerLoanDTO;

import java.sql.SQLException;
import java.util.List;

public class CustomerLoanBoImpl implements CustomerLoanBo {
    CustomerLoanDAO customerLoanDAO= (CustomerLoanDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMERLOAN);

    @Override
    public boolean saveCustomerLoan(List<CustomerLoanDTO> clList) throws SQLException,ClassNotFoundException {
        return customerLoanDAO.saveCustomerLoan(clList);
    }
}
