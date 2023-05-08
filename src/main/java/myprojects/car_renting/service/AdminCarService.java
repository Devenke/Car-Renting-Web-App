package myprojects.car_renting.service;

import myprojects.car_renting.database.CarRepository;
import myprojects.car_renting.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AdminCarService {

    @Autowired
    private CarRepository carRepo;




    // Visszaadja az összes kocsit
    public List<Car> getAllCars() {
        return carRepo.findAll();
    }

    // Visszaadja a carId-hoz tartozó car példányt
    public Car getCarById(int carId) {
        return carRepo.getReferenceById(carId);
    }

    // Az admin felületen kapott adatokkal frissíti az autót az adatbázisban.
    public void updateCar(
            Car car, String title, String description,
            String cost, String gearType, String seats,
            String available, String location, MultipartFile file)
            throws IOException {


        if (!title.isEmpty()){
            car.setTitle(title);
        }
        if (!description.isEmpty()){
            car.setDescription(description);
        }
        if (!cost.isEmpty()){
            car.setCost(cost);
        }
        if (!gearType.isEmpty()){
            car.setGearType(gearType);
        }
        if (!seats.isEmpty()){
            car.setSeats(seats);
        }
        if (!available.isEmpty()){
            car.setAvailable(available);
        }
        if (!location.isEmpty()){
            car.setLocation(location);
        }
        if (!file.isEmpty()){
            car.setImage(file.getBytes());
        }

        carRepo.save(car);
    }

    // Új kocsi felvitele
    public void insertCar(Car car) {
        carRepo.save(car);
    }
}