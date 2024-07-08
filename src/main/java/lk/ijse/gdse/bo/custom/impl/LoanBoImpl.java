package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.custom.LoanBo;
import lk.ijse.gdse.dao.DAOFactory;
import lk.ijse.gdse.dao.custom.LoanDAO;
import lk.ijse.gdse.entity.InterestRate;
import lk.ijse.gdse.entity.Loan;
import lk.ijse.gdse.model.LoanDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanBoImpl implements LoanBo {
    LoanDAO loanDAO = (LoanDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOAN);

    @Override
    public boolean saveLoan(LoanDTO dto) throws SQLException, ClassNotFoundException {
        return loanDAO.save(new Loan(
                dto.getLoanId(),
                dto.getCustomerId(),
                dto.getApplication(),
                dto.getLoanAmount(),
                dto.getLoanType(),
                dto.getLoanTerm(),
                dto.getCollateral(),
                dto.getPurpose(),
                dto.getPercentage(),
                dto.getNic(),
                dto.getDate()));
    }

    @Override
    public boolean updateLoan(LoanDTO dto) throws SQLException, ClassNotFoundException {
        return loanDAO.update(new Loan(
                dto.getCustomerId(),
                dto.getApplication(),
                dto.getLoanAmount(),
                dto.getLoanType(),
                dto.getLoanTerm(),
                dto.getCollateral(),
                dto.getPurpose(),
                dto.getPercentage(),
                dto.getNic(),
                dto.getDate(),
                dto.getLoanId()));
    }

    @Override
    public boolean deleteLoan(String loanId) throws SQLException, ClassNotFoundException {
        return loanDAO.delete(loanId);
    }

    @Override
    public LoanDTO searchByIdLoan(String loanId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<LoanDTO> getAllLoans() throws SQLException, ClassNotFoundException {
        List<Loan> loans = loanDAO.getAll();
        ArrayList<LoanDTO> loanDTOS = new ArrayList<>();
        for (Loan dto : loans) {
            loanDTOS.add(new LoanDTO(dto.getLoanId(), dto.getApplication(), dto.getLoanAmount(), dto.getLoanType(),
                    dto.getLoanTerm(), dto.getCollateral(), dto.getPurpose(), dto.getCustomerId(), dto.getPercentage(),
                    dto.getNic(), dto.getDate()));
        }
        return loanDTOS;
    }


    @Override
    public String generateNewLoanID() throws SQLException, ClassNotFoundException {
        return loanDAO.generateNewID();
    }

    @Override
    public LoanDTO searchByNic(String nic) throws SQLException, ClassNotFoundException {
        Loan dto = loanDAO.searchByNic(nic);
        if (dto != null) {
            return new LoanDTO(dto.getLoanId(), dto.getApplication(), dto.getLoanAmount(), dto.getLoanType(),
                    dto.getLoanTerm(), dto.getCollateral(), dto.getPurpose(), dto.getCustomerId(), dto.getPercentage(),
                    dto.getNic(), dto.getDate());
        }
        return null;
    }

    @Override
    public String getCustomerId(String nic) throws SQLException, ClassNotFoundException {
        return loanDAO.getCustomerId(nic);
    }

    @Override
    public InterestRate searchByLoanType(String loanType) throws SQLException, ClassNotFoundException {
        return loanDAO.searchByLoanType(loanType);
    }
}
