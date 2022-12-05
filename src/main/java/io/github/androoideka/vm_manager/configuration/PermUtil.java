package io.github.androoideka.vm_manager.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class PermUtil {
    public static final List<SimpleGrantedAuthority> AUTHORITIES;
    static {
        String[] tempStringArray = new String[] { "can_create_users",
                "can_read_users",
                "can_update_users",
                "can_delete_users",
                "can_search_machines",
                "can_start_machines",
                "can_stop_machines",
                "can_restart_machines",
                "can_create_machines",
                "can_destroy_machines" };
        AUTHORITIES = Arrays.asList(tempStringArray)
                .stream()
                .map(stringRep -> new SimpleGrantedAuthority(stringRep))
                .toList();
    }
    public static final char[] SHORT_VALUES = new char[] { 'c', 'r', 'u', 'd' };
}
