package com.samkelsey.sortersocket.config;

import com.samkelsey.sortersocket.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Channels to subscribe to
        config.enableSimpleBroker(Constants.SORTING, Constants.ERRORS);

        // Controller endpoint prefixes
        config.setApplicationDestinationPrefixes(Constants.PREFIX);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint for handshake
        registry.addEndpoint(Constants.REGISTRY);
        registry.addEndpoint(Constants.REGISTRY).setAllowedOrigins("http://localhost:3000").withSockJS();
    }

}
