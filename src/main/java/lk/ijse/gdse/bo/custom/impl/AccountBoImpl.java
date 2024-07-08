package lk.ijse.gdse.bo.custom.impl;


import lk.ijse.gdse.bo.custom.AccountBo;
import lk.ijse.gdse.dao.DAOFactory;
import lk.ijse.gdse.dao.custom.AccountDAO;
import lk.ijse.gdse.entity.Account;
import lk.ijse.gdse.model.AccountDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountBoImpl implements AccountBo {
   AccountDAO accountDAO= (AccountDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ACCOUNT);
   public boolean saveAccount(AccountDTO account) throws SQLException, ClassNotFoundException {
      return accountDAO.save(new Account(account.getANo(), account.getCId(), account.getAType(), account.getABalance(), account.getOpenDate(),account.getStatus()));
   }


   public boolean updateAccount(AccountDTO account) throws SQLException, ClassNotFoundException {
      return accountDAO.update(new Account(account.getANo(), account.getCId(), account.getAType(), account.getABalance(), account.getOpenDate(),account.getStatus()));

   }


   public boolean deleteAccount(String id) throws SQLException, ClassNotFoundException {
      return accountDAO.delete(id);
   }


   public AccountDTO searchByIdAccount(String id) throws SQLException, ClassNotFoundException {
      return null;
   }

   @Override
   public List<AccountDTO> getAllAccounts() throws SQLException, ClassNotFoundException {
      List<Account> accounts = accountDAO.getAll();
      List<AccountDTO> accountDTOS = new ArrayList<>();
      for (Account account : accounts) {
         accountDTOS.add(new AccountDTO(account.getANo(), account.getCId(), account.getAType(), account.getABalance(), account.getOpenDate(),account.getStatus()));
      }
      return accountDTOS;
   }

   @Override
   public String generateNewAccountId() throws SQLException, ClassNotFoundException {
      return accountDAO.generateNewID();
   }

   @Override
   public AccountDTO searchByNicAccount(String nic) throws SQLException, ClassNotFoundException {
      Account account = accountDAO.searchByNic(nic);
      if (account != null) {
         return new AccountDTO(account.getANo(), account.getCId(), account.getAType(), account.getABalance(), account.getOpenDate(), account.getStatus());
      }
      return null;
      }

   @Override
   public List<String> getIdsAccount() throws SQLException, ClassNotFoundException {
      return accountDAO.getId();
   }

   @Override
   public String getCustomerId(String nic) throws SQLException, ClassNotFoundException {
      return accountDAO.getCustomerId(nic);
   }

}
