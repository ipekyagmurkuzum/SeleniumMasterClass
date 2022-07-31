package org.selenium.pageobject.api.actions;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.selenium.pageobject.objects.User;
import org.selenium.pageobject.utils.ConfigLoader;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.head;

public class SignUpApi {
    private Cookies cookies;

    public Cookies getCookies() {
        return cookies;
    }

    //groovy
//    private String fetchRegisterNonceValue() {
//        Response response = getAccount();
//        return response.htmlPath().getString("**.findAll { it.@name == 'woocommerce-register-nonce' }.@value");
//    }

    //jsoup
    private String fetchRegisterNonceValue() {
        Response response = getAccount();
        Document document = Jsoup.parse(response.body().prettyPrint());
        Element element = document.selectFirst("#woocommerce-register-nonce");
        return element.attr("value");
    }


    private Response getAccount() {
        Cookies cookies = new Cookies();
        Response response = given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                cookies(cookies).
                log().all().
        when().
                get("/account").
        then().
                log().all().
                extract().
                response();
        if(response.getStatusCode()!=200) {
            throw new RuntimeException("Failed to fetch the account, HTTP Status Code: " + response.getStatusCode());
        }
        return response;
    }

    public Response register(User user) {
        Cookies cookies = new Cookies();
        Header header = new Header("content-type","application/x-www-form-urlencoded");
        Headers headers = new Headers(header);
        HashMap<String,String> formParams = new HashMap<String,String>();
        formParams.put("username", user.getUsername());
        formParams.put("email", user.getEmail());
        formParams.put("password", user.getPassword());
        formParams.put("woocommerce-register-nonce", fetchRegisterNonceValue());
        formParams.put("register", "Register");

        Response response = given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                headers(headers).
                formParams(formParams).
                cookies(cookies).
                log().all().
        when().
                post("/account").
        then().
                log().all().
                extract().
                response();
        if(response.getStatusCode()!=302) {
            throw new RuntimeException("Failed to register the account, HTTP Status Code: " + response.getStatusCode());
        }
        this.cookies= response.getDetailedCookies();
        return response;
    }
}
