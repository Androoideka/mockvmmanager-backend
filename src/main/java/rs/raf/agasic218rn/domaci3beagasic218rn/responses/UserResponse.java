package rs.raf.agasic218rn.domaci3beagasic218rn.responses;

public class UserResponse {
    private String email;
    private String name;
    private String surname;
    private PermissionListResponse permissionListResponse;

    public UserResponse() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public PermissionListResponse getPermissionListResponse() {
        return permissionListResponse;
    }

    public void setPermissionListResponse(PermissionListResponse permissionListResponse) {
        this.permissionListResponse = permissionListResponse;
    }
}
