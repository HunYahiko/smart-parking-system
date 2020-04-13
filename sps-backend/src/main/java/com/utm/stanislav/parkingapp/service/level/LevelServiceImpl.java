package com.utm.stanislav.parkingapp.service.level;

import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.repository.LevelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
public class LevelServiceImpl implements LevelService {
    
    private LevelRepository levelRepository;
    
    @Inject
    public void setLevelRepository(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Level> getLevelsFromParking(Parking parking) {
        return this.levelRepository.findAllByParking(parking);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Level getRandomLevelFrom(Parking parking) {
        return this.levelRepository.findFirstByParking(parking);
    }
}
