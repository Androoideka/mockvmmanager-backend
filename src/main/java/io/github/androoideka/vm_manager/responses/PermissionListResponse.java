package io.github.androoideka.vm_manager.responses;

import org.springframework.security.core.GrantedAuthority;

import io.github.androoideka.vm_manager.configuration.PermissionUtil;

import java.util.*;
import java.util.stream.Collectors;

public class PermissionListResponse {
    private boolean[] permissionValues = new boolean[PermissionUtil.REPRESENTATIONS.length];

    public PermissionListResponse() {

    }

    public PermissionListResponse(Collection<String> permissions) {
        permissions.stream().map(persimmon -> Arrays.asList(PermissionUtil.REPRESENTATIONS).indexOf(persimmon))
                .collect(Collectors.toSet()).forEach(index -> permissionValues[index] = true);
    }

    // Needed for jackson
    public boolean[] getPermissionValues() {
        return permissionValues;
    }

    public void setPermissionValues(boolean[] permissionValues) {
        this.permissionValues = permissionValues;
    }

    public Map<String, Boolean> generatePermissionMap() {
        Map<String, Boolean> permissionMap = new HashMap<>();
        for (int i = 0; i < permissionValues.length; i++) {
            permissionMap.put(PermissionUtil.REPRESENTATIONS[i], permissionValues[i]);
        }
        return permissionMap;
    }

    public Set<String> toPermissionList() {
        Set<String> permissions = new HashSet<>();
        for (int i = 0; i < permissionValues.length; i++) {
            if (permissionValues[i]) {
                permissions.add(PermissionUtil.REPRESENTATIONS[i]);
            }
        }
        return permissions;
    }

    public Set<GrantedAuthority> toSpringAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (int i = 0; i < permissionValues.length; i++) {
            if (permissionValues[i]) {
                authorities.add(PermissionUtil.AUTHORITIES[i]);
            }
        }
        return authorities;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        for (boolean perm : permissionValues) {
            int permHash = perm ? 1 : 0;
            result = prime * result + permHash;
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PermissionListResponse perms = (PermissionListResponse) obj;
        if (permissionValues == null || perms.permissionValues == null
                || permissionValues.length != perms.permissionValues.length) {
            return false;
        }
        for (int i = 0; i < permissionValues.length; i++) {
            if (permissionValues[i] != perms.permissionValues[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        for (int i = 0; i < permissionValues.length; i++) {
            if (permissionValues[i]) {
                display.append(PermissionUtil.SHORT_VALUES[i]);
            } else {
                display.append('-');
            }
        }
        return display.toString();
    }
}
