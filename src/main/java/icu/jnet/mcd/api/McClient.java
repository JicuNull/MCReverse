package icu.jnet.mcd.api;

import icu.jnet.mcd.api.request.*;
import icu.jnet.mcd.api.response.*;

import java.util.Arrays;
import java.util.Objects;

public class McClient extends McBase {

    public static final String DEFAULT_DEVICE_ID = "75408e58622a88c6";
    private String deviceId = DEFAULT_DEVICE_ID, userId;

    public boolean login(String email, String password) {
        return login(email, password, DEFAULT_DEVICE_ID);
    }

    public boolean login(String email, String password, String deviceId) {
        if(success(getAccessToken())) {
            LoginResponse login = queryPost(new LoginRequest(email, password, deviceId), LoginResponse.class);
            auth.updateAccessToken(login.getAccessToken());
            auth.updateRefreshToken(login.getRefreshToken());
            if(success(login)) {
                ProfileResponse profileResponse = getProfile();
                if(success(profileResponse)) {
                    this.email = email;
                    this.deviceId = deviceId;
                    this.userId = profileResponse.getInfo().getHashedDcsId();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean register(String email, String password, String zipCode) {
        return register(email, password, zipCode, DEFAULT_DEVICE_ID);
    }

    public boolean register(String email, String password, String zipCode, String deviceId) {
        return success(getAccessToken()) && success(queryPost(new RegisterRequest(email, password, zipCode, deviceId), LoginResponse.class));
    }

    public boolean activateAccount(String email, String activationCode) {
        return activate(email, activationCode, deviceId, "email");
    }

    public boolean activateAccount(String email, String activationCode, String deviceId) {
        return activate(email, activationCode, deviceId, "email");
    }

    public boolean activateDevice(String email, String activationCode, String deviceId) {
        return activate(email, activationCode, deviceId, "device");
    }

    private boolean activate(String email, String activationCode, String deviceId, String type) {
        return success(getAccessToken()) && success(queryPut(new ActivationRequest(email, activationCode, deviceId, type), Response.class));
    }

    public Response deleteAccount() {
        return queryDelete(new DeleteRequest(), Response.class);
    }

    public ProfileResponse getProfile() {
        return queryGet(new ProfileRequest(), ProfileResponse.class);
    }

    public RestaurantResponse getRestaurants(double latitude, double longitude, int distance, int amount) {
        return queryGet(new RestaurantRequest(latitude, longitude, distance, amount), RestaurantResponse.class);
    }

    public PointsResponse getPoints() {
        return queryGet(new PointsRequest(), PointsResponse.class);
    }

    public OfferResponse getOffers() {
        return queryGet(new OffersRequest(), OfferResponse.class);
    }

    public OfferDetailsResponse getOfferDetails(int propositionId) {
        return queryGet(new OfferDetailsRequest(propositionId), OfferDetailsResponse.class);
    }

    public RedeemResponse redeemCoupon(int propositionId) {
        return redeemCoupon(propositionId, 0);
    }

    public RedeemResponse redeemCoupon(int propositionId, long offerId) {
        return queryGet(new RedeemRequest(propositionId, offerId), RedeemResponse.class);
    }

    public RedeemResponse getIdentificationCode() {
        return queryGet(new IdentRequest(), RedeemResponse.class);
    }

    public BonusPointsResponse getPointsBonuses() {
        return queryGet(new BonusPointsRequest(), BonusPointsResponse.class);
    }

    public OptInResponse optInCampaign(int campaignId) {
        return queryPost(new OptInRequest(campaignId), OptInResponse.class);
    }

    public Response useMyMcDonalds(boolean b) {
        return queryPut(new ProfileRequest().useMyMcDonalds(b, deviceId), Response.class);
    }

    public Response setZipCode(String zipCode) {
        return queryPut(new ProfileRequest().setZipCode(zipCode), Response.class);
    }

    public boolean usesMyMcDonalds() {
        return queryGet(new ProfileRequest(), ProfileResponse.class).getInfo().getSubscriptions().stream()
                .filter(sub -> sub.getOptInStatus().equals("Y")
                        && Arrays.asList("23", "24", "25").contains(sub.getSubscriptionId())
                        || sub.getOptInStatus().equals("N")
                        && sub.getSubscriptionId().equals("21")).count() == 4;
    }

    private AuthResponse getAccessToken() {
        AuthResponse authResponse = queryPost(new AccessRequest(), AuthResponse.class);
        auth.updateAccessToken(authResponse.getToken());
        return authResponse;
    }

    public Response setLocation() {
        return queryPost(new LocationRequest(email), Response.class);
    }

    public Response setNotification() {
        return queryPost(new NotificationRequest(), Response.class);
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof McClient && ((McClient) obj).getEmail().equals(getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
