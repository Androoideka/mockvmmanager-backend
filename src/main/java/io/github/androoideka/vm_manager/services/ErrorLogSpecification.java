package io.github.androoideka.vm_manager.services;

import org.springframework.data.jpa.domain.Specification;

import io.github.androoideka.vm_manager.model.ErrorLog;
import io.github.androoideka.vm_manager.model.Machine;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Set;

public class ErrorLogSpecification implements Specification<ErrorLog> {

    private final Set<Long> machineIds;

    public ErrorLogSpecification(Set<Long> machineIds) {
        this.machineIds = machineIds;
    }

    @Override
    public Predicate toPredicate(Root<ErrorLog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<Machine, ErrorLog> machineErrors = root.join("machine");
        CriteriaBuilder.In<Long> list = criteriaBuilder.in(machineErrors.get("machineId"));
        for (Long machineId : machineIds) {
            list.value(machineId);
        }
        return list;
    }
}
