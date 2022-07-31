package org.selenium.pageobject.api.actions;

import org.selenium.pageobject.objects.User;
import org.selenium.pageobject.utils.FakerUtils;


public class DummyClass {
    public static void main(String[] args) {
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User().
                setUsername(username)
                .setEmail(username + "@askomdch.com")
                .setPassword("demouserpwd");
        SignUpApi signUpAPI = new SignUpApi();
        signUpAPI.register(user);
        System.out.println("Regıster Cookies" + signUpAPI.getCookies());

//        -----without login-----
//        CartApi cartApi= new CartApi();
//        cartApi.addToCart(1198,1);
//        System.out.println(cartApi.getCookies());


//         -----with register-----
        CartApi cartApi = new CartApi(signUpAPI.getCookies());
        cartApi.addToCart(1215, 1);
        System.out.println(cartApi.getCookies());


    }
}
