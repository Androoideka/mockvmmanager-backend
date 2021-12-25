package rs.raf.agasic218rn.domaci3beagasic218rn.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.raf.agasic218rn.domaci3beagasic218rn.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
