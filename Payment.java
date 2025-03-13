public class Payment {
    private String customerId;
    private double amount;
    private String paymentDate;

    public Payment(String customerId, double amount) {
        this.customerId = customerId;
        this.amount = amount;
        this.paymentDate = generateDate();
    }

    public String generateDate() {
        return "2025-03-13"; // Contoh tanggal saat ini
    }

    public void pay() {
        System.out.println("Processing payment of Rp" + amount + " for customer ID: " + customerId);
    }
}
