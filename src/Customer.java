import java.io.Serializable;
import java.util.HashMap;

public class Customer implements Serializable {
    private String name;
    private HashMap<Product, Integer> boughtProducts;
    private int moneySpent = 0;

    //Constructor
    public Customer(String initName){
        name = initName;
        boughtProducts = new HashMap<Product, Integer>();
    }

    //Purchase History method
    //Iterate through boughtProducts list and use key and value
    public void printPurchaseHistory() {
        for(Product p : getBoughtProducts().keySet()){
            System.out.println(getBoughtProducts().get(p) + "x " + p);
        }
    }


    public boolean buyProduct(Product p, int amount) {
        //If boughtProducts map contains the key already, add amount to current amount
        //Add amount to money spent
        if(boughtProducts.containsKey(p)){
            boughtProducts.put(p, (boughtProducts.get(p) + amount));
            moneySpent += amount * p.getPrice();
        //If boughtProducts map doesn't contain the key already, add it to the map
        }else{
            boughtProducts.put(p, amount);
            moneySpent += amount * p.getPrice();
        }
        return true;
    }

    //Get method
    public String getName(){return name;}

    public HashMap<Product, Integer> getBoughtProducts() {return boughtProducts;}

    public int getMoneySpent() {return moneySpent;}

    //Check if there are duplicates
    @Override
    public boolean equals(Object c) {
        return this.toString().equalsIgnoreCase(c.toString());
    }
    @Override
    public int hashCode() {
        return -1;
    }

    //toString Method
    public String toString(){
        return name + " who has spent $" + moneySpent;
    }


}
