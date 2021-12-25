package rs.raf.agasic218rn.domaci3beagasic218rn.responses;

public class LoginResponse {
    private String token;
    private String identifier;

    public LoginResponse() { }

    public LoginResponse(String token, String identifier) {
        this.token = token;
        this.identifier = identifier;
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
}
