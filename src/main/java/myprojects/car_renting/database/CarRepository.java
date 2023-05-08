package myprojects.car_renting.database;

import myprojects.car_renting.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query("SELECT c FROM Car c WHERE c.available ='t'" +
            " AND c.location =(:location)")
    List<Car> searchCarsByCustomer(@Param("location") String location);
}