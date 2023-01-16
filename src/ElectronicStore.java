//Class representing an electronic store
//Has an array of products that represent the items the store can sell
import com.sun.jdi.Value;

import java.io.*;
import java.util.*;

public class ElectronicStore implements Serializable{
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    private String name;
    private HashSet<Product> stock; //Array to hold all products
    private double revenue;
    private HashSet<Customer> customers;

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new HashSet<Product>();
        curProducts = 0;
        customers = new HashSet<Customer>();
    }

    public String getName() {return name;}

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        for(Product nameCheck : stock){
            if(stock.equals(nameCheck)){
                return false;
            }
        }
        stock.add(newProduct);
        return true;
    }

    //Check for customer names
    //If there exists a customer with that name, return false
    //Otherwise, register customer and return true
    public boolean registerCustomer(Customer c) {
        if(customers.contains(c)){
            return false;
        }
        customers.add(c);
        return true;
    }

    //Get the customer list
    //Go through customer list
    //Add them to new list to return
    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> getCustomersList = new ArrayList<Customer>(customers);
        return getCustomersList;
    }

    //Loop through stock list
    //If a match is found add to match list
    public ArrayList<Product> searchProducts(String x) {
        ArrayList<Product> matchProducts = new ArrayList<Product>();
        String xString = x.toUpperCase();

        for(Product nameCheck : stock){
            if(nameCheck.toString().toUpperCase().contains(xString)){
                matchProducts.add(nameCheck);
            }
        }
        return matchProducts;
    }

    //Check if minPrice and/or maxPrice are = to -1
    //Different loops dependent on situation
    //Add product to list if conditions are met
    public ArrayList<Product> searchProducts(String x, double minPrice, double maxPrice) {
        ArrayList<Product> matchProducts = new ArrayList<Product>();

        for(Product p : searchProducts(x)){
            if(maxPrice == -1 || (p.getPrice()) <= maxPrice){
                if(minPrice <= p.getPrice()){
                    matchProducts.add(p);
                }
            }
        }
        return matchProducts;
    }

    //Get if stock has the product
    //Set the soldQuantity of the product to current amount + new amount
    public boolean addStock(Product p, int amount) {
        if(stock.contains(p)){
            p.setSoldQuantity(p.getStockQuantity() + amount);
            return true;
        }
        return false;
    }

    //If conditions are met call buyProduct method, otherwise, return false
    public boolean sellProduct(Product p, Customer c, int amount) {
        if(stock.contains(p) == false || customers.contains(c) == false || p.getStockQuantity() < amount || p.sellUnits(amount) < 0) {
            return false;
        }
        return c.buyProduct(p, amount);
    }


    public ArrayList<Customer> getTopXCustomers(int x) {
        ArrayList<Customer> getTopXCustomersList = new ArrayList<>();
        ArrayList<Customer> sortedCustomerList = new ArrayList<>(customers);
        sortedCustomerList.sort(Comparator.comparing(Customer :: getMoneySpent).reversed());

        //If x is less than 0 return empty list
        if(x <= 0){
            return getTopXCustomersList;
        }
        //If x is greater than list size, return whole list
        else if(x > sortedCustomerList.size()){
            return sortedCustomerList;
        }else{
            //If x is in between 0 and list size, return top x amount
            for(int i = 0; i < x; i++) {
                getTopXCustomersList.add(sortedCustomerList.get(i));
            }
        }
        return getTopXCustomersList;
    }

    //saveToFile method
    public boolean saveToFile(String filename) {
        try {
            ObjectOutputStream out;

            out = new ObjectOutputStream(new FileOutputStream(filename));

            out.writeObject(this);
            out.close();

            return true;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return false;
    }

    //loadFromFile method
    public static ElectronicStore loadFromFile(String filename) {
        try{
            ObjectInputStream in;

            in = new ObjectInputStream(new FileInputStream(filename));
            ElectronicStore restoredStore = (ElectronicStore)in.readObject();
            return restoredStore;

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

} 