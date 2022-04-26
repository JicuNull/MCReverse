package icu.jnet.mcd.model;

public class Authorization {

    private String accessToken, refreshToken;

    public void updateAccessToken(String accessToken) {
        if(accessToken == null) {
            return;
        }
        this.accessToken = "Bearer " + accessToken;
    }

    public void updateRefreshToken(String refreshToken) {
        if(refreshToken == null) {
            return;
        }
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken != null ? accessToken : "";
    }

    public String getRefreshToken() {
        return refreshToken != null ? refreshToken : "";
    }

    public String getBareToken() {
        return getAccessToken().replace("Bearer ", "");
    }
}