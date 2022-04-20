package uz.pdp.applesson10hoteltask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.applesson10hoteltask.Entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
    boolean existsByName(String name);
}
