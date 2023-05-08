package myprojects.car_renting.database;

import myprojects.car_renting.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<Rent, Integer> {

}
