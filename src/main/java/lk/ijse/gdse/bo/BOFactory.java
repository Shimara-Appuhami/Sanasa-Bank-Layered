package lk.ijse.gdse.bo;

import lk.ijse.gdse.bo.custom.impl.CustomerBOImpl;

public class BOFactory {
    public static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBoFactory(){
        return boFactory==null? new BOFactory():boFactory;
    }
    public enum BoTypes{
        CUSTOMER

    }
    public SuperBO getBo(BoTypes boTypes){
        switch (boTypes){
            case CUSTOMER :
                return new CustomerBOImpl();

            default:
                return null;
        }
    }
}
