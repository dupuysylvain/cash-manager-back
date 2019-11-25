package com.epitech.cashmanager.service;

import com.epitech.cashmanager.repository.*;
import com.epitech.cashmanager.exception.CashManagerException;
import com.epitech.cashmanager.model.*;
import com.epitech.cashmanager.tools.CartStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ChequeRepository chequeRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    @Transactional
    public void purchaseWithCreditCard(Cart cart, String nfcId) {
        Account account = retrieveAccountFromNfcId(nfcId);

        blockArticlesAndProceedPayment(cart, account);
    }

    @Override
    @Transactional
    public void purchaseWithCheque(Cart cart, String qrCode) {
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
}
