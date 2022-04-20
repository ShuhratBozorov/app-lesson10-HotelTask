package uz.pdp.applesson10hoteltask.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.applesson10hoteltask.Entity.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    boolean existsByNumberAndFloorAndSizeAndHotel_Id(Integer number, Integer floor, String size, Integer hotel_id);

    List<Room> findAllByHotel_Id(Integer hotel_id);

    Page<Room>findAllByHotel_Id(Integer hotel_id, Pageable pageable);
}
