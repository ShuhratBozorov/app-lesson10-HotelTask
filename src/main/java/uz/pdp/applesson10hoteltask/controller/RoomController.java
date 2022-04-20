package uz.pdp.applesson10hoteltask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.applesson10hoteltask.Entity.Hotel;
import uz.pdp.applesson10hoteltask.Entity.Room;
import uz.pdp.applesson10hoteltask.payload.RoomDto;
import uz.pdp.applesson10hoteltask.repository.HotelRepository;
import uz.pdp.applesson10hoteltask.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto) {
        boolean exists = roomRepository.existsByNumberAndFloorAndSizeAndHotel_Id(
                roomDto.getNumber(), roomDto.getFloor(),roomDto.getSize(),roomDto.getHotelId());
        if (exists)
            return "This Hotel such room exist";
        Room room = new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent())
            return "Hotel not found";
        room.setHotel(optionalHotel.get());
        roomRepository.save(room);
        return "Room saved";
    }

    @GetMapping("/byHotelId/{hotelId}")
    public List<Room> getRoomsByHotelId(@PathVariable Integer hotelId) {
        List<Room> allByHotel_id = roomRepository.findAllByHotel_Id(hotelId);
        return allByHotel_id;
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id) {
        try {
            roomRepository.deleteById(id);
            return "Room deleted";
        } catch (Exception e) {
            return "Error in deleting";
        }
    }

    @PutMapping("/{id}")
    public String editRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            room.setNumber(room.getNumber());
            room.setFloor(room.getFloor());
            room.setSize(room.getSize());
            Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
            if (!optionalHotel.isPresent()){
                return "Hotel not found";
            }
            room.setHotel(optionalHotel.get());
            roomRepository.save(room);
            return "Room edited";
        }
        return "Room not found";
    }

    @GetMapping("/{hotelId}")
    public Page<Room> getRoomListForHotel(@PathVariable Integer hotelId,
                                                     @RequestParam int page) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> roomPage = roomRepository.findAllByHotel_Id(hotelId, pageable);
        return roomPage;
    }






}
