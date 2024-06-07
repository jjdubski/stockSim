package tradable;

import book.BookSide;
import price.Price;

public class TradableDTO {
    public String user; //3 letter code no spaces, no numbers, no special characters
    public String product; /*The stock symbol for the order, must be from 1 to 5 letters/numbers,
                                  they can also have a period “.” In the symbol. Otherwise, -no spaces,
                                  no special characters (i */
    public Price price; //cannot be null
    public BookSide side; //either BUY or SELL (cannot be null)
    public String id; //user + product + price (String) + nanotime (from System.nanoTime()).
    public int originalVolume; //must be greater than 0, less than 10000
    public int remainingVolume; //initialized as originalVolume but is changable
    public int cancelledVolume; //initilized to 0 but changable when cancelled
    public int filledVolume; //initalized to 0 but changeable when filled

    public TradableDTO(String user, String product, Price price, BookSide side, String id,
                       int originalVolume, int remainingVolume, int cancelledVolume, int filledVolume){
        this.user = user;
        this.product = product;
        this.price = price;
        this.side = side;
        this.id = id;
        this.originalVolume = originalVolume;
        this.remainingVolume = remainingVolume;
        this.cancelledVolume = cancelledVolume;
        this.filledVolume = filledVolume;
    }

    public String toString(){
        return user + " order: " + side + " " + product + " at " + price + ", Orig Vol: " + originalVolume
                + ", Rem Vol: " + remainingVolume + ", Fill Vol: " + filledVolume + ", CXL Vol: " + cancelledVolume
                + ", ID: " + id;
    }
}
