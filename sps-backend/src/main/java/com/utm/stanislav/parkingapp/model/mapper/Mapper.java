package com.utm.stanislav.parkingapp.model.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@FunctionalInterface
public interface Mapper<E, D> {
    D map(E entity);
    
    default List<D> mapList(List<E> entities) {
        return entities.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
