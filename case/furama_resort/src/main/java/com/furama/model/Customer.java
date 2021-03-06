package com.furama.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {
    @ManyToOne
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @JoinColumn(name = "idCustomerType", referencedColumnName = "customerTypeId")
    private CustomerType customerType;

    @Id
    @GeneratedValue(generator = "prod-generator")
    @GenericGenerator(name = "prod-generator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "CS"),
            strategy = "com.furama.util.IdentityCodeGenerator")
    private String customerId;
    private String customerCode;

    private String customerName;
    private String customerBirthDay;
    private Integer customerGender;
    private String customerCMDD;
    private String customerPhone;
    private String customerEmail;
    private String customerAddress;
    @JsonIgnore
    private  Integer flag ;

    public Customer() {
    }


    public Customer( String customerCode, String customerName, String customerBirthDay, Integer customerGender, String customerCMDD, String customerPhone, String customerEmail, String customerAddress) {

        this.customerCode = customerCode;
        this.customerName = customerName;
        this.customerBirthDay = customerBirthDay;
        this.customerGender = customerGender;
        this.customerCMDD = customerCMDD;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerBirthDay() {
        return customerBirthDay;
    }

    public void setCustomerBirthDay(String customerBirthDay) {
        this.customerBirthDay = customerBirthDay;
    }

    public Integer getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(Integer customerGender) {
        this.customerGender = customerGender;
    }

    public String getCustomerCMDD() {
        return customerCMDD;
    }

    public void setCustomerCMDD(String customerCMDD) {
        this.customerCMDD = customerCMDD;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }


    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
