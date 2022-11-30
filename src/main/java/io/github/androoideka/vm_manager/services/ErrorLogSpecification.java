package io.github.androoideka.vm_manager.services;

import org.springframework.data.jpa.domain.Specification;

import io.github.androoideka.vm_manager.model.ErrorLog;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Set;

public class ErrorLogSpecification implements Specification<ErrorLog> {

    private final Set<Long> machineIds;

    public ErrorLogSpecification(Set<Long> machineIds) {
        this.machineIds = machineIds;
    }

    @Override
    public Predicate toPredicate(Root<ErrorLog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        CriteriaBuilder.In<Long> list = criteriaBuilder.in(root.get("machine"));
        for (Long machineId : machineIds) {
            list.value(machineId);
        }
        return list;
    }
}
