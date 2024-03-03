import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List; 

class StoreManagementSystemTest {

    @Test
    void testProductConstructor() {
        Product product = new Product("TestProduct", 50.0);
        assertEquals("TestProduct", product.getProductName());
        assertEquals(50.0, product.getPrice());
    }

    @Test
    void testOrderItemConstructor() {
        Product product = new Product("TestProduct", 50.0);
        OrderItem orderItem = new OrderItem(product, 3);
        assertEquals(product, orderItem.getProduct());
        assertEquals(3, orderItem.getQuantity());
    }

    @Test
    void testPrintableOrderItemConstructor() {
        Product product = new Product("TestProduct", 50.0);
        PrintableOrderItem printableOrderItem = new PrintableOrderItem(product, 2);
        assertEquals(product, printableOrderItem.getProduct());
        assertEquals(2, printableOrderItem.getQuantity());
    }

    @Test
    void testCalculateTotal() {
        Product product = new Product("TestProduct", 50.0);
        OrderItem[] orderItems = {new OrderItem(product, 2), new OrderItem(product, 1)};
        ShoppingCart cart = new ShoppingCart(product, orderItems);
        assertEquals(150.0, cart.calculateTotal());
    }

    @Test
    void testDiscountedProductCalculateDiscount() {
        DiscountedProduct discountedProduct = new DiscountedProduct("DiscountedProduct", 100.0);
        assertEquals(0.1, discountedProduct.calculateDiscount());
    }



    @Test
    void testWriteDataToFile() throws FileHandlingException {
        List<String> newData = Arrays.asList("New Line 1", "New Line 2");
        StoreManagementSystem.writeDataToFile(newData);

        List<String> dataLines = StoreManagementSystem.readDataFromFile();
        assertEquals(2, dataLines.size());
        assertEquals("New Line 1", dataLines.get(0));
        assertEquals("New Line 2", dataLines.get(1));
    }



    
}
