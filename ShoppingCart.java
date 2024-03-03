class ShoppingCart {
    private Product product;
    private OrderItem[] orderItems;

    public ShoppingCart(Product product, OrderItem[] orderItems) {
        this.product = product;
        this.orderItems = orderItems;
    }

    public void displayCart() {
        System.out.println("Shopping Cart for " + product.getProductName() + ":");
        for (OrderItem item : orderItems) {
            System.out.println(item.getQuantity() + " x " + item.getProduct().getProductName());
        }
    }

    public double calculateTotal() {
        double total = 0.0;
        for (OrderItem item : orderItems) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        return total;
    }

    public Product getProduct() {
        return null;
    }

    public OrderItem[] getOrderItems() {
        return null;
    }
}
