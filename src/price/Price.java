package price;

import price.exceptions.InvalidPriceOperation;

import java.util.Objects;

public class Price implements Comparable<Price>{
    private final int cents;

    public Price(int cents){
        this.cents = cents;
    }

    public boolean isNegative(){
        if(this.cents < 0){
            return true;
        }else{
            return false;
        }
    }

    public Price add(Price p) throws InvalidPriceOperation {
        if(p == null){
            throw new InvalidPriceOperation("Price cannot be null");
        }
        int sum = this.cents + p.cents;
        return new Price(sum);
    }

    public Price subtract(Price p) throws InvalidPriceOperation {
        if(p == null){
            throw new InvalidPriceOperation("Price cannot be null");
        }
        int total = this.cents - p.cents;
        return new Price(total);
    }

    public Price multiply(int n){
        int total = this.cents * n;
        return new Price(total);
    }

    public boolean greaterOrEqual(Price p) throws InvalidPriceOperation {
        if(p == null){
            throw new InvalidPriceOperation("Price cannot be null");
        }
        if(this.cents >= p.cents){
            return true;
        }else{
            return false;
        }
    }

    public boolean lessOrEqual(Price p) throws InvalidPriceOperation {
        if(p == null){
            throw new InvalidPriceOperation("Price cannot be null");
        }
        if(this.cents <= p.cents){
            return true;
        }else{
            return false;
        }
    }

    public boolean greaterThan(Price p){
//        if(p == null){
//            throw new InvalidPriceOperation("Price cannot be null");
//        }
        if(this.cents > p.cents){
            return true;
        }else{
            return false;
        }
    }

    public boolean lessThan(Price p){
//        if(p == null){
//            throw new InvalidPriceOperation("Price cannot be null");
//        }
        if(this.cents < p.cents){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return cents == price.cents;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cents);
    }

    @Override
    public int compareTo(Price p) {
        return this.cents - p.cents;
    }

    @Override
    public String toString(){
        double dollars = (double) this.cents / 100;
        return String.format("$%,.2f", dollars);
    }

}
