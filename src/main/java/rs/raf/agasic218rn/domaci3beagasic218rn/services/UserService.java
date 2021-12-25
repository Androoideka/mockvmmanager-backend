package rs.raf.agasic218rn.domaci3beagasic218rn.services;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import rs.raf.agasic218rn.domaci3beagasic218rn.requests.UserRequest;
import rs.raf.agasic218rn.domaci3beagasic218rn.responses.UserResponse;

public interface UserService extends UserDetailsService {
    void create(UserRequest userRequest);

    Page<UserResponse> read(Integer page, Integer size);
}
