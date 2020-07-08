package com.example.smartparkapp.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

@FunctionalInterface
public interface Mapper<E, D> {

    E map(D object);

    default List<E> mapList(List<D> objects) {
        return objects.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
