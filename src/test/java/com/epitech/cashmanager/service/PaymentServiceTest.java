package com.epitech.cashmanager.service;

import com.epitech.cashmanager.exception.CashManagerException;
import com.epitech.cashmanager.model.*;
import com.epitech.cashmanager.repository.AccountRepository;
import com.epitech.cashmanager.repository.CartRepository;
import com.epitech.cashmanager.repository.ChequeRepository;
import com.epitech.cashmanager.repository.CreditCardRepository;
import com.epitech.cashmanager.tools.CartStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PaymentServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ChequeRepository chequeRepository;

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Account account;

    private Cart cart;

    private Cheque cheque;

    @Before
    public void init() {
        cart = initCart();

        account = new Account();
        account.setBalance(new BigDecimal(5000));

        CreditCard cc = new CreditCard();
        cc.setNfcId("694386");
        cc.setAccount(account);

        cheque = new Cheque();
        cheque.setValue(new BigDecimal(1200));
        cheque.setAccount(account);
        cheque.setQrCode("380265");

        Cheque chequeTwo = new Cheque();
        chequeTwo.setValue(new BigDecimal(500));
        chequeTwo.setAccount(account);
        chequeTwo.setQrCode("380266");

        paymentService.setMaxAttempts(2);

        when(creditCardRepository.findFirstByNfcId("694386")).thenReturn(cc);
        when(chequeRepository.findFirstByQrCode("380265")).thenReturn(cheque);
        when(chequeRepository.findFirstByQrCode("380266")).thenReturn(chequeTwo);
    }

    @Test
    public void purchaseWithCreditCardTest(){

        assertEquals("start with 5000", new BigDecimal(5000), account.getBalance());

        paymentService.purchaseWithCreditCard(cart, "694386");

        assertEquals("account has 3800 (5000-1200)", new BigDecimal(3800), account.getBalance());
        assertEquals("cart is now done", CartStatus.DONE, (Object)cart.getStatus());
        assertEquals("left 19 screens", 19, getArticleQuantity(cart, 0));
        assertEquals("left 9 iphone", 9, getArticleQuantity(cart, 1));
        assertEquals("left 98 headphones", 98, getArticleQuantity(cart, 2));

        // reset cart
        cart.setStatus(CartStatus.NEW);

        // first attempt failed
        try {
            paymentService.purchaseWithCreditCard(cart, "111111");
            fail("The method should throw an exception");
        } catch (CashManagerException e){
            // An exception should be throw because the nfcID '111111' not exist
            assertNotNull(e);
            assertEquals("cart should not be canceled", CartStatus.NEW, (Object)cart.getStatus());
        }

        // second attempt failed -> cart canceled
        try {
            paymentService.purchaseWithCreditCard(cart, "111111");
            fail("The method should throw an exception");
        } catch (CashManagerException e){
            // An exception should be throw because the nfcID '111111' not exist
            assertNotNull(e);
            assertEquals("cart should be canceled (2 max attempts)", CartStatus.CANCELLED, (Object)cart.getStatus());
        }

        // reset cart
        cart.setStatus(CartStatus.NEW);
        // set 10 iphone
        cart.getArticlesWithQuantity().stream().filter(cq -> cq.getArticle().getId() == 1).findFirst().get().setQuantity(10);

        try {
            paymentService.purchaseWithCreditCard(cart, "694386");
            fail("The method should throw an exception");
        } catch (CashManagerException e){
            // An exception should be throw because not enough money
            assertNotNull(e);
        }

        account.setBalance(new BigDecimal(100000));

        // try to purchase more article than available
        try {
            paymentService.purchaseWithCreditCard(cart, "694386");
            fail("The method should throw an exception");
        } catch (CashManagerException e){
            // An exception should be throw because the article is not available (9 in stock but want to have 10)
            assertNotNull(e);
        }
    }

    @Test
    public void purchaseWithCheque() {

        // Unknown qrCode
        try {
            paymentService.purchaseWithCheque(cart, "383265");
            fail("The method should throw an exception");
        } catch (CashManagerException e) {
            // An exception should be throw because the qrCode is unknown
            assertNotNull(e);
        }

        // cheque money too low
        try {
            paymentService.purchaseWithCheque(cart, "380266");
            fail("The method should throw an exception");
        } catch (CashManagerException e) {
            // An exception should be throw because the cheque money too low
            assertNotNull(e);
        }

        paymentService.purchaseWithCheque(cart, "380265");

        assertEquals("cheque is now used", BigDecimal.ZERO, cheque.getValue());
    }

    private Cart initCart() {
        Article screen = new Article();
        screen.setId(0);
        screen.setQuantity(20);
        screen.setBarcode("12345678");
        screen.setPrice(new BigDecimal(200));
        screen.setName("MSI Screen 144hz");

        Article mobile = new Article();
        mobile.setId(1);
        mobile.setQuantity(10);
        mobile.setBarcode("69673206");
        mobile.setPrice(new BigDecimal(900));
        mobile.setName("Iphone X");

        Article headphone = new Article();
        headphone.setId(2);
        headphone.setQuantity(100);
        headphone.setBarcode("69309206");
        headphone.setPrice(new BigDecimal(50));
        headphone.setName("Headphone");

        CartQuantity screenCartQuantity = new CartQuantity();
        screenCartQuantity.setArticle(screen);
        screenCartQuantity.setQuantity(1);

        CartQuantity mobileCartQuantity = new CartQuantity();
        mobileCartQuantity.setArticle(mobile);
        mobileCartQuantity.setQuantity(1);

        CartQuantity headphoneCartQuantity = new CartQuantity();
        headphoneCartQuantity.setArticle(headphone);
        headphoneCartQuantity.setQuantity(2);

        Cart cart = new Cart();
        cart.setStatus(CartStatus.NEW);
        cart.setArticlesWithQuantity(new HashSet<>(List.of(screenCartQuantity, mobileCartQuantity, headphoneCartQuantity)));

        return cart;
    }

    private int getArticleQuantity(Cart cart, int articleId){
        return cart.getArticlesWithQuantity().stream().filter(cq -> cq.getArticle().getId() == articleId).findFirst().get().getArticle().getQuantity();
    }
}
