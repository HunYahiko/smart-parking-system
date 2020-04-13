package com.utm.stanislav.parkingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class GenericEntity {
    
    @Id
    @Column(name = "id", length = 16, unique = true, nullable = false)
    protected UUID id = UUID.randomUUID();
    
    @Version
    private Long version;
//
//    @Transient
//    @JsonIgnore
//    private boolean isPersisted;
//
//    @Override
//    @JsonIgnore
//    public boolean isNew() {
//        return !isPersisted;
//    }
//
//    @PostPersist
//    private void setPersistence() {
//        this.isPersisted = true;
//    }
//
//    @JsonIgnore
//    public boolean isPersisted() {
//        return isPersisted;
//    }
}
