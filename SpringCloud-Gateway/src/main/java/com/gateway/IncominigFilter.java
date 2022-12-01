//package com.gateway;
//
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.cloud.gateway.route.Route;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.text.SimpleDateFormat;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.UUID;
//
//import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR;
//import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
//import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;
//@Component
//public class IncominigFilter implements GlobalFilter {
//    Log log = LogFactory.getLog(getClass());
//    @SuppressWarnings("unchecked")
//	@Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        Set<URI> uris = exchange.getAttributeOrDefault(GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
//        String originalUri = (uris.isEmpty()) ? "Unknown" : uris.iterator().next().toString();
//        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
//        URI routeUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
//        UUID uuid=UUID.randomUUID();
////        response.setHeader("Access-Control-Allow-Origin", "*");
////        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
////        response.setHeader("Access-Control-Max-Age", "3600");
////        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
////        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
//        HttpHeaders headers = exchange.getRequest().getHeaders();
//        System.out.println(headers);
//        List<String> list = headers.get("authorization");
//       System.out.println( list.get(0));
//        ServerHttpRequest request=exchange.getRequest().mutate()
//        		.header("Authorization",list.get(0) )
//        		.build();
//        ServerWebExchange exchange1 = exchange.mutate().request(request).build();
//		
//		
//        log.info("Incoming request " + originalUri + " is routed to id: " + route.getId()+"metadata is "+route.getMetadata()
//                + ", uri:" + routeUri);
//        return chain.filter(exchange1).then(Mono.fromRunnable(()-> { 
//          
//        		HttpStatus statusCode = exchange1.getResponse().getStatusCode();
//        		System.out.println(statusCode);
//        }));
//        
//    }
//    
//    
//    
//    
//    
//    
//    
//    
//}
