package com.utm.stanislav.parkingapp.service.layout;

import com.utm.stanislav.parkingapp.model.LayoutObject;
import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.exceptions.LevelNotFoundException;
import com.utm.stanislav.parkingapp.repository.LevelRepository;
import com.utm.stanislav.parkingapp.repository.ParkingLotRepository;
import com.utm.stanislav.parkingapp.web.dto.LevelLayoutDto;
import com.utm.stanislav.parkingapp.web.dto.ParkingLotPositionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingLayoutServiceImpl implements ParkingLayoutService {
    
    private final LevelRepository levelRepository;
    
    @Override
    @Transactional
    public LevelLayoutDto getLevelLayout(UUID levelId) throws LevelNotFoundException {
        Level level = levelRepository.findById(levelId)
                .orElseThrow(LevelNotFoundException::new);
        
        List<LayoutObject> levelLayoutObjects = level.getLevelLayout().getLayoutObjects();
        Integer width = level.getLevelLayout().getWidth();
        Integer length = level.getLevelLayout().getLength();
        
        return LevelLayoutDto
                       .builder()
                       .layoutObjects(levelLayoutObjects)
                       .length(length)
                       .width(width)
                       .build();
    }
}
