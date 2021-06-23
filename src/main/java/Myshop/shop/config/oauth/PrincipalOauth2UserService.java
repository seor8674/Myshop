package Myshop.shop.config.oauth;

import Myshop.shop.config.auth.PrincipalDetails;
import Myshop.shop.config.oauth.provider.FacebookUserinfo;
import Myshop.shop.config.oauth.provider.GoogleUserinfo;
import Myshop.shop.config.oauth.provider.OAuth2Userinfo;
import Myshop.shop.entity.User;
import Myshop.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2Userinfo oAuth2Userinfo=null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){

            oAuth2Userinfo = new GoogleUserinfo(oAuth2User.getAttributes());

        }else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            oAuth2Userinfo = new FacebookUserinfo(oAuth2User.getAttributes());

        }
        String provider = oAuth2Userinfo.getprovider();
        String providerid = oAuth2Userinfo.getproviderid();
        String email = oAuth2Userinfo.getemail();
        String userid = provider + "_" + providerid;
        String username = oAuth2Userinfo.getName();
        String password = bCryptPasswordEncoder.encode("이환준");//의미없음
        String Role = "ROLE_MEMBER";
        User user;
        User byUserid = userRepository.findByUserid(userid);
        if(byUserid==null){
            user=new User(username,userid,password,email,Role,provider,providerid);
            userRepository.save(user);
        }else{
            user=byUserid;
        }


        return new PrincipalDetails(user,oAuth2User.getAttributes());
    }
}
