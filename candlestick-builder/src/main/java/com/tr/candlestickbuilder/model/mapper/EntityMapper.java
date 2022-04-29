package com.tr.candlestickbuilder.model.mapper;

import java.util.List;
import java.util.Map;

public interface EntityMapper<D, E> {
    D toDto(E entity);
    E toEntity(D dto);
    List<D> toDtoLists(List<E> entities);
    List<E> toEntityLists(List<D> dtos);
    Map<String, D> toDtoMaps(Map<String, E> entities);
    Map<String, E> toEntityMaps(Map<String, D> dtos);
}
