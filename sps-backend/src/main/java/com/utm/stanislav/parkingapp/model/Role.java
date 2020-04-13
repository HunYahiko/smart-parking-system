package com.utm.stanislav.parkingapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "sps_role")
@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@AllArgsConstructor
public class Role extends GenericEntity {
    
    @Column(name = "name")
    private String name;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
