public class PaymentGoPay extends Payment {
    private String gopayNumber;

    public PaymentGoPay(String customerId, double amount, String gopayNumber) {
        super(customerId, amount);
        this.gopayNumber = gopayNumber;
    }

    @Override
    public void pay() {
        System.out.println("Transferring Rp" + super.amount + " using GoPay to " + gopayNumber);
    }
}
