package tradable;

import book.BookSide;
import price.Price;

public class Quote {
    private String user;
    private String product;
    private QuoteSide buySide;
    private QuoteSide sellSide;

    public Quote(String symbol, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume, String userName) throws InvalidInput {
        setUser(userName);
        setProduct(symbol);
        buySide = new QuoteSide(userName, symbol, buyPrice, buyVolume, BookSide.BUY);
        sellSide = new QuoteSide(userName, symbol, sellPrice, sellVolume, BookSide.SELL);
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

    public QuoteSide getQuoteSide(BookSide sideIn){
        if(sideIn == BookSide.BUY){
            return buySide;
        }else{
            return sellSide;
        }
    }

    public String getSymbol(){
        return product;
    }

    public String getUser(){
        return user;
    }
}
