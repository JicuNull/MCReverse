package icu.jnet.mcd.api.request;

public class LoginRequest implements Request {

    private final Credentials credentials;
    private final String deviceId;

    public LoginRequest(String email, String password, String deviceId) {
        this.credentials = new Credentials(email, password);
        this.deviceId = deviceId;
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/login";
    }

    public static class Credentials {
        private final String loginUsername, password, type = "email";

        public Credentials(String email, String password) {
            this.loginUsername = email;
            this.password = password;
        }
    }
}
