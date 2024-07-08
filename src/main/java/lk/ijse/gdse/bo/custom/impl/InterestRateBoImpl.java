package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.SuperBO;
import lk.ijse.gdse.bo.custom.AccountBo;
import lk.ijse.gdse.bo.custom.InterestRateBo;
import lk.ijse.gdse.dao.DAOFactory;
import lk.ijse.gdse.dao.custom.InterestRateDAO;
import lk.ijse.gdse.entity.Inquiry;
import lk.ijse.gdse.entity.InterestRate;
import lk.ijse.gdse.model.InquiryDTO;
import lk.ijse.gdse.model.InterestRateDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InterestRateBoImpl implements InterestRateBo {
    InterestRateDAO interestRateDAO= (InterestRateDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INTERESTRATE);
    @Override
    public boolean saveInterestRate(InterestRateDTO dto) throws SQLException, ClassNotFoundException {
        return interestRateDAO.save(new InterestRate(dto.getRateId(),dto.getLoanType(),dto.getPercentage()));
    }

    @Override
    public boolean updateInterestRate(InterestRateDTO dto) throws SQLException, ClassNotFoundException {
        return interestRateDAO.update(new InterestRate(dto.getLoanType(),dto.getPercentage(),dto.getRateId()));
    }

    @Override
    public boolean deleteInterestRate(String id) throws SQLException, ClassNotFoundException {
        return interestRateDAO.delete(id);
    }

    @Override
    public InterestRateDTO searchByIdInterestRate(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<InterestRateDTO> getAllInterestRates() throws SQLException, ClassNotFoundException {
        List<InterestRate> interestRates=interestRateDAO.getAll();
        ArrayList<InterestRateDTO> inquiryDTOS=new ArrayList<>();
        for (InterestRate dto:interestRates){
            InterestRateDTO interestRateDTO=new InterestRateDTO(dto.getRateId(),dto.getLoanType(),dto.getPercentage());
            inquiryDTOS.add(interestRateDTO);
        }
        return inquiryDTOS;

    }

    @Override
    public String generateNewInterestRateID() throws SQLException, ClassNotFoundException {
        return interestRateDAO.generateNewID();
    }

    @Override
    public InterestRateDTO searchByNicCustomer(String loanType) throws SQLException, ClassNotFoundException {
        InterestRate dto = interestRateDAO.searchByNic(loanType);
        if (dto != null) {
            return new InterestRateDTO(dto.getRateId(),dto.getLoanType(),dto.getPercentage());
        }
        return null;
    }
}