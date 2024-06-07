import book.BookSide;
import tradable.Order;
import book.ProductBook;
import tradable.Quote;
import price.PriceFactory;

public class Main2 {
    public static void main(String[] args) {
//        Price.Price p = new Price.Price(123456789);
//        Price.Price p = new Price.Price(123);
//        Price.Price p = new Price.Price(9999999999999);
//        Price.Price p = new Price.Price(0);
//        Price.Price p = new Price.Price(-1);

//        Price.Price p = Price.PriceFactory.makePrice("$-000100,001.0019999");
//        Price.Price p = Price.PriceFactory.makePrice(98765);
//        Price.Price p = Price.PriceFactory.makePrice("98765");
//        Price.Price p = Price.PriceFactory.makePrice(000);
//        Price.Price p = Price.PriceFactory.makePrice("000");
//        Price.Price p = Price.PriceFactory.makePrice(".22");
//        Price.Price p = Price.PriceFactory.makePrice("14.7555");
//        Price.Price p = Price.PriceFactory.makePrice("25.79");
//        Price.Price p = Price.PriceFactory.makePrice("001.76");
//        Price.Price p = Price.PriceFactory.makePrice("4,567.89");
//        Price.Price p = Price.PriceFactory.makePrice("-12.85");
//        Price.Price p = Price.PriceFactory.makePrice(-12);
//        Price.Price p = Price.PriceFactory.makePrice("$-12");

//        Price.Price p1 = Price.PriceFactory.makePrice("$-.89");
//        Price.Price p2 = Price.PriceFactory.makePrice("");
//        Price.Price p3 = Price.PriceFactory.makePrice("4,567.89");
//
//        Price.Price p4 = Price.PriceFactory.makePrice("$.89");
//        Price.Price p5 = Price.PriceFactory.makePrice("$0.89");
//
//
//        System.out.println(p1 + " Hashcode: " + p1.hashCode());
//        System.out.println(p1 + " toString: " + p1.toString() + "\n");
//
//        System.out.println("" + " Hashcode: " + p2.hashCode());
//        System.out.println("" + " toString: " + p2.toString() + "\n");
//
//        System.out.println(p1 + " isNegative: " + p1.isNegative());
//        System.out.println(p3 + " isNegative: " + p3.isNegative() + "\n");
//
//        try {
//            System.out.println(p1 + " plus " + p2 + " : " + p1.add(p2));
//            System.out.println(p1 + " plus " + p3 + " : " + p1.add(p3) + "\n");
//
//            System.out.println(p1 + " minus " + p2 + " : " + p1.subtract(p2));
//            System.out.println(p1 + " minus " + p3 + " : " + p1.subtract(p3) + "\n");
//        } catch (PricePkg.InvalidPriceOperation e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println(p1 + " times " + "0" + " : " + p1.multiply(0));
//        System.out.println(p1 + " times " + "127" + " : " + p1.multiply(127) + "\n");
//
//        try {
//            System.out.println(p1 + " greaterOrEqual " + p3 + " : " + p1.greaterOrEqual(p3));
//            System.out.println(p1 + " greaterOrEqual " + p4 + " : " + p1.greaterOrEqual(p4));
//            System.out.println(p4 + " greaterOrEqual " + p5 + " : " + p4.greaterOrEqual(p5) + "\n");
//
//            System.out.println(p1 + " lessOrEqual " + p3 + " : " + p1.lessOrEqual(p3));
//            System.out.println(p1 + " lessOrEqual " + p4 + " : " + p1.lessOrEqual(p4));
//            System.out.println(p4 + " lessOrEqual " + p5 + " : " + p4.lessOrEqual(p5) + "\n");
//
//            System.out.println(p1 + " greaterThan " + p3 + " : " + p1.greaterThan(p3));
//            System.out.println(p1 + " greaterThan " + p4 + " : " + p1.greaterThan(p4));
//            System.out.println(p4 + " greaterThan " + p5 + " : " + p4.greaterThan(p5) + "\n");
//
//            System.out.println(p1 + " lessThan " + p3 + " : " + p1.lessThan(p3));
//            System.out.println(p1 + " lessThan " + p4 + " : " + p1.lessThan(p4));
//            System.out.println(p4 + " lessThan " + p5 + " : " + p4.lessThan(p5) + "\n");
//
//            System.out.println(p1 + " equals " + p3 + " : " + p1.equals(p3));
//            System.out.println(p1 + " equals " + p4 + " : " + p1.equals(p4));
//            System.out.println(p4 + " equals " + p5 + " : " + p4.equals(p5) + "\n");
//
//            System.out.println(p1 + " compareTo " + p3 + " : " + p1.compareTo(p3));
//            System.out.println(p1 + " compareTo " + p4 + " : " + p1.compareTo(p4));
//            System.out.println(p4 + " compareTo " + p5 + " : " + p4.compareTo(p5) + "\n");
//        } catch (PricePkg.InvalidPriceOperation e) {
//            System.out.println(e.getMessage());
//        }
////    }
//
        final BookSide BUY = BookSide.BUY;
        final BookSide SELL = BookSide.SELL;
        // This main content should execute w/o issue - no exceptions should occur.
        try {
            ProductBook pb = new ProductBook("TGT");
            System.out.print("1) Enter BUY order for TGT from EST");
            System.out.println(" - book shows 1 order on the BUY side");
            Order o1 = new Order("EST", "TGT", PriceFactory.makePrice(17720), 50, BUY);
            pb.add(o1);
            System.out.println(pb);
            System.out.print("========================================================");
            System.out.println("========================================================\n");
            System.out.println("2) Cancel BUY order for TGT from EST - book should be empty");
            pb.cancel(BUY, o1.getId());
            System.out.println(pb);
            System.out.print("========================================================");
            System.out.println("========================================================\n");
            System.out.print("3) Enter BUY order for TGT from ANA");
            System.out.println(" - book shows 1 order on the BUY side");
            Order o2 = new Order("ANA", "TGT", PriceFactory.makePrice(17720), 50, BUY);
            pb.add(o2);
            System.out.println(pb);
            System.out.print("========================================================");
            System.out.println("========================================================\n");
            System.out.print("4) Enter Quote for TGT from BOB - book shows 1 order on ");
            System.out.println("the BUY side and 1 quote on the BUY & SELL side");
            Quote qte = new Quote("TGT",
                    PriceFactory.makePrice(17720), 75,
                    PriceFactory.makePrice(17730), 75,
                    "BOB");
            pb.add(qte);
            System.out.println(pb);
            System.out.print("========================================================");
            System.out.println("========================================================\n");
            System.out.print("5) Enter SELL order for TGT from COD");
            System.out.println(" - book shows new SELL order on the SELL side");
            Order o3 = new Order("COD", "TGT", PriceFactory.makePrice(17730), 85, SELL);
            pb.add(o3);
            System.out.println(pb);
            System.out.print("========================================================");
            System.out.println("========================================================\n");
            System.out.print("6) Enter BUY order for TGT from DIG - fully trades against SELL-side");
            System.out.println("quote (partially) from BOB - partial sell-side quote from BOB remains");
            Order o4 = new Order("DIG", "TGT", PriceFactory.makePrice(17730), 60, BUY);
            pb.add(o4);
            System.out.println(pb);
            System.out.print("========================================================");
            System.out.println("========================================================\n");
            System.out.println("7) Cancel all open TGT orders & quotes - leaves book empty");
            pb.cancel(BUY, o2.getId());
            pb.cancel(SELL, o3.getId());
            pb.removeQuotesForUser("BOB");
            System.out.println(pb);
            System.out.print("========================================================");
            System.out.println("========================================================\n");
            System.out.print("8) Enter BUY order for TGT from ANA - ");
            System.out.println("book shows 1 order from ANA on the BUY side");
            Order o5 = new Order("ANA", "TGT", PriceFactory.makePrice(17710), 50, BUY);
            pb.add(o5);
            System.out.println(pb);
            System.out.print("========================================================");
            System.out.println("========================================================\n");
            System.out.print("9) Enter BUY order for TGT from BOB at a lesser price - ");
            System.out.println("book contains 2 BUY orders at 2 prices");
            Order o6 = new Order("BOB", "TGT", PriceFactory.makePrice(17720), 100, BUY);
            pb.add(o6);
            System.out.println(pb);
            System.out.print("========================================================");
            System.out.println("========================================================\n");
            System.out.print("10) Enter BUY order for TGT from COD at an even lesser price");
            System.out.println(" - book contains 3 BUY orders at 3 prices");
            Order o7 = new Order("COD", "TGT", PriceFactory.makePrice(17730), 150, BUY);
            pb.add(o7);
            System.out.println(pb);
            System.out.print("========================================================");
            System.out.println("========================================================\n");
            System.out.print("11) Enter Quote for TGT from DIG at a better BUY side - ");
            System.out.println("book shows quote sides at the top of both BUY & SELL sides");
            qte = new Quote("TGT",
                    PriceFactory.makePrice(17740), 200,
                    PriceFactory.makePrice(17750), 200,
                    "DIG");
            pb.add(qte);
            System.out.println(pb);
            System.out.print("========================================================");
            System.out.println("========================================================\n");
            System.out.print("12) Enter large SELL order for TGT from EST to trade out BUY side - ");
            System.out.println("trades with everything on the BUY side, leaving 1 SELL-side quote");
            Order o8 = new Order("EST", "TGT", PriceFactory.makePrice(17710), 500, SELL);
            pb.add(o8);
            System.out.println(pb);
            System.out.print("========================================================");
            System.out.println("========================================================\n");
            System.out.println("13) Cancel TGT quote from DIG - leaves the book empty");
            pb.removeQuotesForUser("DIG");
            System.out.println(pb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}