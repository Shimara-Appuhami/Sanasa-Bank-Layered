package lk.ijse.gdse.dao;

import lk.ijse.gdse.model.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T>extends SuperDAO {
    public  boolean save(T dto) throws SQLException, ClassNotFoundException;
    public  boolean update(T dto) throws SQLException, ClassNotFoundException;
    public  boolean delete(String id) throws SQLException, ClassNotFoundException;
    public CustomerDTO searchById(String id) throws SQLException, ClassNotFoundException;
    public  List<T> getAll() throws SQLException, ClassNotFoundException;
    public  String getLastId() throws SQLException, ClassNotFoundException;
    public CustomerDTO searchByNic(String nic) throws SQLException, ClassNotFoundException;

    }