
class Bike extends Vehicle {

    private final boolean hasCarrier;

    public Bike(String brand, int speed, boolean hasCarrier) {
        super(brand, speed);
        this.hasCarrier = hasCarrier;
    }

    // Overriding method
    @Override
    public void displayInfo() {
        System.out.println("Bike Brand: " + brand + ", Speed: " + speed + " km/h, Has Carrier: " + hasCarrier);
    }
}
