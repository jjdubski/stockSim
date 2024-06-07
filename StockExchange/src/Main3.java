///////////////////////////////////////////////////////////////////////////////////////
// You will probably need to change the below imports to match your package structure
import tradable.Order;
import book.ProductManager;
import tradable.Quote;
import price.PriceFactory;
import tradable.TradableDTO;
import user.User;
import user.UserManager;

import static book.BookSide.BUY; // You will probably need to change this to match your
import static book.BookSide.SELL;
///////////////////////////////////////////////////////////////////////////////////////

public class Main3 {


    public static void main(String[] args) {

        // This main content should execute w/o issue - no exceptions should occur.
        try {
            System.out.println("\nSetup: Initialize ProductManager and UserManager");
            ProductManager.getInstance().addProduct("WMT");
            ProductManager.getInstance().addProduct("TGT");

            UserManager.getInstance().init(
                    new String[]{"ANA", "BOB", "COD", "DIG", "EST"});

            ////////////////////////////////////////////////////////////////////////////
            System.out.println("\nStep A) Build up book sides with Quotes (no trades)");
            ProductManager.getInstance().addQuote(
                    new Quote("TGT",
                            PriceFactory.makePrice(15990), 75,
                            PriceFactory.makePrice(16000), 75,
                            "ANA"));
            ProductManager.getInstance().addQuote(
                    new Quote("TGT",
                            PriceFactory.makePrice(15990), 100,
                            PriceFactory.makePrice(16000), 100,
                            "BOB"));
            System.out.println(ProductManager.getInstance().getProductBook("TGT").toString());

            ////////////////////////////////////////////////////////////////////////////
            System.out.println("\n\nStep B) Enter an Order that trades with the SELL side quotes");
            ProductManager.getInstance().addTradable(
                    new Order("COD", "TGT", PriceFactory.makePrice(16000), 100, BUY));
            System.out.println(ProductManager.getInstance().getProductBook("TGT").toString());

            ////////////////////////////////////////////////////////////////////////////
            System.out.println("\n\nStep C) Change user ANA's Quote");
            ProductManager.getInstance().addQuote(
                    new Quote("TGT",
                            PriceFactory.makePrice(15985), 111,
                            PriceFactory.makePrice(16000), 111,
                            "ANA"));
            System.out.println(ProductManager.getInstance().getProductBook("TGT").toString());

            ////////////////////////////////////////////////////////////////////////////
            System.out.println("\n\nStep D) Display random Tradable for user ANA");

            User u1 = UserManager.getInstance().getUser("ANA");
            boolean value = u1.hasTradableWithRemainingQty();
            System.out.println("User ANA has Tradable with remaining quantity: " + value);
            TradableDTO u1DTO = u1.getTradableWithRemainingQty();
            System.out.println("User ANA's random Tradable with remaining quantity:\n" + u1DTO.toString());

            ////////////////////////////////////////////////////////////////////////////
            System.out.println("\n\nStep E) Enter an Order that trades out the BUY side quotes");
            ProductManager.getInstance().addTradable(
                    new Order("DIG", "TGT", PriceFactory.makePrice(15985), 211, SELL));
            System.out.println(ProductManager.getInstance().getProductBook("TGT").toString());

            ////////////////////////////////////////////////////////////////////////////
            System.out.println("\n\nStep F) Cancel quotes from ANA and BOB");
            ProductManager.getInstance().cancelQuote("TGT", "ANA");
            ProductManager.getInstance().cancelQuote("TGT", "BOB");
            System.out.println(ProductManager.getInstance().getProductBook("TGT").toString());

            User u2 = UserManager.getInstance().getUser("ANA");
            System.out.println("User ANA has Tradable with remaining quantity: " + u2.hasTradableWithRemainingQty());
            User u3 = UserManager.getInstance().getUser("BOB");
            System.out.println("User BOB has Tradable with remaining quantity: " + u3.hasTradableWithRemainingQty());

            ////////////////////////////////////////////////////////////////////////////
            System.out.println("\n\nStep G) Print users and their OrderDTO's (user order doesn't matter)");
            System.out.println(UserManager.getInstance().toString());

            ////////////////////////////////////////////////////////////////////////////
            System.out.println("\n\nStep H) Print product books");
            System.out.println(ProductManager.getInstance().toString());

            ////////////////////////////////////////////////////////////////////////////
            System.out.println("\n\nStep I) Enter 3 BUY side orders for WMT");
            ProductManager.getInstance().addTradable(
                    new Order("COD", "WMT", PriceFactory.makePrice(6010), 50, BUY));
            ProductManager.getInstance().addTradable(
                    new Order("DIG", "WMT", PriceFactory.makePrice(6010), 100, BUY));
            ProductManager.getInstance().addTradable(
                    new Order("EST", "WMT", PriceFactory.makePrice(6010), 75, BUY));
            System.out.println(ProductManager.getInstance().getProductBook("WMT").toString());

            ////////////////////////////////////////////////////////////////////////////
            System.out.println("\n\nStep J) Enter Quote from user BOB for WMT that trades out BUY orders");
            ProductManager.getInstance().addQuote(
                    new Quote("WMT",
                            PriceFactory.makePrice(6000), 225,
                            PriceFactory.makePrice(6010), 225,
                            "BOB"));
            ProductManager.getInstance().cancelQuote("WMT", "BOB");
            System.out.println(ProductManager.getInstance().getProductBook("WMT").toString());

            ////////////////////////////////////////////////////////////////////////////
            System.out.println("\n\nStep K) Display all Product Books");
            System.out.println(ProductManager.getInstance().toString());

            ////////////////////////////////////////////////////////////////////////////
            System.out.println("\n\nStep L) Display all Users and their TradableDTO's");
            System.out.println(UserManager.getInstance().toString());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
