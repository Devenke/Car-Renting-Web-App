package myprojects.car_renting.controller;

import myprojects.car_renting.model.Car;
import myprojects.car_renting.model.Rent;
import myprojects.car_renting.service.AdminCarService;
import myprojects.car_renting.service.AdminRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AdminCarService adminCarService;
    @Autowired
    private AdminRentService adminRentService;



    //Admin felület kezdő oldal
    @GetMapping("/adminpage")
    public String adminPage() {

        return "admin/adminpage.html";
    }




    // Megjeleníti az összes foglalást
    // Ezeket törölni lehet
    @GetMapping("/rents")
    public String adminPageRents(
            Model model
    ) {
        List<Rent> rents = adminRentService.getAllRents();

        model.addAttribute("rents", rents);

        return "admin/adminrents.html";
    }




    // A törlésre kattintva kitörli a kiválasztott rendelést, amennyiben az nem futó rendelés
    // Újra betölti az oldalt a frissült adatokkal
    @PostMapping("/deleterent")
    public String deleteRent(
            Model model,
            @RequestParam("rentid") int rentId
    ) {

        Rent rent = adminRentService.getRentById(rentId);

        //Ha a rendelés jelenleg folyamatban van, kap egy 'Aktív rendelés' jelzőt
        java.util.Date currentTime = new java.util.Date();
        if (adminRentService.deleteRent(rent)) {
            model.addAttribute("feedback", "Aktív rendelés");
            model.addAttribute("rentid", rentId);
        }

        adminPageRents(model);

        return "admin/adminrents.html";
    }




    //Autók szerkesztése gombra nyílik meg
    //Listázza az összes kocsit
    @GetMapping("/carupdatepage")
    public String adminPageCarUpdate(
            Model model
    ) {

        List<Car> allCars = adminCarService.getAllCars();

        model.addAttribute("cars", allCars);

        return "admin/carupdatepage.html";
    }





    // A kívánt kocsit kiválasztva nyílik meg
    // Adatbekérő oldal, placeholder-nek a kocsi jelenlegi adatai jelennek meg, ezzel könnyítve a módosítást
    @GetMapping("/editcarinfo")
    public String editCarInfo(
            Model model,
            @RequestParam("carid") int carId
    ) {

        Car car = adminCarService.getCarById(carId);

        model.addAttribute("car", car);

        return "admin/editcarinfo.html";
    }





    // Update gombra mindent frissít, amire új adatot kapott.
    // A változtatás bekerül a car_update_log táblába
    // Adat nélküli kattintásra nem történik semmi.
    @PostMapping("/updatecar")
    public String updateCar(
            Model model,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "cost", required = false) String cost,
            @RequestParam(value = "geartype", required = false) String gearType,
            @RequestParam(value = "seats", required = false) String seats,
            @RequestParam(value = "available", required = false) String available,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("carid") int carId
    ) throws IOException {

        Car car = adminCarService.getCarById(carId);

        adminCarService.updateCar(car, title, description, cost, gearType, seats, available, location, file);

        adminPageCarUpdate(model);

        return "admin/carupdatepage.html";
    }



    

    // Adat bekérő oldalra visz egyből
    // Kitöltés kötelező minden attribútumra
    @GetMapping("/newcarpage")
    public String newCarPage() {

        return "admin/adminnewcar.html";
    }





    // Felvitt adatokkal felkerül az adatbázisba
    // Betölti a kezdő admin paget
    @PostMapping("/insertcar")
    public String insertNewCar(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "cost") String cost,
            @RequestParam(value = "geartype") String gearType,
            @RequestParam(value = "seats") String seats,
            @RequestParam(value = "available") String available,
            @RequestParam(value = "location") String location,
            @RequestParam(value = "file") MultipartFile file
    ) throws IOException {

        Car car = new Car(title, description, cost, gearType, seats, available, location, file.getBytes());

        adminCarService.insertCar(car);

        return "admin/adminpage.html";
    }
}