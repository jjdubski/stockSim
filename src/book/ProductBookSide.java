package book;

import user.*;
import price.Price;
import tradable.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ProductBookSide {
    private BookSide side;
    private final HashMap<Price, ArrayList<Tradable>> bookEntries;

    public ProductBookSide(BookSide side) throws InvalidInput {
        setSide(side);
        this.bookEntries = new HashMap<Price, ArrayList<Tradable>>();
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

    public TradableDTO add(Tradable o){
        if(!bookEntries.containsKey(o.getPrice())){
            bookEntries.put(o.getPrice(), new ArrayList<>());
        }
        bookEntries.get(o.getPrice()).add(o);
        System.out.println("ADD: " + side + ": " + o.getUser() + " order: " + o.getSide() + " " + o.getProduct() + " at "
                + o.getPrice() + ", Orig Vol: " + o.getOriginalVolume() + ", Rem Vol: " + o.getRemainingVolume()
                + ", Fill Vol: " + o.getFilledVolume() + ", CXL Vol: " + o.getCancelledVolume() + ", ID: " + o.getId());
        return o.makeTradableDTO();
    }
    public TradableDTO cancel(String tradableId) throws user.DataValidationException {
        for(Price p : bookEntries.keySet()){
            for(Tradable t : bookEntries.get(p)){
                if(t.getId() == tradableId){
                    bookEntries.get(p).remove(t);
                    System.out.println("CANCEL: " + side + ": " + tradableId + " Cxl Qty: " + t.getRemainingVolume());
                    t.setCancelledVolume(t.getRemainingVolume());
                    t.setRemainingVolume(0);
                    if(bookEntries.get(p).isEmpty()){
                        bookEntries.remove(p);
                    }
                    UserManager.getInstance().addToUser(t.getUser(),t.makeTradableDTO());
                    return t.makeTradableDTO();
                }
            }
        }
        return null;
    }
    public TradableDTO removeQuotesForUser(String userName) throws user.DataValidationException {
        TradableDTO result = null;
        for(Price p : bookEntries.keySet()){
            for(Tradable t : bookEntries.get(p)){
                if(t.getUser() == userName){
                    result = cancel(t.getId());
                    if(bookEntries.get(p) != null && bookEntries.get(p).isEmpty()){
                        bookEntries.remove(p);
                    }
                    return result;
                }
            }
        }
        return null;
    }
    public Price topOfBookPrice(){
        if(this.side == BookSide.BUY && !bookEntries.isEmpty()){
            return Collections.max(bookEntries.keySet());
        } else if (this.side == BookSide.SELL && !bookEntries.isEmpty()) {
            return Collections.min(bookEntries.keySet());
        }
        return null;
    }
    public int topOfBookVolume(){
        int total = 0;
        if(this.side == BookSide.BUY && !bookEntries.isEmpty()){
            for(Tradable t : bookEntries.get(topOfBookPrice())){
                total += t.getRemainingVolume();
            }
        }
        if(this.side == BookSide.SELL && !bookEntries.isEmpty()){
            for(Tradable t : bookEntries.get(topOfBookPrice())){
                total += t.getRemainingVolume();
            }
        }
        return total;
    }
    public void tradeOut(Price price, int vol) throws user.DataValidationException {
        int remainingVol = vol;
        ArrayList<Tradable> list = bookEntries.get(price);

        while(remainingVol > 0){
            Tradable firstOrder = list.get(0);

            if(firstOrder.getRemainingVolume() <= remainingVol){
                list.remove(0);
                int firstOrderRemainingVolume = firstOrder.getRemainingVolume();
                firstOrder.setFilledVolume(firstOrderRemainingVolume+firstOrder.getFilledVolume());
                remainingVol -= firstOrder.getRemainingVolume();
                firstOrder.setRemainingVolume(0);
                System.out.println("\tFULL FILL: " + "(" + side + " " + firstOrderRemainingVolume + ") "
                        + firstOrder.getUser() + " order: " + firstOrder.getSide() + " " + firstOrder.getProduct() + " at "
                        + firstOrder.getPrice() + ", Orig Vol: " + firstOrder.getOriginalVolume() + ", Rem Vol: "
                        + firstOrder.getRemainingVolume() + ", Fill Vol: " + firstOrder.getFilledVolume() + ", CXL Vol: "
                        + firstOrder.getCancelledVolume() + ", ID: " + firstOrder.getId());
                UserManager.getInstance().addToUser(firstOrder.getUser(), firstOrder.makeTradableDTO());
            }else{
                firstOrder.setFilledVolume(firstOrder.getFilledVolume()+remainingVol);
                firstOrder.setRemainingVolume(firstOrder.getRemainingVolume()-remainingVol);
                System.out.println("\tPARTIAL FILL: " + "(" + side + " " + remainingVol + ") "
                        + firstOrder.getUser() + " order: " + firstOrder.getSide() + " " + firstOrder.getProduct() + " at "
                        + firstOrder.getPrice() + ", Orig Vol: " + firstOrder.getOriginalVolume() + ", Rem Vol: "
                        + firstOrder.getRemainingVolume() + ", Fill Vol: " + firstOrder.getFilledVolume() + ", CXL Vol: "
                        + firstOrder.getCancelledVolume() + ", ID: " + firstOrder.getId());
                remainingVol = 0;
                UserManager.getInstance().addToUser(firstOrder.getUser(), firstOrder.makeTradableDTO());
            }
        }
        if(bookEntries.get(price).isEmpty()){
            bookEntries.remove(price);
        }
    }
    public String toString(){
        String result = "Side: " + side + "\n";
        for(Price p : bookEntries.keySet()){
            result += "\tPrice: " + p.toString() + "\n";
            for(Tradable t : bookEntries.get(p)){
                result += "\t\t" + t.toString() + "\n";
            }
        }
        return result;
    }
}
