package lk.ijse.gdse.bo;

import lk.ijse.gdse.bo.custom.impl.*;

public class BOFactory {
    public static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBoFactory(){
        return boFactory==null? new BOFactory():boFactory;
    }
    public enum BoTypes{
        CUSTOMER,ACCOUNT,BALANCE,CUSTOMERLOAN,EMPLOYEE,INQUIRY,INTERESTRATE,LOAN,PAYMENT,REMINDER

    }
    public SuperBO getBo(BoTypes boTypes){
        switch (boTypes){
            case CUSTOMER :
                return new CustomerBOImpl();
            case ACCOUNT:
                return new AccountBoImpl();
            case BALANCE :
                return new BalanceBoImpl();
            case CUSTOMERLOAN:
                return new CustomerLoanBoImpl();
            case EMPLOYEE :
                return new EmployeeBoImpl();
            case INQUIRY :
                return new InquiryBoImpl();
            case INTERESTRATE :
                return new InterestRateBoImpl();
            case LOAN:
                return new LoanBoImpl();
            case PAYMENT :
                return new PaymentBoImpl();
            case REMINDER :
                return new ReminderBoImpl();


            default:
                return null;
        }
    }
}
