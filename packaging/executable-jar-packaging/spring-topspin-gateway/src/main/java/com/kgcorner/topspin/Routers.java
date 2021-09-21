package com.kgcorner.topspin;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
import java.util.function.Function;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 15/08/21
 */

@Component
public class Routers {

    private static final String X_APPLICATION_NAME = "X-Application-name";
    private static final String X_APPLICATION_HASH = "X-Application-Hash";
    private static final String X_REQUESTED_AT = "X-Requested-At";
    private static final String APPLICATION_NAME = "gateway";
    @Value("${store.service.host:http://localhost:9003}")
    private String storeServiceHost;

    @Value("${product.service.host:http://localhost:9004}")
    private String productOfferServiceHost;

    @Value("${user.service.host:http://localhost:9002}")
    private String userServiceHost;

    @Value("${auth.service.host:http://localhost:9001}")
    private String authServiceHost;

    @Bean
    public RouteLocator createRouteLocator(RouteLocatorBuilder builder) {
        String requestedAT = new Date().getTime()+"";
       return builder.routes()
           //Store's routes
           .route("create-store", p-> p.path("/manage/stores")
                    .filters(getGateway(requestedAT)).uri(storeServiceHost))
           .route("update-and-delete-store", p-> p.path("/manage/stores/**")
               .filters(getGateway(requestedAT)).uri(storeServiceHost))
           .route("get-stores", p-> p.path("/stores/**")
               .filters(getGateway(requestedAT))
               .uri(storeServiceHost))
            .route("get-all-stores", p-> p.path("/stores")
                    .filters(getGateway(requestedAT)
                    ).uri(storeServiceHost))

           //Category's routes
           .route("create-category", p-> p.path("/manage/categories")
               .filters(getGateway(requestedAT)).uri(storeServiceHost))
           .route("update-and-delete-category", p-> p.path("/manage/categories/**")
               .filters(getGateway(requestedAT)).uri(storeServiceHost))
           .route("get-category", p-> p.path("/categories/**")
               .filters(getGateway(requestedAT))
               .uri(storeServiceHost))
           .route("get-all-category", p-> p.path("/categories")
               .filters(getGateway(requestedAT)
               ).uri(storeServiceHost))

           //users's routes
           .route("create-user", p-> p.path("/manage/users")
               .filters(getGatewayWithPath(requestedAT, "/users")).uri(userServiceHost))
           .route("update-and-delete-user", p-> p.path("/manage/users/**")
               .filters(getGatewayWithRewrite(requestedAT, "/manage/users/(?<userId>.*)",
                   "/users/${userId}")).uri(userServiceHost))
           .route("get-user", p-> p.path("/users/**")
               .filters(getGateway(requestedAT))
               .uri(userServiceHost))
           .route("get-all-user", p-> p.path("/users")
               .filters(getGateway(requestedAT)
               ).uri(userServiceHost))

           //offer's routes
           .route("create-offer", p-> p.path("/manage/offers")
               .filters(getGateway(requestedAT)).uri(productOfferServiceHost))
           .route("update-and-delete-offer", p-> p.path("/manage/offers/**")
               .filters(getGateway(requestedAT)).uri(productOfferServiceHost))
           .route("get-offer", p-> p.path("/offers/**")
               .filters(getGateway(requestedAT))
               .uri(productOfferServiceHost))
           .route("get-all-offer", p-> p.path("/offers")
               .filters(getGateway(requestedAT)
               ).uri(productOfferServiceHost))

           //product's routes
           .route("create-products", p-> p.path("/manage/products")
               .filters(getGatewayWithPath(requestedAT, "/offers")).uri(productOfferServiceHost))
           .route("update-and-delete-products", p-> p.path("/manage/products/**")
               .filters(getGatewayWithRewrite(requestedAT, "/manage/products/(?<productId>.*)",
                   "/products/${productId}")).uri(productOfferServiceHost))
           .route("get-products", p-> p.path("/products/**")
               .filters(getGateway(requestedAT))
               .uri(productOfferServiceHost))
           .route("get-all-products", p-> p.path("/products")
               .filters(getGateway(requestedAT)
               ).uri(productOfferServiceHost))
            //User's routes
           .route("create-user", p-> p.path("/users")
               .filters(getGateway(requestedAT)).uri(userServiceHost))
           .route("delete-update-user", p-> p.path("/user/users/**")
                    .filters(getGatewayWithRewrite(requestedAT, "/user/users/<?<userId>.*)",
                        "users/${userId}")).uri(userServiceHost)
                    )
           //Transaction's route
           .route("create-cashback", p-> p.path("/manage/cashbacks")
               .filters(getGatewayWithRewrite(requestedAT, "/manage/cashbacks)",
                   "/cashbacks")).uri(userServiceHost))
           .route("delete-update-cashback", p-> p.path("/manage/cashbacks/**")
               .filters(getGatewayWithRewrite(requestedAT, "/manage/cashbacks/<?<cashbackId>.*)",
                   "cashbacks/${cashbackId}")).uri(userServiceHost))
           .route("create-redeem", p-> p.path("/user/redeems/**")
               .filters(getGatewayWithRewrite(requestedAT, "/user/redeems)",
                   "/redeems")).uri(userServiceHost))
           .route("delete-update-redeem", p-> p.path("/user/redeems/**")
               .filters(getGatewayWithRewrite(requestedAT, "/user/redeems)",
                   "/redeems")).uri(userServiceHost))
           .route("hold transaction", p-> p.path("/manage/transactions/*/hold")
               .filters(getGatewayWithRewrite(requestedAT, "/manage/transactions/<?<transactionId>.*/hold)",
                   "/transactions/${transactionId}/hold")).uri(userServiceHost))
           //auth's routes
           .route("create-login", p-> p.path("/manage/login")
               .filters(getGatewayWithPath(requestedAT, "/login")).uri(authServiceHost))
           .route("create-login", p-> p.path("/manage/admin")
               .filters(getGatewayWithPath(requestedAT, "/admin")).uri(authServiceHost))
           .route("create-token", p-> p.path("/token")
               .filters(getGateway(requestedAT))
               .uri(authServiceHost))
           .route("refresh-token", p-> p.path("/refresh_token")
               .filters(getGateway(requestedAT))
               .uri(authServiceHost))

           .build();

    }

