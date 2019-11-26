package com.epitech.cashmanager.service;

import com.epitech.cashmanager.repository.*;
import com.epitech.cashmanager.exception.CashManagerException;
import com.epitech.cashmanager.model.*;
import com.epitech.cashmanager.tools.CartStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ChequeRepository chequeRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private CartRepository cartRepository;

    @Value("${payment.max-attempts}")
    private Integer maxAttempts;

    private HashMap<Integer, Integer> attemptsByCart = new HashMap<>();

    @Override
    public void purchaseWithCreditCard(Cart cart, String nfcId) {
        try {
            launchPurchaseWithCreditCard(cart, nfcId);
        } catch (CashManagerException e) {
            handleAttemptFailed(cart, e.getMessage());
        }
    }

    @Override
    public void purchaseWithCheque(Cart cart, String qrCode) {
        try {
            launchPurchaseWithCheque(cart, qrCode);
        } catch (CashManagerException e) {
            handleAttemptFailed(cart, e.getMessage());
        }
    }

    @Transactional
    public void launchPurchaseWithCreditCard(Cart cart, String nfcId) {
        Account account = retrieveAccountFromNfcId(nfcId);
        blockArticlesAndProceedPayment(cart, account);
    }

    @Transactional
    public void launchPurchaseWithCheque(Cart cart, String qrCode) {
        Cheque cheque = retrieveChequeFromQrCode(qrCode);
        Account account = cheque.getAccount();

        if (!cart.getTotalAmount().equals(cheque.getValue())) {
            throw new CashManagerException("The amount of the check is not good");
        }

        cheque.setValue(BigDecimal.ZERO);
        chequeRepository.save(cheque);
        blockArticlesAndProceedPayment(cart, account);
    }

    private Account retrieveAccountFromNfcId(String nfcId) {
        CreditCard cc = creditCardRepository.findFirstByNfcId(nfcId);

        if (cc == null) {
            throw new CashManagerException("Unknown nfcId");
        }

        return cc.getAccount();
    }

    private Cheque retrieveChequeFromQrCode(String qrCode) {
        Cheque cheque = chequeRepository.findFirstByQrCode(qrCode);

        if (cheque == null) {
            throw new CashManagerException("Unknown qrCode");
        }

        return cheque;
    }

    public void blockArticlesAndProceedPayment(Cart cart, Account account) {
        BigDecimal newBalance = account.getBalance().subtract(cart.getTotalAmount());

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new CashManagerException("Not enough money in your account !");
        }

        for (CartQuantity cq : cart.getArticlesWithQuantity()) {
            Article article = cq.getArticle();
            int newQuantity = article.getQuantity() - cq.getQuantity();

            if (newQuantity < 0) {
                throw new CashManagerException("not enough quantity for article :" + article.getName());
            }

            article.setQuantity(newQuantity);
        }

        cart.setStatus(CartStatus.DONE);
        cartRepository.save(cart);

        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    private void handleAttemptFailed(Cart cart, String message) {
        int attempts = 1;

        if (attemptsByCart.containsKey(cart.getId())) {
            attempts = attemptsByCart.get(cart.getId()) + 1;
        }

        if (maxAttempts.equals(attempts)) {
            // revert cart
            attemptsByCart.remove(cart.getId());
            cart.setStatus(CartStatus.CANCELLED);
            cartRepository.save(cart);
            message = message + " (the cart was cancelled because to much attempts failed : " + maxAttempts + ")";
        } else {
            attemptsByCart.put(cart.getId(), attempts);
        }

        throw new CashManagerException(message);
    }
}
