package rs.raf.agasic218rn.domaci3beagasic218rn.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import rs.raf.agasic218rn.domaci3beagasic218rn.configuration.PermissionUtil;

import java.util.*;

public class PermissionListDTO {
    private boolean[] permissionValues = new boolean[PermissionUtil.REPRESENTATIONS.length];

    public PermissionListDTO() {

    }

    public PermissionListDTO(PermissionList permissionList) {
        permissionValues[0] = permissionList.isCanCreateUsers();
        permissionValues[1] = permissionList.isCanReadUsers();
        permissionValues[2] = permissionList.isCanUpdateUsers();
        permissionValues[3] = permissionList.isCanDeleteUsers();
    }

    public PermissionListDTO(Collection<? extends GrantedAuthority> authorities) {
        for(int i = 0; i < PermissionUtil.AUTHORITIES.length; i++) {
            if(authorities.contains(PermissionUtil.AUTHORITIES[i])) {
                permissionValues[i] = true;
            }
        }
    }

    public boolean[] getPermissionValues() {
        return permissionValues;
    }

    public void setPermissionValues(boolean[] permissionValues) {
        this.permissionValues = permissionValues;
    }

    public boolean canReadUsers() {
        return permissionValues[1];
    }

    public boolean canCreateUsers() {
        return permissionValues[0];
    }

    public boolean canUpdateUsers() {
        return permissionValues[2];
    }

    public boolean canDeleteUsers() {
        return permissionValues[3];
    }

    public void setCanReadUsers(boolean canReadUsers) { permissionValues[1] = canReadUsers; }

    public void setCanCreateUsers(boolean canCreateUsers) { permissionValues[0] = canCreateUsers; }

    public void setCanUpdateUsers(boolean canUpdateUsers) { permissionValues[2] = canUpdateUsers; }

    public void setCanDeleteUsers(boolean canDeleteUsers) { permissionValues[3] = canDeleteUsers; }

    public Map<String, Boolean> getPermissionMap() {
        Map<String, Boolean> permissionMap = new HashMap<>();
        for(int i = 0; i < permissionValues.length; i++) {
            permissionMap.put(PermissionUtil.REPRESENTATIONS[i], permissionValues[i]);
            /*if(permissionValues[i]) {
                permissionMap.put(permissionRepresentations[i], permissionValues[i]);
            }*/
        }
        return permissionMap;
    }

    public List<GrantedAuthority> toSpringAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(int i = 0; i < permissionValues.length; i++) {
            if(permissionValues[i]) {
                authorities.add(PermissionUtil.AUTHORITIES[i]);
            }
        }
        return authorities;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        for(boolean perm : permissionValues) {
            int permHash = perm ? 1 : 0;
            result = prime * result + permHash;
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        PermissionListDTO perms = (PermissionListDTO) obj;
        if(permissionValues == null || perms.permissionValues == null || permissionValues.length != perms.permissionValues.length) {
            return false;
        }
        for(int i = 0; i < permissionValues.length; i++) {
            if(permissionValues[i] != perms.permissionValues[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        for(int i = 0; i < permissionValues.length; i++) {
            if(permissionValues[i]) {
                display.append(PermissionUtil.SHORT_VALUES[i]);
            } else {
                display.append('-');
            }
        }
        return display.toString();
    }
}
