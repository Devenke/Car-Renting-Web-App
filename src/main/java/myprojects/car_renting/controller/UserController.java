package myprojects.car_renting.controller;

import myprojects.car_renting.model.Car;
import myprojects.car_renting.model.Rent;
import myprojects.car_renting.service.UserCarService;
import myprojects.car_renting.service.UserRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserCarService userCarService;
    @Autowired
    private UserRentService userRentService;


    // Kezdő oldal, megjeleníti az összes kocsit
    // Az oldalon végre kell hajtani egy keresést, hogy tovább lehessen menni a rendelés felületre
    @GetMapping("/")
    public String startPage(
            Model model
    ) {

        List<Car> allCars = userCarService.getAllCars();
        model.addAttribute("cars", allCars);

        return "customer/udemx.html";
    }




    // Lekéri a keresés adatait, majd visszaadja a kezdő oldalt a
    // keresésben megadott adatoknak megfelelő, és elérhető autókkal
    // Váltó típusának, ülések számának megadása nem kötelező
    // Itt már meg fog jelenni az érdekel gomb, tovább lehet menni
    @GetMapping("/search")
    public String searchCars(
            Model model,
            @RequestParam("location") String location,
            @RequestParam("dateon") Date dateOn,
            @RequestParam("dateoff") Date dateOff,
            @RequestParam(value = "gear", required = false) String gear,
            @RequestParam(value = "seats", required = false) String seats
    ) {

        List<Car> cars = userCarService.searchCarsByCustomer(location, dateOn, dateOff, gear, seats);

        model.addAttribute("cars", cars);
        model.addAttribute("dateon", dateOn);
        model.addAttribute("dateoff", dateOff);
        model.addAttribute("location", location);

        return "customer/udemx.html";
    }




    // 'Érdekel' gombra kattintva hívódik meg
    //  A keresésben megadott adatok továbbítva vannak a rentpage.html oldalnak.
    //  Itt történik az adatbekérés, illetve az előző keresésnek megfelelően mutatja az oldal bal oldalán,
    //  hogy hány napra van a foglalás, és ez mennyibe fog kerülni.
    @GetMapping("/rent")
    public String rentPage(
            Model model,
            @RequestParam(value = "carid") int carId,
            @RequestParam(value = "dateon") Date dateOn,
            @RequestParam(value = "dateoff") Date dateOff,
            @RequestParam(value = "location") String location
    ) {
        Car car = userCarService.getCarById(carId);

        model.addAttribute("car", car);
        model.addAttribute("dateon", dateOn);
        model.addAttribute("dateoff", dateOff);
        model.addAttribute("location", location);

        // Bérlés hossz
        long diff = userCarService.getTimeDifference(dateOn, dateOff);
        model.addAttribute("days", diff);

        // Költség (megjelenítéshez)
        long costSum = userCarService.getCostOfCar(car.getCost(), diff);
        model.addAttribute("costsum", costSum);

        return "customer/rentpage.html";
    }




    // A bekért adatok, a keresés alapján előzőleg megadott dátum,
    // illetve az adott div car_id-ja szerint létrejön a rendelés
    // Visszaugrik a kezdő oldalra
    @PostMapping("/insertrent")
    public String firstPage(
            Model model,
            @RequestParam("firstname") String firstName,
            @RequestParam("secondname") String secondName,
            @RequestParam("email") String email,
            @RequestParam("phonenumber") String phonenumber,
            @RequestParam("idtype") String idtype,
            @RequestParam("idnumber") String idnumber,
            @RequestParam("postalcode") String postalCode,
            @RequestParam("city") String city,
            @RequestParam("address") String address,
            @RequestParam("carid") int carId,
            @RequestParam("dateon") Date dateOn,
            @RequestParam("dateoff") Date dateOff

    ) {

        Rent rent = new Rent(firstName, secondName, email, phonenumber, idtype, idnumber, postalCode, city, address, carId, dateOn, dateOff);
        userRentService.insertRent(rent);

        startPage(model);

        return "customer/udemx.html";
    }
}