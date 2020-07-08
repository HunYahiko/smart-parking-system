package com.utm.stanislav.parkingapp.service.level;

import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.repository.LevelRepository;
import com.utm.stanislav.parkingapp.web.dto.LevelNameListingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LevelServiceImplTest {
    
    @Mock
    private LevelRepository levelRepository;
    
    @InjectMocks
    private LevelServiceImpl levelService;
    
    private Parking parking;
    @BeforeEach
    void setUp() {
        parking = Mockito.mock(Parking.class);
    }
    
    @Test
    void getAllFrom_whenParkingDoesNotExists_returnEmptyList() {
        when(levelRepository.findAllByParking(parking)).thenReturn(Collections.emptyList());
        
        assertThat(levelService.getAllFrom(parking).isEmpty()).isTrue();
        verify(levelRepository).findAllByParking(parking);
    }
    
    @Test
    void getAllFrom_whenParkingExists_returnsNonEmptyList() {
        Level level = Mockito.mock(Level.class);
        List<Level> levels = Arrays.asList(level, level, level);
        
        when(levelRepository.findAllByParking(parking)).thenReturn(levels);
        
        assertThat(levelService.getAllFrom(parking)).isEqualTo(levels);
        verify(levelRepository).findAllByParking(parking);
    }
    
    @Test
    void getNameListingFrom_whenParkingDoesNotExists_returnsEmptyList() {
        when(levelRepository.findAllByParking(parking)).thenReturn(Collections.emptyList());
        
        assertThat(levelService.getNameListingFrom(parking).isEmpty()).isTrue();
        verify(levelRepository).findAllByParking(parking);
    }
    
    @Test
    void getNameListingFrom_whenParkingDoesExists_returnListOfTheSameSize() {
        List<Level> levels = Mockito.anyList();
        lenient().when(levelRepository.findAllByParking(parking)).thenReturn(levels);
        assertThat(levelService.getNameListingFrom(parking).size()).isEqualTo(levels.size());
    }
    
}
