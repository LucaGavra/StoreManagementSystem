import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StoreManagementSystem {
    private static final String CONFIG_FILE_PATH = "config.properties";
    private static final String DATA_FILE_PATH = "data.txt";

    public static void main(String[] args) {
        try {
            // Load configuration from file or program arguments
            loadConfiguration();

            // Use program arguments to choose which function to call
            if (args.length == 0) {
                System.out.println("Please provide an argument to choose a function to call.");
                return;
            }

            switch (args[0]) {
                case "displayCart":
                    // Test the displayCart function
                    Product product = new Product("Laptop", 999.99);
                    OrderItem[] orderItems = {new OrderItem(product, 2), new OrderItem(product, 1)};
                    ShoppingCart cart = new ShoppingCart(product, orderItems);
                    cart.displayCart();
                    System.out.println("Total: $" + cart.calculateTotal());
                    break;

                case "calculateDiscount":
                    // Test the calculateDiscount function
                    DiscountedProduct discountedProduct = new DiscountedProduct("Smartphone", 499.99);
                    System.out.println("Discount: " + discountedProduct.calculateDiscount());
                    break;

                case "readData":
                    // Test reading data from the file
                    List<String> dataLines = readDataFromFile();
                    for (String line : dataLines) {
                        System.out.println(line);
                    }
                    break;

                case "writeData":
                    // Test writing data to the file
                    List<String> newData = Arrays.asList("Data Line 1", "Data Line 2");
                    writeDataToFile(newData);
                    break;

                case "printOrderItemDetails":
                    // Test printing order item details
                    Product itemProduct = new Product("Tablet", 299.99);
                    OrderItem orderItem = new PrintableOrderItem(itemProduct, 3);
                    Printable printableOrderItem = (Printable) orderItem;
                    printableOrderItem.printDetails();
                    break;

                case "processOrdersConcurrently":
                    // Test processing orders concurrently
                    processOrdersConcurrently();
                    break;

                default:
                    System.out.println("Invalid argument. Use 'displayCart', 'calculateDiscount', 'readData', 'writeData', 'printOrderItemDetails', or 'processOrdersConcurrently'.");
            }
        } catch (InvalidInputException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (FileHandlingException e) {
            System.out.println("Error handling file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void processOrdersConcurrently() {
        // Sample orders
        Product laptop = new Product("Laptop", 999.99);
        Product smartphone = new Product("Smartphone", 499.99);
    
        OrderItem[] orderItems1 = {new OrderItem(laptop, 2), new OrderItem(laptop, 1)};
        OrderItem[] orderItems2 = {new OrderItem(smartphone, 1), new OrderItem(smartphone, 3)};
    
        ShoppingCart cart1 = new ShoppingCart(laptop, orderItems1);
        ShoppingCart cart2 = new ShoppingCart(smartphone, orderItems2);
    

        Thread thread1 = new Thread(() -> processOrder(cart1));
        Thread thread2 = new Thread(() -> processOrder(cart2));
    
        thread1.start(); 
        thread2.start(); 
    
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private static void processOrder(ShoppingCart cart) {
        System.out.println("Processing order for " + cart.getProduct().getProductName());
        cart.displayCart();
        System.out.println("Total: $" + cart.calculateTotal());
    }
    

    private static void loadConfiguration() throws FileHandlingException, InvalidInputException {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            throw new FileHandlingException("Error reading configuration file", e);
        }

        validateNotEmpty(properties, "exampleProperty");
        validatePositiveIntegerProperty(properties, "somePositiveIntProperty");
    }

    

    private static void validateNotEmpty(Properties properties, String propertyName) throws InvalidInputException {
        String propertyValue = properties.getProperty(propertyName);

        if (propertyValue == null || propertyValue.trim().isEmpty()) {
            throw new InvalidInputException("Invalid configuration: " + propertyName + " is null or empty");
        }
    }

    private static void validatePositiveIntegerProperty(Properties properties, String propertyName)
            throws InvalidInputException {
        String propertyValue = properties.getProperty(propertyName);

        validateNotEmpty(properties, propertyName);

        try {
            int value = Integer.parseInt(propertyValue);

            if (value <= 0) {
                throw new InvalidInputException("Invalid configuration: " + propertyName + " must be a positive integer");
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid configuration: " + propertyName + " is not a valid integer");
        }
    }

    public static List<String> readDataFromFile() throws FileHandlingException {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE_PATH))) {
            List<String> dataLines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                dataLines.add(line);
            }
            return dataLines;
        } catch (IOException e) {
            throw new FileHandlingException("Error reading data file", e);
        }
    }

    public static void writeDataToFile(List<String> data) throws FileHandlingException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE_PATH))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new FileHandlingException("Error writing data file", e);
        }
    }
}
