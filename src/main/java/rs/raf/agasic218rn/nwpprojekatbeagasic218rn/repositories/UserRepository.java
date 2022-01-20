package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
