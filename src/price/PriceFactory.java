package price;

import java.util.HashMap;

public abstract class PriceFactory {
    private static final HashMap<Integer, Price> prices = new HashMap<>();

    public static Price makePrice(int value){
        return addPrice(value);
    }

    public static Price makePrice(String stringValueIn){
        if(stringValueIn == null){
            return null;
        }
        boolean negative = false;
        int dollars = 0;
        int cents = 0;

        //check negative price
        if(stringValueIn.contains("-")){
            negative = true;
        }

        //remove - $ and , from the strings
        String[] cleanStrings = stringValueIn.split("[-$,]");
        String cleanString = "";
        for(int i = 0; i<cleanStrings.length; i++){
            cleanString += cleanStrings[i];
        }

        //check if there is a decimal and set dollars and cents accordingly
        if(!cleanString.contains(".")){
            if(cleanString.length() > 0) {
                dollars = Integer.parseInt(cleanString);
            }
        }else{
            cleanStrings = cleanString.split("[.]");
            //before . is always dollars unless empty
            if(cleanStrings[0].length() > 0) {
                dollars = Integer.parseInt(cleanStrings[0]);
            }
            //after . is always cents (floored to 2 values)
            cents = Integer.parseInt(String.format("%.2s",cleanStrings[1]));
        }

        //convert everything to cents
        cents += dollars*100;

        //set negative if true
        if(negative){
            cents = -cents;
        }

       return addPrice(cents);
    }

    private static Price addPrice(int cents){
        if(!prices.containsKey(cents)){
            prices.put(cents, new Price(cents));
            return prices.get(cents);
        }else{
            return prices.get(cents);
        }
    }
}
