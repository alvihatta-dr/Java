public class App {
    public static void main(String[] args) {
        PaymentOVO ovoPayment = new PaymentOVO("123456", 50000, "081234567890");
        PaymentGoPay gopayPayment = new PaymentGoPay("654321", 75000, "089876543210");

        ovoPayment.pay();  // Memanggil metode pay() dari PaymentOVO
        gopayPayment.pay();  // Memanggil metode pay() dari PaymentGoPay
    }
}
