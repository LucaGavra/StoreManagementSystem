class DiscountedProduct extends Product implements Discountable {
    public DiscountedProduct(String productName, double price) {
        super(productName, price);
    }

    @Override
    public double calculateDiscount() {
        // Implement discount calculation logic
        return 0.1; // 10% discount for demonstration
    }
}