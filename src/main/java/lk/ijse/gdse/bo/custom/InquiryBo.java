package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBO;
import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.Inquiry;
import lk.ijse.gdse.model.EmployeeDTO;
import lk.ijse.gdse.model.InquiryDTO;

import java.sql.SQLException;
import java.util.List;

public interface InquiryBo extends SuperBO {
    boolean saveInquiry(InquiryDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateInquiry(InquiryDTO dto) throws SQLException, ClassNotFoundException;
    boolean deleteInquiry(String id) throws SQLException, ClassNotFoundException;
    InquiryDTO searchByIdInquiry(String id) throws SQLException, ClassNotFoundException;
    List<InquiryDTO> getAllInquiries() throws SQLException, ClassNotFoundException;
    String generateNewInquiryID() throws SQLException, ClassNotFoundException;
    public Inquiry searchByNicInquiry(String nic) throws SQLException, ClassNotFoundException;
    String getCustomerId(String nic) throws SQLException, ClassNotFoundException;

}
