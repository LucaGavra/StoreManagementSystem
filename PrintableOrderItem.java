class PrintableOrderItem extends OrderItem implements Printable {
    public PrintableOrderItem(Product product, int quantity) {
        super(product, quantity);
    }

    @Override
    public void printDetails() {
        System.out.println("Printable Order Item Details:");
        System.out.println("Product: " + getProduct().getProductName());
        System.out.println("Quantity: " + getQuantity());
    }
}