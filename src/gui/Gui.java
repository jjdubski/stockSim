package gui;

import price.Price;
import market.CurrentMarketObserver;
import market.CurrentMarketSide;
import price.exceptions.InvalidPriceOperation;

import java.util.HashMap;

public class Gui implements CurrentMarketObserver {

    UserDisplayManager userDisplayManager = new UserDisplayManager();
    public final HashMap<String, Price> lastSales = new HashMap<>();


    public Gui() {
        userDisplayManager.showMarketDisplay();
    }

    public void shutdown() {
        userDisplayManager.shutdown();
    }

    @Override
    public void updateCurrentMarket(String symbol, CurrentMarketSide buySide, CurrentMarketSide sellSide) {
        userDisplayManager.updateMarketData(
                symbol, buySide.getPrice(), buySide.getVolume(),
                sellSide.getPrice(), sellSide.getVolume()
        );
        updateTicker(symbol, Math.random() < 0.5 ? buySide.getPrice() : sellSide.getPrice());
    }
    private void updateTicker(String symbol, Price p) {

        if (p == null) return;

        Price ls = lastSales.get(symbol);
        char dir = '●';
        if (ls != null) {
            if (p.greaterThan(ls))
                dir = '▲';
            else if (p.lessThan(ls))
                dir = '▼';
        }
        lastSales.put(symbol, p);
        updateTicker(symbol, p, dir);
    }

    public void updateTicker(String product, Price p, char direction) {
        userDisplayManager.updateTicker(product, p, direction);
    }
}
