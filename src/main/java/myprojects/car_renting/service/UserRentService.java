package myprojects.car_renting.service;

import myprojects.car_renting.database.RentRepository;
import myprojects.car_renting.model.Rent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRentService {

    @Autowired
    private RentRepository rentRepo;



    // Foglalás insert
    public void insertRent(Rent rent) {
        rentRepo.save(rent);
    }
}
