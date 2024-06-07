package user;

import market.*;
import tradable.*;
import java.util.HashMap;

public class User implements CurrentMarketObserver {
    private String userId;
    private HashMap<String, TradableDTO> tradables = new HashMap<>();
    private HashMap<String, CurrentMarketSide[]> currentMarkets = new HashMap<>();

    public User(String userId) throws InvalidInput {
       setUserId(userId);
    }

    private void setUserId(String userId) throws InvalidInput {
        if(userId.length() != 3){
            throw new InvalidInput("Invalid user ID length");
        }
        for (char c : userId.toCharArray()) {
            if(!Character.isLetter(c)){
                throw new InvalidInput("Invalid user ID character(s)");
            }
        }
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }
    public void addTradable(TradableDTO o) {
        if(o != null){
            tradables.put(o.id, o);
        }
    }
    public boolean hasTradableWithRemainingQty() {
        if(!tradables.isEmpty()){
            for(TradableDTO tradable : tradables.values()){
                if(tradable.remainingVolume > 0){
                    return true;
                }
            }
        }
        return false;
    }
    public TradableDTO getTradableWithRemainingQty() {
        if(!tradables.isEmpty()){
            for(TradableDTO tradable : tradables.values()){
                if(tradable.remainingVolume > 0){
                    return tradable;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String result = "User Id: " + userId + "\n";
        for(TradableDTO tradable : tradables.values()){
            result += "\tProduct: " + tradable.product + ", Price: " + tradable.price + ", OriginalVolume: "
            + tradable.originalVolume + ", RemainingVolume: " + tradable.remainingVolume + ", CancelledVolume: "
            + tradable.cancelledVolume + ", Filled volume: " + tradable.filledVolume + ", User: " + tradable.user
            + ", Side: " + tradable.side + ", Id: " + tradable.id + "\n";
        }
        return result;
    }

    @Override
    public void updateCurrentMarket(String symbol, CurrentMarketSide buySide, CurrentMarketSide sellSide) {
        CurrentMarketSide[] currentMarketSides =  new CurrentMarketSide[]{buySide, sellSide};
        currentMarkets.put(symbol, currentMarketSides);
    }

    public String getCurrentMarkets(){
        String result = "";
        for(String symbol : currentMarkets.keySet()){
            result += symbol + "\t" + currentMarkets.get(symbol)[0].toString() + " "
                    + currentMarkets.get(symbol)[1].toString() + "\n";
        }
        return result;
    }
}
