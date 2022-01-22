package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.services;

import org.springframework.data.jpa.domain.Specification;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.Machine;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.Status;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MachineSpecification implements Specification<Machine> {

    private final String name;
    private final List<Status> statuses;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final Long userId;
    private boolean showInactive;

    public MachineSpecification(String name, List<Status> statuses, LocalDate dateFrom, LocalDate dateTo, Long userId) {
        this.name = name;
        this.statuses = statuses;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.userId = userId;
        this.showInactive = false;
    }

    public void setShowInactive(boolean value) {
        this.showInactive = value;
    }

    @Override
    public Predicate toPredicate(Root<Machine> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(!showInactive) {
            predicates.add(criteriaBuilder.equal(root.get("active"), true));
        }
        if(userId != -1) {
            predicates.add(criteriaBuilder.equal(root.get("createdBy"), this.userId));
        }

        if (!name.isBlank()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + this.name.toLowerCase(Locale.ROOT) + "%"
            ));
        }
        Predicate statusPredicate = null;
        for(Status status : this.statuses) {
            Predicate currentPredicate = criteriaBuilder.equal(root.get("status"), status);
            if(statusPredicate == null) {
                statusPredicate = currentPredicate;
            } else {
                statusPredicate = criteriaBuilder.or(statusPredicate, currentPredicate);
            }
        }
        if (dateFrom != null && dateTo != null) {
            predicates.add(criteriaBuilder.between(root.get("created"), this.dateFrom, this.dateTo));
        }
        return predicates.stream()
                .reduce(predicates.get(0),
                        criteriaBuilder::and);
    }
}
