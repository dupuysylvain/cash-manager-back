package com.epitech.cashmanager.service;

import com.epitech.cashmanager.model.Cart;

public interface PaymentService {
    void purchaseWithCreditCard(Cart cart, String nfcId);
    void purchaseWithCheque(Cart cart, String qrCode);
}
