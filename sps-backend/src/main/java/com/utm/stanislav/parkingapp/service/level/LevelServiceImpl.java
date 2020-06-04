package com.utm.stanislav.parkingapp.service.level;

import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.repository.LevelRepository;
import com.utm.stanislav.parkingapp.web.dto.LevelNameListingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {
    
    private final LevelRepository levelRepository;
    
    @Override
    @Transactional
    public List<Level> getAllFrom(Parking parking) {
        return levelRepository.findAllByParking(parking);
    }
    
    @Override
    @Transactional
    public Optional<Level> getRandomFrom(Parking parking) {
        return levelRepository.findFirstByParking(parking);
    }
    
    @Override
    @Transactional
    public List<LevelNameListingDto> getNameListingFrom(Parking parking) {
        List<Level> levels = getAllFrom(parking);
        return levels.stream()
                .map(this::mapToNameListing)
                .collect(Collectors.toList());
    }
    
    private LevelNameListingDto mapToNameListing(Level level) {
        return LevelNameListingDto.builder()
                       .levelId(level.getId())
                       .levelName(level.getLogicalId())
                       .build();
    }
}
