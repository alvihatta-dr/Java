
class Car extends Vehicle {

    private final int doors;

    public Car(String brand, int speed, int doors) {
        super(brand, speed);
        this.doors = doors;
    }

    // Overriding method
    @Override
    public void displayInfo() {
        System.out.println("Car Brand: " + brand + ", Speed: " + speed + " km/h, Doors: " + doors);
    }
}
