package rs.raf.agasic218rn.domaci3beagasic218rn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.raf.agasic218rn.domaci3beagasic218rn.configuration.JwtUtil;
import rs.raf.agasic218rn.domaci3beagasic218rn.responses.PermissionListResponse;
import rs.raf.agasic218rn.domaci3beagasic218rn.requests.LoginRequest;
import rs.raf.agasic218rn.domaci3beagasic218rn.requests.UserRequest;
import rs.raf.agasic218rn.domaci3beagasic218rn.responses.LoginResponse;
import rs.raf.agasic218rn.domaci3beagasic218rn.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
        PermissionListResponse permissionListResponse = new PermissionListResponse(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        return ResponseEntity.ok(new LoginResponse(
                jwtUtil.generateToken(loginRequest.getEmail(), permissionListResponse.generatePermissionMap()),
                loginRequest.getEmail()));
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
        this.userService.create(userRequest);
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(this.userService.read(page, size));
    }
}
