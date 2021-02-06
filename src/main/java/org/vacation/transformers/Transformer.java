package org.vacation.transformers;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

public abstract class Transformer<D,E> {

    public abstract D toDto(E e);

    public abstract E toEntity(D d);

    public List<D> toDtoList(List<E> entityList) {
        /* return entityList.stream()
                    .map(e -> toDto(e))
                    .collect(toList()); */
        List<D> dtoList = new ArrayList<>();
        for (E entity : entityList) {
            D dto = toDto(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public List<E> toEntityList(List<D> dtoList) {
        return dtoList.stream()
                    .map(d -> toEntity(d))
                    .collect(toList());
    }

}
