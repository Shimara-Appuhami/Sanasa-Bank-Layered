package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.DAOFactory;
import lk.ijse.gdse.dao.SQLUtil;
import lk.ijse.gdse.dao.custom.CustomerDAO;
import lk.ijse.gdse.dao.custom.CustomerLoanDAO;
import lk.ijse.gdse.dao.custom.LoanDAO;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.entity.CustomerLoan;
import lk.ijse.gdse.model.CustomerDTO;
import lk.ijse.gdse.model.CustomerLoanDTO;
import lk.ijse.gdse.model.LoanDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerLoanDAOImpl implements CustomerLoanDAO {


    @Override

    public boolean save(CustomerLoan dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer_loan_details (loan_id, c_id) VALUES (?, ?)",dto.getLoanId(),dto.getCustomerId());
    }

    @Override
    public boolean update(CustomerLoan dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String cId) throws SQLException, ClassNotFoundException {
    return false;
    }

    @Override
    public CustomerLoan searchById(String cId) throws SQLException, ClassNotFoundException {
       return null;
    }

    @Override
    public List<CustomerLoan> getAll() throws SQLException, ClassNotFoundException {
       return null;
    }



    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
      return null;
    }



    @Override
    public CustomerLoan searchByNic(String nic) throws SQLException, ClassNotFoundException {
       return null;
    }

    public boolean saveCustomerLoan(List<CustomerLoanDTO> clList) throws SQLException,ClassNotFoundException {
        if (clList.isEmpty()) {
            return true;
        }

        System.out.println("Saving customer loan details...");

        try {
         //   PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);

            //           DbConnection.getInstance().getConnection().setAutoCommit(false);
            for (CustomerLoanDTO cl : clList) {
               boolean isSaved= SQLUtil.execute("INSERT INTO customer_loan_details (loan_id, c_id) VALUES (?, ?)",cl.getLoanId(), cl.getCustomerId());
//                pst.setString(1, cl.getLoanId());
//                pst.setString(2, cl.getCustomerId());
//                pst.addBatch();
                if (!isSaved){
                    return false;
                }
            }

            System.out.println("CustomerLoan saved");
            return true;

        } catch (SQLException e) {
            System.out.println("Error saving customer loan details: " + e.getMessage());
            // DbConnection.getInstance().getConnection().rollback();
            return false;
        }
        //  finally {
        //   DbConnection.getInstance().getConnection().commit();
        //   DbConnection.getInstance().getConnection().setAutoCommit(true);
        // }
    }


}
