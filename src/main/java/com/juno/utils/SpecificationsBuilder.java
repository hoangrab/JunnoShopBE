package com.juno.utils;

import jakarta.persistence.EnumType;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SpecificationsBuilder<T> {
    private final List<Specification<T>> specifications = new ArrayList<>();
    private boolean isOrCondition = false;

    public SpecificationsBuilder<T> with(Specification<T> specification) {
        specifications.add(specification);
        return this;
    }

    public SpecificationsBuilder<T> with(Consumer<SpecificationsBuilder<T>> specificationConsumer) {
        specificationConsumer.accept(this);
        return this;
    }

    public SpecificationsBuilder<T> like(String field, String value) {
        specifications.add((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(field), "%" + value + "%")
        );
        return this;
    }

    public SpecificationsBuilder<T> enumSearch(String field, String value) {
        specifications.add((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(field), Enum.valueOf(EnumType.class, value))
        );
        return this;
    }

    public SpecificationsBuilder<T> booleanSearch(String field, Boolean value) {
        specifications.add((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(field), value)
        );
        return this;
    }

    public SpecificationsBuilder<T> integerSearch(String field, Integer value) {
        specifications.add((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(field), value)
        );
        return this;
    }

    public SpecificationsBuilder<T> and(Consumer<SpecificationsBuilder<T>> specificationConsumer) {
        boolean originalCondition = isOrCondition;
        isOrCondition = false;
        specificationConsumer.accept(this);
        isOrCondition = originalCondition;
        return this;
    }

    public SpecificationsBuilder<T> or(Consumer<SpecificationsBuilder<T>> specificationConsumer) {
        boolean originalCondition = isOrCondition;
        isOrCondition = true;
        specificationConsumer.accept(this);
        isOrCondition = originalCondition;
        return this;
    }

    public Specification<T> build() {
        if (specifications.isEmpty()) {
            return null;
        }

        Specification<T> result = specifications.get(0);
        for (int i = 1; i < specifications.size(); i++) {
            Specification<T> specification = specifications.get(i);
            result = isOrCondition ? Specification.where(result).or(specification) : result.and(specification);
        }
        return result;
    }
}
