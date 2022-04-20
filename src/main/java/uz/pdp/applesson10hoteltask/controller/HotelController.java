package uz.pdp.applesson10hoteltask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.applesson10hoteltask.Entity.Hotel;
import uz.pdp.applesson10hoteltask.Entity.Room;
import uz.pdp.applesson10hoteltask.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    @PostMapping
    public String addHotel(@RequestBody Hotel hotel) {
        boolean existsByName = hotelRepository.existsByName(hotel.getName());
        if (existsByName)
            return "This hotel already exist";
        hotelRepository.save(hotel);
        return "Hotel added";
    }

    @GetMapping("/{id}")
    public Hotel getHotelId(@PathVariable Integer id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){
            Hotel hotel = optionalHotel.get();
            return hotel;
        }
        return new Hotel();
    }
}
