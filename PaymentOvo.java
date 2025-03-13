public class PaymentOVO extends Payment {
    private String ovoNumber;

    public PaymentOVO(String customerId, double amount, String ovoNumber) {
        super(customerId, amount);
        this.ovoNumber = ovoNumber;
    }

    @Override
    public void pay() {
        System.out.println("Transferring Rp" + super.amount + " using OVO to " + ovoNumber);
    }
}
