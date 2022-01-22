package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
