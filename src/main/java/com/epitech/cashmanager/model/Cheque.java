package com.epitech.cashmanager.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="cheque")
public class Cheque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "value")
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Cheque() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Check{" +
                "id=" + id +
                ", qrCode='" + qrCode + '\'' +
                ", value=" + value +
                ", account=" + account +
                '}';
    }
}
