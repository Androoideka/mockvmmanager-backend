package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.responses;

public class LoginResponse {
    private String token;
    private UserResponse userResponse;

    public LoginResponse() { }

    public LoginResponse(String token, UserResponse userResponse) {
        this.token = token;
        this.userResponse = userResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }
}
