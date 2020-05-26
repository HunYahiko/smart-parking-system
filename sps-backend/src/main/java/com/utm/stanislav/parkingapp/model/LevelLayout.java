package com.utm.stanislav.parkingapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sps_level_layout")
@Data
@NoArgsConstructor
public class LevelLayout extends GenericEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "levelLayout")
    private List<LayoutObject> layoutObjects;
    
    @Column(name = "width")
    private Integer width;
    
    @Column(name = "length")
    private Integer length;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "levelLayout")
    private Level level;
}
