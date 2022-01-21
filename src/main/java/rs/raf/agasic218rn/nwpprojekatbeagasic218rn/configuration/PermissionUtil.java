package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.configuration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class PermissionUtil {
    public static final String[] REPRESENTATIONS = new String[] {"can_create_users",
            "can_read_users",
            "can_update_users",
            "can_delete_users",
            "can_search_machines",
            "can_start_machines",
            "can_stop_machines",
            "can_restart_machines",
            "can_create_machines",
            "can_destroy_machines"};
    public static final GrantedAuthority[] AUTHORITIES = new GrantedAuthority[REPRESENTATIONS.length];
    static {
        for(int i = 0; i < REPRESENTATIONS.length; i++) {
            AUTHORITIES[i] = new SimpleGrantedAuthority(REPRESENTATIONS[i]);
        }
    }
    public static final char[] SHORT_VALUES = new char[] {'c', 'r', 'u', 'd'};
}
