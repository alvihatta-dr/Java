
public class Main {

    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle("Generic", 50);
        vehicle.displayInfo();
        vehicle.accelerate();
        vehicle.accelerate(20);

        System.out.println();

        Car car = new Car("Toyota", 80, 4);
        car.displayInfo();
        car.accelerate();
        car.accelerate(30);

        System.out.println();

        Bike bike = new Bike("Yamaha", 40, true);
        bike.displayInfo();
        bike.accelerate();
        bike.accelerate(15);
    }
}
