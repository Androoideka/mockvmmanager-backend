package rs.raf.agasic218rn.domaci3beagasic218rn.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import rs.raf.agasic218rn.domaci3beagasic218rn.requests.UserRequest;

public interface UserService extends UserDetailsService {
    boolean createUser(UserRequest userRequest);
}
