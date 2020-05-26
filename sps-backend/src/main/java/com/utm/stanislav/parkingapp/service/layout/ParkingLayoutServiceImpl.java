package com.utm.stanislav.parkingapp.service.layout;

import com.utm.stanislav.parkingapp.model.LayoutObject;
import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.exceptions.LevelNotFoundException;
import com.utm.stanislav.parkingapp.repository.LevelRepository;
import com.utm.stanislav.parkingapp.repository.ParkingLotRepository;
import com.utm.stanislav.parkingapp.service.level.LevelService;
import com.utm.stanislav.parkingapp.web.dto.LevelLayoutDTO;
import com.utm.stanislav.parkingapp.web.dto.ParkingLayoutDTO;
import com.utm.stanislav.parkingapp.web.dto.ParkingLotPositionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingLayoutServiceImpl implements ParkingLayoutService {
    
    private final ParkingLotRepository parkingLotRepository;
    private final LevelRepository levelRepository;
    
    @Override
    @Transactional
    public LevelLayoutDTO getLevelLayout(UUID levelId) throws LevelNotFoundException {
        Level level = levelRepository.findById(levelId)
                .orElseThrow(LevelNotFoundException::new);
        
        List<LayoutObject> levelLayoutObjects = level.getLevelLayout().getLayoutObjects();
        Integer width = level.getLevelLayout().getWidth();
        Integer length = level.getLevelLayout().getLength();
        
        return new LevelLayoutDTO(levelLayoutObjects, width, length);
        
//        List<ParkingLotPositionDTO> parkingLotsPositions = parkingLotRepository.findAllByLevelId(levelId)
//                       .stream()
//                       .map(this::mapToDto)
//                       .collect(Collectors.toList());
//        Integer rows = parkingLotsPositions.stream()
//                .mapToInt(ParkingLotPositionDTO::getXPosition)
//                .max().orElseThrow(Exception::new) + 1;
//
//        Integer columns = parkingLotsPositions.stream()
//                .mapToInt(ParkingLotPositionDTO::getYPosition)
//                .max().orElseThrow(Exception::new) + 1;
//
//        return new ParkingLayoutDTO(parkingLotsPositions, rows, columns);
    }
    
    private ParkingLotPositionDTO mapToDto(ParkingLot parkingLot) {
        Integer xPosition = parkingLot.getParkingLotPosition().getXPosition();
        Integer yPosition = parkingLot.getParkingLotPosition().getYPosition();
        
        return new ParkingLotPositionDTO(
                parkingLot.getLogicalId(),
                xPosition,
                yPosition
        );
    }
}
