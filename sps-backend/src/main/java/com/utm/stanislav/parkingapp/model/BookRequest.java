package com.utm.stanislav.parkingapp.model;

import com.utm.stanislav.parkingapp.model.enums.BookRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sps_book_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest extends GenericEntity {
    
    @OneToOne
    @JoinColumn(name = "parking_lot_id", unique = true, nullable = false)
    private ParkingLot parkingLot;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "failed_attempts")
    private Integer failedAttempts;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookRequestStatus bookRequestStatus;
    
    public BookRequest(User user) {
        this.user = user;
    }
}
