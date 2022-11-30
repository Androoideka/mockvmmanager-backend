package io.github.androoideka.vm_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.github.androoideka.vm_manager.model.Machine;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long>, JpaSpecificationExecutor<Machine> {
    @Modifying
    @Query("update Machine m set m.opCounter = m.opCounter + 1 where m.machineId = :machineId and m.version = m.opCounter")
    @Transactional
    Integer incrementOpCounter(@Param("machineId") Long machineId);
}
