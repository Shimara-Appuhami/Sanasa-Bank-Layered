package lk.ijse.gdse.dao;

import lk.ijse.gdse.bo.custom.impl.*;
import lk.ijse.gdse.dao.custom.impl.*;
import lk.ijse.gdse.entity.Account;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory() {
        return daoFactory==null? new DAOFactory():daoFactory;
    }
    public enum DAOTypes{
        CUSTOMER,ACCOUNT,BALANCE,CUSTOMERLOAN,EMPLOYEE,INQUIRY,INTERESTRATE,LOAN,PAYMENT,REMINDER,USER,LOGIN
    }
    public SuperDAO getDAO(DAOTypes daoTypes ){
        switch (daoTypes){
            case CUSTOMER :
                return new CustomerDAOImpl();
            case ACCOUNT:
                return new AccountDAOImpl();
            case BALANCE :
                return new BalanceDAOImpl();
            case CUSTOMERLOAN:
                return new CustomerLoanDAOImpl();
            case EMPLOYEE :
                return new EmployeeDAOImpl();
            case INQUIRY :
                return new InquiryDAOImpl();
            case INTERESTRATE :
                return new InterestRateDAOImpl();
            case LOAN:
                return new LoanDAOImpl();
            case PAYMENT :
                return new PaymentDAOImpl();
            case REMINDER :
                return new ReminderDAOImpl();
                case USER :
                    return new UserDAOImpl();
                case LOGIN :
                    return new LoginDAOImpl();



            default:
                return null;
        }
    }
}
