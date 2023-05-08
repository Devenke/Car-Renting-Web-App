package myprojects.car_renting.service;

import myprojects.car_renting.database.RentRepository;
import myprojects.car_renting.model.Rent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminRentService {

    @Autowired
    private RentRepository rentRepo;


    public List<Rent> getAllRents() {
        return rentRepo.findAll();
    }

    // Visszadobja a rendelés példányát
    public Rent getRentById(int rentId) {
        return rentRepo.getReferenceById(rentId);
    }


    // Törli a rendelést
    public boolean deleteRent(Rent rent) {

        boolean needsFeedback = false;
        java.util.Date currentTime = new java.util.Date();

        if (currentTime.getTime() < rent.getDateOn().getTime() || currentTime.getTime() > rent.getDateOff().getTime()) {
            rentRepo.delete(rent);
        } else {
            needsFeedback = true;
        }
        return needsFeedback;
    }
}