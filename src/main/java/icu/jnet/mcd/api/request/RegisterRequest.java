package icu.jnet.mcd.api.request;

import com.google.api.client.http.HttpContent;

import java.util.HashMap;

public class RegisterRequest implements Request {

    private Policies policies = new Policies();
    private Audit audit = new Audit();
    private Device device;
    private Address address;
    private Credentials credentials;
    private final String emailAddress, firstName = "M", lastName = "M";
    private final boolean optInForMarketing = false;

    public RegisterRequest(String email, String password, String zipCode, String country, String deviceId) {
        this.emailAddress = email;
        address = new Address(zipCode, country);
        credentials = new Credentials(email, password);
        device = new Device(deviceId);
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/registration";
    }

    private static class Address {
        private final String zipCode, country;

        private Address(String zipCode, String country) {
            this.zipCode = zipCode;
            this.country = country;
        }
    }

    private static class Audit {
        private final String registrationChannel = "M";
    }

    private static class Credentials {
        private final String loginUsername, password, type = "email";

        private Credentials(String email, String password) {
            this.loginUsername = email;
            this.password = password;
        }
    }

    private static class Device {
        private final String deviceIdType = "AndroidId", isActive = "Y", os = "android",
            osVersion = "9", timezone = "Europe/Berlin";
        private String deviceId;

        public Device(String deviceId) {
            this.deviceId = deviceId;
        }
    }

    private static class Policies {
        private HashMap<String, Boolean> acceptancePolicies = new HashMap<>();

        public Policies() {
            acceptancePolicies.put("1", true);
            acceptancePolicies.put("4", true);
        }
    }
}
