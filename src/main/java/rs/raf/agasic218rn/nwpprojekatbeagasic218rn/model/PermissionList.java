package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model;

import javax.persistence.Embeddable;

@Embeddable
public class PermissionList {
    private boolean canReadUsers;
    private boolean canCreateUsers;
    private boolean canUpdateUsers;
    private boolean canDeleteUsers;

    public PermissionList() {
    }

    public boolean isCanReadUsers() {
        return canReadUsers;
    }

    public void setCanReadUsers(boolean canReadUsers) {
        this.canReadUsers = canReadUsers;
    }

    public boolean isCanCreateUsers() {
        return canCreateUsers;
    }

    public void setCanCreateUsers(boolean canCreateUsers) {
        this.canCreateUsers = canCreateUsers;
    }

    public boolean isCanUpdateUsers() {
        return canUpdateUsers;
    }

    public void setCanUpdateUsers(boolean canUpdateUsers) {
        this.canUpdateUsers = canUpdateUsers;
    }

    public boolean isCanDeleteUsers() {
        return canDeleteUsers;
    }

    public void setCanDeleteUsers(boolean canDeleteUsers) {
        this.canDeleteUsers = canDeleteUsers;
    }
}
