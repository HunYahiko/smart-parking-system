package com.utm.stanislav.parkingapp.service.rpibridge;

import com.utm.stanislav.parkingapp.model.RPiBridge;
import com.utm.stanislav.parkingapp.model.exceptions.RPiBridgeNotFoundException;
import com.utm.stanislav.parkingapp.repository.RPiBridgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RPiBridgeServiceImplTest {
    
    @Mock
    private RPiBridgeRepository rPiBridgeRepository;
    
    @InjectMocks
    private RPiBridgeServiceImpl rPiBridgeService;
    
    private RPiBridge rPiBridge = Mockito.mock(RPiBridge.class);
    private String mockString;
    
    @BeforeEach
    void setUp() {
        mockString = Mockito.anyString();
    }
    
    @Test
    void getByLogicalId_whenRPiBridgeDoesntExists_returnsEmptyOptional() {
        when(rPiBridgeRepository.findByLogicalId(mockString)).thenReturn(Optional.empty());
        
        assertThat(rPiBridgeService.getByLogicalId(mockString)).isEqualTo(Optional.empty());
        verify(rPiBridgeRepository).findByLogicalId(mockString);
    }
    
    @Test
    void getByLogicalId_whenRPiBridgeExists_returnsRPiBridgeOptional() {
        when(rPiBridgeRepository.findByLogicalId(mockString)).thenReturn(Optional.of(rPiBridge));
        
        assertThat(rPiBridgeService.getByLogicalId(mockString)).isEqualTo(Optional.of(rPiBridge));
        verify(rPiBridgeRepository).findByLogicalId(mockString);
    }
    
    @Test
    void getBySessionId_whenNoRPiBridgeExistsWithSuchSessionId_returnsEmptyOptional() {
        when(rPiBridgeRepository.findBySessionId(mockString)).thenReturn(Optional.empty());
        
        assertThat(rPiBridgeService.getBySessionId(mockString)).isEqualTo(Optional.empty());
        verify(rPiBridgeRepository).findBySessionId(mockString);
    }
    
    @Test
    void getBySessionId_whenRPiBridgeExistsWithSuchSessionId_returnsRPiBridgeOptional() {
        when(rPiBridgeRepository.findBySessionId(mockString)).thenReturn(Optional.of(rPiBridge));
        
        assertThat(rPiBridgeService.getBySessionId(mockString)).isEqualTo(Optional.of(rPiBridge));
        verify(rPiBridgeRepository).findBySessionId(mockString);
    }
    
    @Test
    void setSessionIdOn_whenRPiBridgeDoesNotExists_throwsRPiBridgeNotFoundException() {
        when(rPiBridgeRepository.findByLogicalId(mockString)).thenReturn(Optional.empty());
        
        assertThrows(RPiBridgeNotFoundException.class,
                () -> rPiBridgeService.setSessionIdOn(mockString, mockString));
    
        verify(rPiBridge, never()).setIsConnected(Mockito.anyBoolean());
        verify(rPiBridge, never()).setSessionId(Mockito.anyString());
    }
    
    @Test
    void setSessionIdOn_whenRPiBridgeExists_setsSessionIdAndIsConnectedOnRPiBridge() {
        when(rPiBridgeRepository.findByLogicalId(mockString)).thenReturn(Optional.of(rPiBridge));
        
        assertDoesNotThrow(() -> rPiBridgeService.setSessionIdOn(mockString, mockString));
        
        verify(rPiBridge).setIsConnected(Boolean.TRUE);
        verify(rPiBridge).setSessionId(mockString);
    }
    
    @Test
    void removeSessionIdFrom_whenRPiDoesNotExist_throwsRPiBridgeNotFoundException() {
        when(rPiBridgeRepository.findBySessionId(mockString)).thenReturn(Optional.empty());
        
        assertThrows(RPiBridgeNotFoundException.class, () -> rPiBridgeService.removeSessionIdFrom(mockString));
    
        verify(rPiBridge, never()).setIsConnected(Mockito.anyBoolean());
        verify(rPiBridge, never()).setSessionId(Mockito.anyString());
    }
    
    @Test
    void removeSessionIdFrom_whenRPiBridgeExists_removeSessionIdAndSetIsConnectedToFalseOnRPiBridge() {
        when(rPiBridgeRepository.findBySessionId(mockString)).thenReturn(Optional.of(rPiBridge));
        
        assertDoesNotThrow(() -> rPiBridgeService.removeSessionIdFrom(mockString));
    
        verify(rPiBridge).setIsConnected(Boolean.FALSE);
        verify(rPiBridge).setSessionId(null);
    }
}
