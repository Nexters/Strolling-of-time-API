package com.nexters.wiw.api.service.specification;

import com.nexters.wiw.api.domain.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupSpecification {
    public static Specification<Group> search(Map<String, Object> filter) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            filter.forEach((key, value) -> {
                String likeValue = "%" + value + "%";

                switch (key) {
                    case "name":
                        predicates.add(criteriaBuilder.like(root.get(key).as(String.class), likeValue));
                        break;
                    case "category":
                        predicates.add(criteriaBuilder.equal(root.get(key).as(String.class), value));
                }
            });
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
