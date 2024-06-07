package market;

import price.*;
import price.exceptions.InvalidPriceOperation;

public class CurrentMarketTracker {
    private static CurrentMarketTracker instance = new CurrentMarketTracker();

    private CurrentMarketTracker() {}

    public static CurrentMarketTracker getInstance(){
        if(instance == null){
            instance = new CurrentMarketTracker();
        }
        return instance;
    }

    public void updateMarket(String symbol, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume) throws InvalidPriceOperation {
        Price marketWidth = PriceFactory.makePrice(0);
        if(buyPrice != null && sellPrice != null) {
            marketWidth = sellPrice.subtract(buyPrice);
        }
        if(buyPrice == null){
            buyPrice = PriceFactory.makePrice(0);
        }
        if(sellPrice == null){
            sellPrice = PriceFactory.makePrice(0);
        }
        CurrentMarketSide buySide = new CurrentMarketSide(buyPrice, buyVolume);
        CurrentMarketSide sellSide = new CurrentMarketSide(sellPrice, sellVolume);
        System.out.println("*********** Current Market ***********");
        System.out.println("* " + symbol + "\t" + buyPrice + "x" + buyVolume + " - "
                + sellPrice + "x" + sellVolume + " [" + marketWidth + "]");
        System.out.println("**************************************");
        CurrentMarketPublisher.getInstance().acceptCurrentMarket(symbol, buySide, sellSide);
    }
}
