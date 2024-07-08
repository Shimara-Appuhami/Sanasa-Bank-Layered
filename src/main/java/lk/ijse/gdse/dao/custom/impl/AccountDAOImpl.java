package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.SQLUtil;
import lk.ijse.gdse.dao.custom.AccountDAO;
import lk.ijse.gdse.entity.Account;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {
    @Override
    public boolean save(Account dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO account VALUES (?, ?, ?, ?, ?, ?)",dto.getANo(),dto.getCId(),dto.getAType(),dto.getABalance(),dto.getOpenDate(),dto.getStatus());

    }

    @Override
    public boolean update(Account dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE account SET c_id=?, a_type=?, a_balance=?, open_date=?, status=? WHERE a_no=?",dto.getCId(),dto.getAType(),dto.getABalance(),dto.getOpenDate(),dto.getStatus(),dto.getANo());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM account WHERE a_no = ?",id);
    }

    @Override
    public Account searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Account> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Account> allAccount = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM account");
        while (rst.next()) {
            Account account = new Account(rst.getString("a_no"), rst.getString("c_id"), rst.getString("a_type"), rst.getString("a_balance"), rst.getString("open_date"), rst.getString("status"));
            allAccount.add(account);
        }
        return allAccount;

    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT a_no FROM account ORDER BY a_no DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("a_no");
            int newCustomerId = Integer.parseInt(id.replace("A", "")) + 1;
            return String.format("A%03d", newCustomerId);
        } else {
            return "A001";
        }
    }



    @Override
    public Account searchByNic(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT l.a_no, l.c_id, l.a_type, l.a_balance, l.open_date, l.status FROM account l JOIN customer c ON l.c_id = c.c_id WHERE c.nic = ?",nic);
        if (rst.next()) {
            return new Account(
                    rst.getString("a_no"),
                    rst.getString("a_id"),
                    rst.getString("a_type"),
                    rst.getString("a_balance"),
                    rst.getString("open_date"),
                    rst.getString("status")
            );
        }
        return null;
    }

  public List<String>getId() throws SQLException, ClassNotFoundException {
      ResultSet rst = SQLUtil.execute("SELECT c_id FROM customer");
      ArrayList<String> idList = new ArrayList<>();
      while (rst.next()) {
          idList.add(rst.getString("c_id"));
      }
      return idList;
  }

    @Override
    public String getCustomerId(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT c_id FROM customer WHERE nic =?", nic);
        if (rst.next()) {
            return rst.getString("c_id");
        }
        return null;
    }


}
