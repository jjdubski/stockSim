package book;

import price.exceptions.InvalidPriceOperation;
import tradable.*;
import user.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ProductManager {
    private static ProductManager instance;
    private HashMap<String, ProductBook> products = new HashMap<>();

    private ProductManager() {}

    public ArrayList<String> getProductList(){
        return new ArrayList<>(products.keySet());
    }
    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }
    public void addProduct(String symbol) throws InvalidInput, DataValidationException {
        if(symbol == null || symbol.isEmpty()) {
            throw new DataValidationException("Symbol cannot be null or empty");
        }
        if(symbol.length() < 1 || symbol.length() > 5) {
            throw new DataValidationException("Invalid product length");
        }
        for(char c: symbol.toCharArray()) {
            if(!Character.isLetter(c) || c == '.') {
                throw new DataValidationException("Invalid product character");
            }
        }
        products.put(symbol, new ProductBook(symbol));
    }
    public ProductBook getProductBook(String symbol) throws DataValidationException {
        if(!products.containsKey(symbol) || products.get(symbol) == null) {
            throw new DataValidationException("Product does not exist");
        }
        return products.get(symbol);
    }
    public String getRandomProduct() {
        if(products.isEmpty()) {
            return null;
        }
        ArrayList<String> productList = new ArrayList<>(products.keySet());
        Random rand = new Random();
        int index = rand.nextInt(productList.size());
        return productList.get(index);
    }
    public TradableDTO addTradable(Tradable o) throws DataValidationException, user.DataValidationException, InvalidPriceOperation {
        TradableDTO result = null;
        if(o == null){
            throw new DataValidationException("Tradable cannot be null");
        }
        if(products.containsKey(o.getProduct())){
            result = products.get(o.getProduct()).add(o);
            UserManager.getInstance().addToUser(o.getUser(), o.makeTradableDTO());
        }
        return result;
    }
    public TradableDTO[] addQuote(Quote q) throws DataValidationException, user.DataValidationException, InvalidPriceOperation {
        TradableDTO[] result = new TradableDTO[2];
        if(q == null){
            throw new DataValidationException("Quote cannot be null");
        }
        if(products.containsKey(q.getSymbol())){
            products.get(q.getSymbol()).removeQuotesForUser(q.getUser());
            result[0] = addTradable(q.getQuoteSide(BookSide.BUY));
            result[1] = addTradable(q.getQuoteSide(BookSide.SELL));
        }
        return result;
    }
    public TradableDTO cancel(TradableDTO o) throws DataValidationException, user.DataValidationException, InvalidPriceOperation {
        if(o == null){
            throw new DataValidationException("Tradable cannot be null");
        }
        TradableDTO result = null;
        result= products.get(o.product).cancel(o.side, o.id);
        if(result == null){
            System.out.println("Failed to cancel.");
        }
        return result;
    }
    public TradableDTO[] cancelQuote(String symbol, String user) throws DataValidationException, user.DataValidationException, InvalidPriceOperation {
        if(symbol == null || symbol.isEmpty()){
            throw new DataValidationException("Symbol cannot be null");
        }
        if(user == null || user.isEmpty()){
            throw new DataValidationException("User cannot be null");
        }
        if(!products.containsKey(symbol)){
            throw new DataValidationException("Product does not exist");
        }
        TradableDTO[] result = products.get(symbol).removeQuotesForUser(user);
        return result;
    }

    @Override
    public String toString() {
        String result = "";
        for(ProductBook product: products.values()) {
            result += product.toString() + "\n";
        }
        return result;
    }
}
