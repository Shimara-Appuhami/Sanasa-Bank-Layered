package lk.ijse.gdse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Customer implements Serializable {
        private String cId;
        private String cName;
        private String cEmail;
        private String cContact;
        private String cAddress;
        private String cAge;
        private String dateOfBirth;
        private String nic;
        private String registrationDate;
        private String annualIncome;


        public Customer(String name, String email, String contact, String address, String age, String birth, String registrationDate, String income, String inId) {
            this.cName = name;
            this.cEmail = email;
            this.cContact = contact;
            this.cAddress = address;
            this.cAge = age;
            this.dateOfBirth = birth;
            this.nic = inId;
            this.registrationDate = registrationDate;
            this.annualIncome = income;
        }


}
