package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.SuperBO;
import lk.ijse.gdse.bo.custom.AccountBo;
import lk.ijse.gdse.bo.custom.BalanceBo;
import lk.ijse.gdse.dao.DAOFactory;
import lk.ijse.gdse.dao.custom.BalanceDAO;
import lk.ijse.gdse.entity.Balance;
import lk.ijse.gdse.entity.Customer;
import lk.ijse.gdse.entity.Reminder;
import lk.ijse.gdse.model.BalanceDTO;
import lk.ijse.gdse.model.CustomerDTO;
import lk.ijse.gdse.model.tm.BalanceTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BalanceBoImpl implements BalanceBo {
    BalanceDAO balanceDAO= (BalanceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BALANCE);
    @Override
    public boolean saveBalance(BalanceDTO dto) throws SQLException, ClassNotFoundException {
        return balanceDAO.save(new Balance(dto.getBId(),dto.getLoanId(),dto.getPrincipalBalance(),dto.getInterestBalance(),dto.getTotalBalance(),dto.getLastUpdatedDate()));
    }

    @Override
    public boolean updateBalance(BalanceDTO dto) throws SQLException, ClassNotFoundException {
        return balanceDAO.update(new Balance(dto.getLoanId(),dto.getPrincipalBalance(),dto.getInterestBalance(),dto.getTotalBalance(),dto.getLastUpdatedDate(),dto.getBId()));
    }

    @Override
    public boolean deleteBalance(String id) throws SQLException, ClassNotFoundException {
        return balanceDAO.delete(id);
    }

    @Override
    public BalanceDTO searchByIdBalance(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<BalanceDTO> getAllBalances() throws SQLException, ClassNotFoundException {
        List<Balance> balances=balanceDAO.getAll();
        ArrayList<BalanceDTO> balanceDTOS=new ArrayList<>();
        for (Balance dto:balances){
            BalanceDTO balanceDTO=new BalanceDTO(dto.getBId(),dto.getLoanId(),dto.getPrincipalBalance(),dto.getInterestBalance(),dto.getTotalBalance(),dto.getLastUpdatedDate());
            balanceDTOS.add(balanceDTO);
        }
        return balanceDTOS;

    }

    @Override
    public String generateNewBalanceId() throws SQLException, ClassNotFoundException {
        return balanceDAO.generateNewID();
    }

    @Override
    public BalanceDTO searchByNicBalance(String nic) throws SQLException, ClassNotFoundException {
        Balance balance = balanceDAO.searchByNic(nic);
        if (balance != null) {
            return new BalanceDTO(balance.getBId(), balance.getLoanId(), balance.getPrincipalBalance(), balance.getInterestBalance(), balance.getTotalBalance(), balance.getLastUpdatedDate());
        }
        return null;
    }

    @Override
    public BalanceDTO calculateBalance(String nic, double loanAmount, double interestRate) throws SQLException, ClassNotFoundException {
    return balanceDAO.calculatePaymentAmount(nic, loanAmount, interestRate);
    }

    @Override
    public String getAmount(String nic) throws SQLException, ClassNotFoundException {
        return balanceDAO.getAmount(nic);
    }

    @Override
    public String getPayBalance(String nic) throws SQLException, ClassNotFoundException {
        return balanceDAO.getPayBalance(nic);
    }

    @Override
    public String getTotalBalance(String nic) throws SQLException, ClassNotFoundException {
    return balanceDAO.getTotalBalance(nic);    }

    @Override
    public String getLastPaymentDate(String nic) throws SQLException, ClassNotFoundException {
        return balanceDAO.getLastPaymentDate(nic);
    }

    @Override
    public String getLoanId(String nic) throws SQLException, ClassNotFoundException {
        return balanceDAO.getLoanId(nic);
    }

}
