package com.kgcorner.topspin;


import com.kgcorner.crypto.Hasher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 15/08/21
 */

@Component
public class Routers {

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
                    .filters(getGatewayWithPath(requestedAT, "/stores")).uri(storeServiceHost))
           .route("update-and-delete-store", p-> p.path("/manage/stores/**")
               .filters(getGatewayWithRewrite(requestedAT, "/manage/stores/(?<storeId>.*)",
                   "/stores/${storeId}")).uri(storeServiceHost))
           .route("get-stores", p-> p.path("/stores/**")
               .filters(getGateway(requestedAT))
               .uri(storeServiceHost))
            .route("get-all-stores", p-> p.path("/stores")
                    .filters(getGateway(requestedAT)
                    ).uri(storeServiceHost))

           //Category's routes
           .route("create-category", p-> p.path("/manage/categories")
               .filters(getGatewayWithPath(requestedAT, "/categories")).uri(storeServiceHost))
           .route("update-and-delete-category", p-> p.path("/manage/categories/**")
               .filters(getGatewayWithRewrite(requestedAT, "/manage/categories/(?<categoryId>.*)",
                   "/categories/${categoryId}")).uri(storeServiceHost))
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
               .filters(getGatewayWithPath(requestedAT, "/offers")).uri(productOfferServiceHost))
           .route("update-and-delete-offer", p-> p.path("/manage/offers/**")
               .filters(getGatewayWithRewrite(requestedAT, "/manage/offers/(?<offerId>.*)",
                   "/offers/${offerId}")).uri(productOfferServiceHost))
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
           .build();

    }

    private Function<GatewayFilterSpec, UriSpec> getGatewayWithPath(String requestedAT, String path) {
        return f -> f.setPath(path)
            .addRequestHeader("X-Application-name", "gateway")
            .addRequestHeader("X-Application-Hash", getHash(requestedAT))
            .addRequestHeader("X-REquested-At", requestedAT);
    }

    private Function<GatewayFilterSpec, UriSpec> getGatewayWithRewrite(String requestedAT, String regex,
                                                                       String replaceWith) {
        return f -> f.rewritePath(regex, replaceWith)
            .addRequestHeader("X-Application-name", "gateway")
            .addRequestHeader("X-Application-Hash", getHash(requestedAT))
            .addRequestHeader("X-REquested-At", requestedAT);
    }

    private Function<GatewayFilterSpec, UriSpec> getGateway(String requestedAT) {
        return f -> f
            .addRequestHeader("X-Application-name", "gateway")
            .addRequestHeader("X-Application-Hash", getHash(requestedAT))
            .addRequestHeader("X-REquested-At", requestedAT);
    }

    public String getHash(String requestedAt) {
        String payload = String.format("%s%s%s%s", "gateway", "key", requestedAt, "secret");
        return Hasher.getCrypt(payload, "secret");

    }
}