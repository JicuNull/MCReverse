package icu.jnet.mcd.api;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import icu.jnet.mcd.api.request.RefreshRequest;
import icu.jnet.mcd.api.request.Request;
import icu.jnet.mcd.model.Authorization;
import icu.jnet.mcd.api.response.Response;
import icu.jnet.mcd.api.response.LoginResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import static icu.jnet.mcd.network.RequestManager.*;

class McBase {

    private final HttpRequestFactory factory;
    private final Gson gson = new Gson();
    private final Random rand = new Random();
    final Authorization auth = new Authorization();
    private String aToken = "";

    String email;

    public McBase(String aToken) {
        this.aToken = aToken;
        this.factory = new NetHttpTransport().createRequestFactory();
    }

    boolean success(Response response) {
        return response.getStatus().getType().equals("Success");
    }

    <T extends Response> T queryGet(Request request, Class<T> clazz)  {
        try {
            String url = request.getUrl();
            HttpRequest httpRequest = factory.buildGetRequest(new GenericUrl(url));
            return query(httpRequest, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz);
    }

    <T extends Response> T queryPost(Request request, Class<T> clazz) {
        try {
            String url = request.getUrl();
            HttpContent httpContent = request.getContent();
            HttpRequest httpRequest = factory.buildPostRequest(new GenericUrl(url), httpContent);
            return query(httpRequest, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz);
    }

    <T extends Response> T queryDelete(Request request, Class<T> clazz) {
        try {
            String url = request.getUrl();
            HttpRequest httpRequest = factory.buildDeleteRequest(new GenericUrl(url));
            return query(httpRequest, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz);
    }

    <T extends Response> T queryPut(Request request, Class<T> clazz) {
        try {
            String url = request.getUrl();
            HttpContent httpContent = request.getContent();
            HttpRequest httpRequest = factory.buildPutRequest(new GenericUrl(url), httpContent);
            return query(httpRequest, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz);
    }

    private <T extends Response> T query(HttpRequest request, Class<T> clazz) {
        try {
            setRequestHeaders(request);
            //addRequest(request);
            T t = gson.fromJson(request.execute().parseAsString(), clazz);
            //removeLast();
            return t;
        } catch (HttpResponseException e) {
            try {
                Response response = gson.fromJson(e.getContent(), Response.class);
                if(response != null && response.getStatus().getErrors().stream()
                        .anyMatch(error -> error.getErrorType().equals("JWTTokenExpired"))) { // Authorization expired
                    if(loginRefresh()) {
                        return query(request, clazz);
                    }
                }
            } catch (JsonSyntaxException e2) {
                System.out.println(e.getContent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz);
    }

    private boolean loginRefresh() {
        LoginResponse login = queryPost(new RefreshRequest(auth.getRefreshToken()), LoginResponse.class);
        if(success(login)) {
            auth.updateAccessToken(login.getAccessToken());
            auth.updateRefreshToken(login.getRefreshToken());
            return true;
        }
        return false;
    }

    private void setRequestHeaders(HttpRequest request) {
        String token = !auth.getAccessToken().isEmpty()
                ? auth.getAccessToken()
                : "Basic NkRFVXlKT0thQm96OFFSRm00OXFxVklWUGowR1V6b0g6NWltaDZOS1UzdjVDVWlmVHZIUTdFeEY4ZXhrbWFOamI=";

        HttpHeaders headers = new HttpHeaders();
        headers.set("mcd-clientid", "6DEUyJOKaBoz8QRFm49qqVIVPj0GUzoH");
        headers.set("authorization", token);
        headers.set("accept-charset", "UTF-8");
        headers.set("content-type", request.getContent() != null ? request.getContent().getType() : "application/json;");
        headers.set("accept-language", "de-DE");
        headers.set("user-agent", "MCDSDK/19.0.60 (Android; 30; de-DE) GMA/7.7");
        headers.set("mcd-sourceapp", "GMA");
        headers.set("mcd-uuid", "ab65a26f-b02c-416d-a5e5-a32df5ba762d"); // Can not be fully random?
        if(request.getUrl().toString().endsWith("login") || request.getUrl().toString().endsWith("profile")) {
            headers.set("mcd-marketid", "DE");
            headers.set("x-acf-sensor-data", aToken);
        }
        request.setHeaders(headers);
    }

    private <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
