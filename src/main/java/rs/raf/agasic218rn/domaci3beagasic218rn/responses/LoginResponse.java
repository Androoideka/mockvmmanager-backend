package rs.raf.agasic218rn.domaci3beagasic218rn.responses;

public class LoginResponse {
    private String token;
    private String identifier;
    private PermissionListResponse permissionListResponse;

    public LoginResponse() { }

    public LoginResponse(String token, String identifier, PermissionListResponse permissionListResponse) {
        this.token = token;
        this.identifier = identifier;
        this.permissionListResponse = permissionListResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public PermissionListResponse getPermissionListResponse() {
        return permissionListResponse;
    }

    public void setPermissionListResponse(PermissionListResponse permissionListResponse) {
        this.permissionListResponse = permissionListResponse;
    }
}
