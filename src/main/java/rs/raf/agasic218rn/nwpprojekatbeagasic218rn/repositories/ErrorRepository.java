package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.ErrorLog;

@Repository
public interface ErrorRepository extends JpaRepository<ErrorLog, Long>, JpaSpecificationExecutor<ErrorLog> {
}
