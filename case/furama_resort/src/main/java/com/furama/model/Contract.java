package com.furama.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Contract {
    @ManyToOne
    @JoinColumn(referencedColumnName = "employeeId")
    private Employee idEmployee;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(referencedColumnName = "customerId")
    private Customer idCustomer;
    @ManyToOne
    @JoinColumn(referencedColumnName = "serviceId")
    private Service idService;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;
    private String contractStartDay;
    private String contractEndDay;
    private Double contractDeposit;
    private Double contractToTalMoney;


    public Contract() {
    }

    public Contract(Employee idEmployee, Customer idCustomer, Service idService, List<ContractDetail> contractDetails, Long contractId, String contractStartDay, String contractEndDay, Double contractDeposit, Double contractToTalMoney) {
        this.idEmployee = idEmployee;
        this.idCustomer = idCustomer;
        this.idService = idService;
        this.contractId = contractId;
        this.contractStartDay = contractStartDay;
        this.contractEndDay = contractEndDay;
        this.contractDeposit = contractDeposit;
        this.contractToTalMoney = contractToTalMoney;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getContractStartDay() {
        return contractStartDay;
    }

    public void setContractStartDay(String contractStartDay) {
        this.contractStartDay = contractStartDay;
    }

    public String getContractEndDay() {
        return contractEndDay;
    }

    public void setContractEndDay(String contractEndDay) {
        this.contractEndDay = contractEndDay;
    }

    public Double getContractDeposit() {
        return contractDeposit;
    }

    public void setContractDeposit(Double contractDeposit) {
        this.contractDeposit = contractDeposit;
    }

    public Double getContractToTalMoney() {
        return contractToTalMoney;
    }

    public void setContractToTalMoney(Double contractToTalMoney) {
        this.contractToTalMoney = contractToTalMoney;
    }

    public Employee getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Employee idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Customer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Customer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Service getIdService() {
        return idService;
    }

    public void setIdService(Service idService) {
        this.idService = idService;
    }


    @Override
    public String toString() {
        return
                "Customer=" + idCustomer.getCustomerName() +
                        ", Service=" + idService.getServiceName();

    }
}
