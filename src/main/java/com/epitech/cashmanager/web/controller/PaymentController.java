package com.epitech.cashmanager.web.controller;

import com.epitech.cashmanager.exception.CashManagerException;
import com.epitech.cashmanager.model.Cart;
import com.epitech.cashmanager.service.CartService;
import com.epitech.cashmanager.service.PaymentService;
import com.epitech.cashmanager.tools.PaymentMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CartService cartService;

    @PostMapping("/api/payment/purchase/{paymentMethod}/{paymentId}")
    public void purchaseCart(Principal principal, @PathVariable String paymentMethod, @PathVariable String paymentId) {

        Cart currentCart = cartService.findCurrentCart(principal.getName());

        if (currentCart.getArticlesWithQuantity() == null || currentCart.getArticlesWithQuantity().isEmpty()) {
            throw new CashManagerException("Trying to purchase an empty cart !");
        }

        switch (paymentMethod) {
            case PaymentMethods.CC:
                paymentService.purchaseWithCreditCard(currentCart, paymentId);
                break;
            case PaymentMethods.CHEQUE:
                paymentService.purchaseWithCheque(currentCart, paymentId);
                break;
            default:
                throw new CashManagerException("Unknown payment method");
        }
    }
}
