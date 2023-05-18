package com.ajith.userService.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Configuration
@Component
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

  private  Logger logger= LoggerFactory.getLogger(RestTemplateInterceptor.class);



    private OAuth2AuthorizedClientManager manager;

    public RestTemplateInterceptor(OAuth2AuthorizedClientManager manager) {
        this.manager = manager;
    }


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        OAuth2AuthorizedClient authorize = manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build());
        String tokenValue = authorize.getAccessToken().getTokenValue();

        logger.info("Authorize from restTemplate,{}",authorize);
        logger.info("Token from restTemplate,{}",tokenValue);

        request.getHeaders().add("Authorization","Bearer "+tokenValue);
        return execution.execute(request, body);
    }
}
