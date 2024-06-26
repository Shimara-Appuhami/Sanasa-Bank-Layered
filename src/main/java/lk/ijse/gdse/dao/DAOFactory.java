package lk.ijse.gdse.dao;

import lk.ijse.gdse.dao.custom.impl.CustomerDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory() {
        return daoFactory==null? new DAOFactory():daoFactory;
    }
    public enum DAOTypes{
        CUSTOMER
    }
    public SuperDAO getDAO(DAOTypes daoTypes ){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();

            default:
                return null;
        }
    }
}
