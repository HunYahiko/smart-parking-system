package com.utm.stanislav.parkingapp.service.level;

import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {
    
    private final LevelRepository levelRepository;
    
    @Override
    @Transactional
    public List<Level> getLevelsFromParking(Parking parking) {
        return this.levelRepository.findAllByParking(parking);
    }
    
    @Override
    @Transactional
    public Level getRandomLevelFrom(Parking parking) {
        return this.levelRepository.findFirstByParking(parking);
    }
}
