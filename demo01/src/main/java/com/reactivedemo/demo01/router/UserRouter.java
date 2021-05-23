package com.reactivedemo.demo01.router;

import com.mongodb.internal.connection.Server;
import com.reactivedemo.demo01.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {
    @Bean
    RouterFunction<ServerResponse> routerFunction(UserHandler handler){
        return RouterFunctions.nest(
                RequestPredicates.path("/router/user"),
                RouterFunctions.route(RequestPredicates.GET(""), handler::getAllUser)
                .andRoute(RequestPredicates.GET("/test"), serverRequest -> {
                    return ServerResponse.ok().bodyValue("this is name");
                })
        );
    };

}
