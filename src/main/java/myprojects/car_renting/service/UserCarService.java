package myprojects.car_renting.service;

import myprojects.car_renting.database.CarRepository;
import myprojects.car_renting.database.RentRepository;
import myprojects.car_renting.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserCarService {

    @Autowired
    private CarRepository carRepo;

    @Autowired
    private RentRepository rentRepo;



    // Nem kér be adatot, visszaadja az összes kocsit
    public List<Car> getAllCars() {

        List<Car> cars = carRepo.findAll();
        return  cars;
    }

    // Keresés
    public List<Car> searchCarsByCustomer (String location, Date dateOn, Date dateOff, String gear, String seats) {

        List<Car> cars = carRepo.searchCarsByCustomer(location);

        // Ha nincs pipálva egy checkbox sem / ha mind a kettő pipálva van, nem nyúl hozzá
        // Ha csak az egyikre van keresés, csak akkor szűri
        if (gear != null && gear.split(",").length == 1){
            for (int i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                if (!car.getGearType().equals(gear)) {
                    cars.remove(i);
                    i--;
                }
            }
        }


        // Ahány darab kérés jön az ülések számáról

        // Ha nincs rá keresés / mind a háromra van, akkor nem kell vele foglalkozni
        if (seats != null) {
            int searchesForSeats = seats.split(",").length;
            String[] seatArray = seats.split(",");

            // Amennyiben csak 1 pipa van ülésekre, csak ezt vizsgálja
            if (searchesForSeats == 1){
                for (int i = 0; i < cars.size(); i++) {
                    Car car = cars.get(i);
                    if (!car.getSeats().equals(seatArray[0])) {
                        cars.remove(i);
                        i--;
                    }
                }

                // Ha két ülésre is van keresés, mindkettőt nézi
            } else if (searchesForSeats == 2) {
                for (int i = 0; i < cars.size(); i++) {
                    Car car = cars.get(i);
                    if (!car.getSeats().equals(seatArray[0]) && !car.getSeats().equals(seatArray[1])) {
                        cars.remove(i);
                        i--;
                    }
                }
            }
        }


        return  cars;
    }


    // Car id-t vár, visszatérési értéke a megtalált autó példánya / null
    public Car getCarById(int carId) {
        Car car = carRepo.getReferenceById(carId);

        return car;
    }
    
    

    // A diff értéke a foglalás kezdete és vége közti NAP különbség
    // Az oldal (rentpage.html) bal oldalán zárójelben jelenik meg, hogy x(diff) nap * kocsi értéke 
    public long getTimeDifference(Date dateOn, Date dateOff) {
        long diffInMillies = Math.abs(dateOff.getTime() - dateOn.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        
        return diff;
    }



    // A bérlés teljes összegének megjelenítéséhez kell
    public long getCostOfCar(String cost, long diffInDays) {
        int carCost = Integer.parseInt(cost);
        long costSum = carCost * diffInDays;

        return costSum;
    }
}