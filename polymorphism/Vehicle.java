
class Vehicle {

    protected String brand;
    protected int speed;

    public Vehicle(String brand, int speed) {
        this.brand = brand;
        this.speed = speed;
    }

    public void displayInfo() {
        System.out.println("Brand: " + brand + ", Speed: " + speed + " km/h");
    }

    // Overloaded method
    public void accelerate() {
        speed += 10;
        System.out.println("Vehicle accelerated. New speed: " + speed + " km/h");
    }

    public void accelerate(int increase) {
        speed += increase;
        System.out.println("Vehicle accelerated by " + increase + " km/h. New speed: " + speed + " km/h");
    }
}
