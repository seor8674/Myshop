package Myshop.shop.config.oauth.provider;

import java.util.Map;

public class FacebookUserinfo implements OAuth2Userinfo{

    private Map<String,Object> attribute;

    public FacebookUserinfo(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getproviderid() {
        return (String)attribute.get("id");
    }

    @Override
    public String getprovider() {
        return "facebook";
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
