package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.enums.ParkingStatus;
import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.RPiBridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, UUID> {

    List<ParkingLot> findAllByrPiBridge(RPiBridge rPiBridge);
    Optional<ParkingLot> findFirstByLevelAndParkingStatus(Level level, ParkingStatus parkingStatus);
    List<ParkingLot> findByLevelAndParkingStatus(Level level, ParkingStatus parkingStatus);
    
    @Query(nativeQuery = true, value = "select spl.* from sps_parking_lot spl\n" +
                                               "left join sps_level sl\n" +
                                               "on spl.level_id = sl.id\n" +
                                               "left join sps_parking sp\n" +
                                               "on sl.parking_id = sp.id\n" +
                                               "where sp.id = ?1\n")
    List<ParkingLot> findAllByParkingId(UUID parkingId);
    
    @Query(nativeQuery = true, value = "select spl.* from sps_parking_lot spl\n" +
                                               "where spl.level_id = ?1\n")
    List<ParkingLot> findAllByLevelId(UUID levelId);
}
