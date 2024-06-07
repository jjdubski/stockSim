package tradable;

import book.BookSide;
import price.Price;

public class QuoteSide implements Tradable {
    private String user; //3-letter code no spaces, no numbers, no special characters
    private String product; /*The stock symbol for the order, must be from 1 to 5 letters/numbers,
                                  they can also have a period “.” In the symbol. Otherwise, -no spaces,
                                  no special characters (i */
    private Price price; //cannot be null
    private BookSide side; //either BUY or SELL (cannot be null)
    private String id; //user + product + price (String) + nanotime (from System.nanoTime()).
    private int originalVolume; //must be greater than 0, less than 10000
    private int remainingVolume; //initialized as originalVolume but is changable
    private int cancelledVolume; //initilized to 0 but changable when cancelled
    private int filledVolume; //initalized to 0 but changeable when filled

    public QuoteSide(String user, String product, Price price, int originalVolume, BookSide side) throws InvalidInput {
        setUser(user);
        setProduct(product);
        setPrice(price);
        setSide(side);
        setOriginalVolume(originalVolume);
        setRemainingVolume(originalVolume);
        setCancelledVolume(0);
        setFilledVolume(0);

        this.id = user + product + price.toString() + System.nanoTime();
    }

    private void setUser(String user) throws InvalidInput {
        if(user.length() != 3) {
            throw new InvalidInput("Invalid username length");
        }
        for(char c: user.toCharArray()) {
            if(!Character.isLetter(c)) {
                throw new InvalidInput("Invalid username character");
            }
        }
        this.user = user;
    }

    private void setProduct(String product) throws InvalidInput {
        if(product.length() < 1 || product.length() > 5) {
            throw new InvalidInput("Invalid product length");
        }
        for(char c: product.toCharArray()) {
            if(!Character.isLetter(c) || c == '.') {
                throw new InvalidInput("Invalid product character");
            }
        }
        this.product = product;
    }

    private void setPrice(Price price) throws InvalidInput {
        if(price == null) {
            throw new InvalidInput("Invalid price (null)");
        }
        this.price = price;
    }

    private void setSide(BookSide side) throws InvalidInput {
        if(side == null) {
            throw new InvalidInput("Invalid side (null)");
        }
        if(side != BookSide.BUY && side != BookSide.SELL) {
            throw new InvalidInput("Invalid side");
        }
        this.side = side;
    }

    private void setOriginalVolume(int volume) throws InvalidInput {
        if(volume < 0 || volume > 10000) {
            throw new InvalidInput("Invalid volume");
        }
        this.originalVolume = volume;
    }

    @Override
    public String toString() {
        return product + " quote from " + user + ": " + price + ", Orig Vol: " + originalVolume + ", Rem Vol: "
                + remainingVolume + ", Fill Vol: " + filledVolume + ", CXL Vol: " + cancelledVolume + " "
                + ", ID: " + id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getCancelledVolume() {
        return this.cancelledVolume;
    }

    @Override
    public void setCancelledVolume(int newVol) {
        this.cancelledVolume = newVol;
    }

    @Override
    public int getRemainingVolume() {
        return this.remainingVolume;
    }

    @Override
    public void setRemainingVolume(int newVol) {
        this.remainingVolume = newVol;
    }

    @Override
    public TradableDTO makeTradableDTO() {
        return new TradableDTO(this.user, this.product, this.price, this.side, this.id,
                this.originalVolume, this.remainingVolume, this.cancelledVolume, this.filledVolume);
    }

    @Override
    public Price getPrice() {
        return this.price;
    }

    @Override
    public void setFilledVolume(int newVol) {
        this.filledVolume = newVol;
    }

    @Override
    public int getFilledVolume() {
        return this.filledVolume;
    }

    @Override
    public BookSide getSide() {
        return this.side;
    }

    @Override
    public String getUser() {
        return this.user;
    }

    @Override
    public String getProduct() {
        return this.product;
    }

    @Override
    public int getOriginalVolume() {
        return this.originalVolume;
    }
}
