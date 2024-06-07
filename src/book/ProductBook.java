package book;

import market.*;
import price.*;
import price.exceptions.InvalidPriceOperation;
import user.*;
import tradable.*;

public class ProductBook {
    private String product;
    private ProductBookSide buySide;
    private ProductBookSide sellSide;

    public ProductBook(String product) throws InvalidInput {
        setProduct(product);
        buySide = new ProductBookSide(BookSide.BUY);
        sellSide = new ProductBookSide(BookSide.SELL);
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
    public TradableDTO add(Tradable t) throws user.DataValidationException, InvalidPriceOperation {
        TradableDTO result = null;
        if(t.getSide() == BookSide.BUY){
            result = buySide.add(t);
        }else if(t.getSide() == BookSide.SELL){
            result = sellSide.add(t);
        }
        tryTrade();
        updateMarket();
        return result;
    }
    public TradableDTO[] add(Quote qte) throws user.DataValidationException {
        TradableDTO buyDTO = buySide.add(qte.getQuoteSide(BookSide.BUY));
        TradableDTO sellDTO = sellSide.add(qte.getQuoteSide(BookSide.SELL));
        tryTrade();
        return new TradableDTO[]{buyDTO,sellDTO};
    }
    public TradableDTO cancel(BookSide side, String orderId) throws user.DataValidationException, InvalidPriceOperation {
        TradableDTO result = null;
        if(side == BookSide.BUY){
            result = buySide.cancel(orderId);
            updateMarket();
        }else if(side == BookSide.SELL){
            result = sellSide.cancel(orderId);
            updateMarket();
        }
        return result;
    }
    public void tryTrade() throws user.DataValidationException {
        Price topBuy = buySide.topOfBookPrice();
        Price topSell = sellSide.topOfBookPrice();

        while(topBuy != null && topSell != null && topBuy.compareTo(topSell) >= 0){
            int topBuyVol = buySide.topOfBookVolume();
            int topSellVol = sellSide.topOfBookVolume();

            int tradingVol = Math.min(topBuyVol, topSellVol);

            sellSide.tradeOut(topSell, tradingVol);
            buySide.tradeOut(topBuy, tradingVol);

            topBuy = buySide.topOfBookPrice();
            topSell = sellSide.topOfBookPrice();
        }
    }
    public TradableDTO[] removeQuotesForUser(String userName) throws user.DataValidationException, InvalidPriceOperation {
        TradableDTO buyDTO = buySide.removeQuotesForUser(userName);
        TradableDTO sellDTO = sellSide.removeQuotesForUser(userName);
        UserManager.getInstance().getUser(userName).addTradable(buyDTO);
        UserManager.getInstance().getUser(userName).addTradable(sellDTO);
        updateMarket();
        return new TradableDTO[]{buyDTO,sellDTO};
    }
    public String toString(){
        String result = "--------------------------------------------\n"
                        + "Product Book: " + product + "\n"
                        + buySide.toString()
                        + sellSide.toString()
                        + "--------------------------------------------";
        return result;
    }

    private void updateMarket() throws InvalidPriceOperation {
        Price topBuyPrice = buySide.topOfBookPrice();
        Price topSellPrice = sellSide.topOfBookPrice();
        int topBuyVol = buySide.topOfBookVolume();
        int topSellVol = sellSide.topOfBookVolume();

        CurrentMarketTracker.getInstance().updateMarket(product, topBuyPrice, topBuyVol, topSellPrice, topSellVol);
    }
}
