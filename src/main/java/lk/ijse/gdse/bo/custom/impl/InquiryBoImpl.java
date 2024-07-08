package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.SuperBO;
import lk.ijse.gdse.bo.custom.AccountBo;
import lk.ijse.gdse.bo.custom.InquiryBo;
import lk.ijse.gdse.dao.DAOFactory;
import lk.ijse.gdse.dao.custom.InquiryDAO;
import lk.ijse.gdse.entity.Customer;
import lk.ijse.gdse.entity.Inquiry;
import lk.ijse.gdse.model.CustomerDTO;
import lk.ijse.gdse.model.InquiryDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InquiryBoImpl implements InquiryBo {
    InquiryDAO inquiryDAO= (InquiryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INQUIRY);
    @Override
    public boolean saveInquiry(InquiryDTO dto) throws SQLException, ClassNotFoundException {
        return inquiryDAO.save(new Inquiry(dto.getInId(),dto.getNic(),dto.getCId(),dto.getInType(),dto.getInDate(),dto.getResponseDate()));
    }

    @Override
    public boolean updateInquiry(InquiryDTO dto) throws SQLException, ClassNotFoundException {
        return inquiryDAO.update(new Inquiry(dto.getNic(),dto.getCId(),dto.getInType(),dto.getInDate(),dto.getResponseDate(),dto.getInId()));
    }

    @Override
    public boolean deleteInquiry(String id) throws SQLException, ClassNotFoundException {
        return inquiryDAO.delete(id);
    }

    @Override
    public InquiryDTO searchByIdInquiry(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<InquiryDTO> getAllInquiries() throws SQLException, ClassNotFoundException {
        List<Inquiry> inquiries=inquiryDAO.getAll();
        ArrayList<InquiryDTO> inquiryDTOS=new ArrayList<>();
        for (Inquiry dto:inquiries){
            InquiryDTO inquiryDTO=new InquiryDTO(dto.getInId(),dto.getNic(),dto.getCId(),dto.getInType(),dto.getInDate(),dto.getResponseDate());
            inquiryDTOS.add(inquiryDTO);
        }
        return inquiryDTOS;
    }

    @Override
    public String generateNewInquiryID() throws SQLException, ClassNotFoundException {
        return inquiryDAO.generateNewID();
    }

    @Override
    public InquiryDTO searchByNicInquiry(String nic) throws SQLException, ClassNotFoundException {
        Inquiry dto = inquiryDAO.searchById(nic);
        if (dto != null) {
            return new InquiryDTO(dto.getInId(),dto.getNic(),dto.getCId(),dto.getInType(),dto.getInDate(),dto.getResponseDate());
        }
        return null;
    }
}
