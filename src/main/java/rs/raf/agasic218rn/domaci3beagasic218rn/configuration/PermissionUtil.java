package rs.raf.agasic218rn.domaci3beagasic218rn.configuration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class PermissionUtil {
    public static final String[] REPRESENTATIONS = new String[] {"can_create_users", "can_read_users", "can_update_users", "can_delete_users"};
    public static final GrantedAuthority[] AUTHORITIES = new GrantedAuthority[]
            { new SimpleGrantedAuthority(REPRESENTATIONS[0]), new SimpleGrantedAuthority(REPRESENTATIONS[1]),
                    new SimpleGrantedAuthority(REPRESENTATIONS[2]), new SimpleGrantedAuthority(REPRESENTATIONS[3]) };
    public static final char[] SHORT_VALUES = new char[] {'c', 'r', 'u', 'd'};
    public static final String CREATE_USERS = REPRESENTATIONS[0];
    public static final String READ_USERS = REPRESENTATIONS[1];
    public static final String UPDATE_USERS = REPRESENTATIONS[2];
    public static final String DELETE_USERS = REPRESENTATIONS[3];
}
