package com.epitech.cashmanager.service;

import com.epitech.cashmanager.exception.ArticleNotExist;
import com.epitech.cashmanager.exception.CashManagerException;
import com.epitech.cashmanager.exception.NotEnoughQuantity;
import com.epitech.cashmanager.model.Article;
import com.epitech.cashmanager.model.Cart;
import com.epitech.cashmanager.model.CartQuantity;
import com.epitech.cashmanager.model.User;
import com.epitech.cashmanager.repository.ArticleRepository;
import com.epitech.cashmanager.repository.CartQuantityRepository;
import com.epitech.cashmanager.repository.CartRepository;
import com.epitech.cashmanager.repository.UserRepository;
import com.epitech.cashmanager.tools.CartStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CartServiceTest {

    public static final String JOHN_DOE = "john.doe";

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    @SuppressWarnings("unused")
    private CartQuantityRepository cartQuantityRepository;

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Before
    public void init(){
        User john = new User();
        john.setFirstName("John");
        john.setLastName("Doe");
        john.setUsername("john.doe");

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
        headphone.setPrice(new BigDecimal(250));
        headphone.setName("Headphone");

        CartQuantity screenCartQuantity = new CartQuantity();
        screenCartQuantity.setArticle(screen);
        screenCartQuantity.setQuantity(2);

        CartQuantity mobileCartQuantity = new CartQuantity();
        mobileCartQuantity.setArticle(mobile);
        mobileCartQuantity.setQuantity(1);

        Cart cart = new Cart();
        cart.setStatus(CartStatus.NEW);
        cart.setUser(john);
        cart.setArticlesWithQuantity(new HashSet<>(List.of(screenCartQuantity, mobileCartQuantity)));

        john.setCarts(new ArrayList<>(List.of(cart)));

        when(userRepository.findByUsername(JOHN_DOE)).thenReturn(john);

        when(articleRepository.findById(0)).thenReturn(Optional.of(screen));
        when(articleRepository.findById(1)).thenReturn(Optional.of(mobile));
        when(articleRepository.findById(2)).thenReturn(Optional.of(headphone));

        // on save, return the cart
        when(cartRepository.save(any(Cart.class))).thenAnswer((Answer<Cart>) invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return (Cart) args[0];
        });
    }

    @Test
    public void findCurrentCartTest() {
        Cart cart = cartService.findCurrentCart(JOHN_DOE);

        assertNotNull("Cart is find", cart);
        assertEquals("2 articles in the cart", 2, cart.getArticlesWithQuantity().size());

        // cancel the current cart with 2 articles
        cart.setStatus(CartStatus.CANCELLED);

        cart = cartService.findCurrentCart(JOHN_DOE);

        assertNotNull("A new cart should be created", cart);
        assertEquals("The new card should be empty", 0, cart.getArticlesWithQuantity().size());
    }

    @Test
    public void addArticleToCartTest() {
        Cart cart = cartService.addArticleToCart(JOHN_DOE, 0, 10);

        assertEquals("they are now 10 screen on cart", 10, getArticleQuantity(cart, 0));

        // insert a new article
        cart = cartService.addArticleToCart(JOHN_DOE, 2, 7);

        assertEquals("they are now 7 headphones on cart", 7, getArticleQuantity(cart, 2));

        try {
            cartService.addArticleToCart(JOHN_DOE, 5, 2);
            fail("The method should throw an exception");
        } catch (ArticleNotExist e) {
            // An exception should be throw because the article '5' not exist
            assertNotNull(e);
        }

        try {
            cartService.addArticleToCart(JOHN_DOE, 0, 30);
            fail("The method should throw an exception");
        } catch (NotEnoughQuantity e) {
            // An exception should be throw because not enough stock available (only 20)
            assertNotNull(e);
        }

        try {
            cartService.addArticleToCart(JOHN_DOE, 0, 0);
            fail("The method should throw an exception");
        } catch (CashManagerException e) {
            // An exception should be throw because trying add neutral quantity
            assertNotNull(e);
        }
    }
    @Test
    public void removeArticleFromCartTest() {
        Cart cart = cartService.removeArticleFromCart(JOHN_DOE, 0, 1);

        assertEquals("they are still 1 screen", 1, getArticleQuantity(cart, 0));

        cart = cartService.removeArticleFromCart(JOHN_DOE, 0, 1);

        assertEquals("No more screen", 0, getArticleCount(cart, 0));

        try {
            cartService.removeArticleFromCart(JOHN_DOE, 0, 2);
            fail("The method should throw an exception");
        } catch (CashManagerException e) {
            // An exception should be throw because no more article on cart
            assertNotNull(e);
        }

        try {
            cartService.removeArticleFromCart(JOHN_DOE, 0, -1);
            fail("The method should throw an exception");
        } catch (CashManagerException e) {
            // An exception should be throw because can't remove negative value
            assertNotNull(e);
        }

        try {
            cartService.removeArticleFromCart(JOHN_DOE, 1, 2);
            fail("The method should throw an exception");
        } catch (CashManagerException e) {
            // An exception should be throw because no enough article on cart
            assertNotNull(e);
        }
    }

    @Test
    public void deleteCartTest() {
        Cart cart = cartService.findCurrentCart(JOHN_DOE);

        cartService.deleteCart(JOHN_DOE);

        assertEquals("cart status should be cancel",(Object) CartStatus.CANCELLED, (Object) cart.getStatus());

        cart = cartService.findCurrentCart(JOHN_DOE);

        try {
            cartService.deleteCart(JOHN_DOE);
            fail("The method should throw an exception");
        } catch (CashManagerException e) {
            // An exception should be throw because empty cart
            assertNotNull(e);
        }
    }

    private int getArticleQuantity(Cart cart, int articleId){
        return cart.getArticlesWithQuantity().stream().filter(cq -> cq.getArticle().getId() == articleId).findFirst().get().getQuantity();
    }

    private long getArticleCount(Cart cart, int articleId){
        return cart.getArticlesWithQuantity().stream().filter(cq -> cq.getArticle().getId() == articleId).count();
    }
}
