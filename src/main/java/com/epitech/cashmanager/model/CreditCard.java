package com.epitech.cashmanager.model;

import javax.persistence.*;

@Entity
@Table(name="credit_card")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nfc_id")
    private String nfcId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public CreditCard() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNfcId() {
        return nfcId;
    }

    public void setNfcId(String nfcId) {
        this.nfcId = nfcId;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", nfcId='" + nfcId + '\'' +
                ", account=" + account +
                '}';
    }
}
