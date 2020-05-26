package com.utm.stanislav.parkingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.utm.stanislav.parkingapp.model.enums.LayoutObjectOrientation;
import com.utm.stanislav.parkingapp.model.enums.LayoutObjectType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sps_layout_object")
@Data
@NoArgsConstructor
public class LayoutObject extends GenericEntity {
    
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private LayoutObjectType type;
    
    @Column(name = "orientation")
    @Enumerated(EnumType.STRING)
    private LayoutObjectOrientation orientation;
    
    @Column(name = "x")
    @JsonProperty("x")
    private Integer xPosition;
    
    @Column(name = "y")
    @JsonProperty("y")
    private Integer yPosition;
    
    @ManyToOne
    @JoinColumn(name = "level_layout_id", nullable = false)
    @JsonIgnore
    private LevelLayout levelLayout;
    
}