    private Function<GatewayFilterSpec, UriSpec> getGatewayWithPath(String requestedAT, String path) {
        return f -> f.setPath(path)
            .addRequestHeader(X_APPLICATION_NAME, APPLICATION_NAME)
            .addRequestHeader(X_APPLICATION_HASH, getHash(requestedAT))
            .addRequestHeader(X_REQUESTED_AT, requestedAT)
            .dedupeResponseHeader("Access-Control-Allow-Origin " +
                "Access-Control-Allow-Credentials", "RETAIN_FIRST");
    }

    private Function<GatewayFilterSpec, UriSpec> getGatewayWithRewrite(String requestedAT, String regex,
                                                                       String replaceWith) {
        return f -> f.rewritePath(regex, replaceWith)
            .addRequestHeader(X_APPLICATION_NAME, APPLICATION_NAME)
            .addRequestHeader(X_APPLICATION_HASH, getHash(requestedAT))
            .addRequestHeader(X_REQUESTED_AT, requestedAT)
            .dedupeResponseHeader("Access-Control-Allow-Origin " +
                "Access-Control-Allow-Credentials", "RETAIN_FIRST");
    }

    private Function<GatewayFilterSpec, UriSpec> getGateway(String requestedAT) {
        return f -> f
            .addRequestHeader(X_APPLICATION_NAME, APPLICATION_NAME)
            .addRequestHeader(X_APPLICATION_HASH, getHash(requestedAT))
            .addRequestHeader(X_REQUESTED_AT, requestedAT)
            .dedupeResponseHeader("Access-Control-Allow-Origin " +
                "Access-Control-Allow-Credentials", "RETAIN_FIRST");
    }

    public String getHash(String requestedAt) {
        String payload = String.format("%s%s%s%s", APPLICATION_NAME, "key", requestedAt, "secret");
        return Hasher.getCrypt(payload, "secret");

    }
}
final class Hasher {
    //Defines number of hashing rounds. Its non configurable because passwords are supposed to
    // be protected using this. Changing this number may lead to failed password validation
    private static final int ROUNDS = 10;

    private Hasher(){}

    public static String getCrypt(String payload, String salt) {
        SecureRandom random = new SecureRandom(salt.getBytes());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(ROUNDS, random);

        return encoder.encode(payload);
    }

    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}