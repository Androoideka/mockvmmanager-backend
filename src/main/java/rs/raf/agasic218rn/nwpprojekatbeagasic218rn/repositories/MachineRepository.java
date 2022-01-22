package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.Machine;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long>, JpaSpecificationExecutor<Machine> {
    @Modifying
    @Query("update Machine m set m.opCounter = m.opCounter + 2 where m.machineId = :machineId and m.version = m.opCounter")
    @Transactional
    Integer incrementOpCounter(Long machineId);
}
