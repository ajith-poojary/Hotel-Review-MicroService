package com.ajith.userService.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;


@Configuration
@Component
public class FiegnClientInterceptor implements RequestInterceptor {

    private Logger logger= LoggerFactory.getLogger(FiegnClientInterceptor.class);



    // we need to get hold of token , that we get it from OAuth2AuthorizedClientManager

    @Autowired
    private OAuth2AuthorizedClientManager manager;  // need to create bean of oauth2 , after that we can autowire it from here, creating the bean in Myconfig class

    @Override
    public void apply(RequestTemplate template) {

        OAuth2AuthorizedClient authorize = manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build());
        String tokenValue = authorize.getAccessToken().getTokenValue();

        logger.info("Feign token : ", tokenValue);

        // now we got the token
        // set the token in template
        template.header("Authorization","Bearer "+tokenValue);

    }
}
