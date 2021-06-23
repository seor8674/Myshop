package Myshop.shop.config.oauth.provider;

import java.util.Map;

public class GoogleUserinfo implements OAuth2Userinfo{
    private Map<String,Object> attribute;

    public GoogleUserinfo(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getproviderid() {
        return (String)attribute.get("sub");
    }

    @Override
    public String getprovider() {
        return "google";
    }

    @Override
    public String getemail() {
        return (String)attribute.get("email");
    }

    @Override
    public String getName() {
        return (String)attribute.get("name");
    }
}
