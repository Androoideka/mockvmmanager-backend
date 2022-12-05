package io.github.androoideka.vm_manager.responses;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.github.androoideka.vm_manager.configuration.PermUtil;

import java.util.*;

public class PermissionListResponse {
    private boolean[] permissionValues = new boolean[PermUtil.AUTHORITIES.size()];

    public PermissionListResponse() {

    }

    // Needed for jackson
    public boolean[] getPermissionValues() {
        return permissionValues;
    }

    public void setPermissionValues(boolean[] permissionValues) {
        this.permissionValues = permissionValues;
    }

    public static PermissionListResponse fromSpringAuthorities(Set<SimpleGrantedAuthority> authorities) {
        PermissionListResponse list = new PermissionListResponse();
        list.permissionValues = new boolean[PermUtil.AUTHORITIES.size()];
        for (int i = 0; i < PermUtil.AUTHORITIES.size(); i++) {
            list.permissionValues[i] = authorities.contains(PermUtil.AUTHORITIES.get(i));
        }
        return list;
    }

    public Set<SimpleGrantedAuthority> toSpringAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (int i = 0; i < permissionValues.length; i++) {
            if (permissionValues[i]) {
                authorities.add(PermUtil.AUTHORITIES.get(i));
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
                display.append(PermUtil.SHORT_VALUES[i]);
            } else {
                display.append('-');
            }
        }
        return display.toString();
    }
}
