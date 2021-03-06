package icu.jnet.mcd.api.request;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpContent;
import com.google.gson.Gson;

public interface Request {

    String getUrl();

    default HttpContent getContent() {
        return ByteArrayContent.fromString("application/json", new Gson().toJson(this));
    }
}
