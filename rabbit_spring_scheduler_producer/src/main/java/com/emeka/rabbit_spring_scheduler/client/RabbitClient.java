package com.emeka.rabbit_spring_scheduler.client;

import com.emeka.rabbit_spring_scheduler.entity.RabbitmqQueue;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Base64;
import java.util.List;

@Service
public class RabbitClient {

//    use webclient to get all queues
    public List<RabbitmqQueue> getAllQueues (){
       WebClient webClient = WebClient.create("http://localhost:15672/api/queues");
       String basicAuthHeader = createBasicAuthHeaders("guest", "guest");
       return webClient.get().header(HttpHeaders.AUTHORIZATION,basicAuthHeader).retrieve().bodyToMono(
               new ParameterizedTypeReference<List<RabbitmqQueue>>() {
               }).block(Duration.ofSeconds(10));

    }
    public String createBasicAuthHeaders(String username, String password){
        String authString = username+":"+password;
         return "Basic "+ Base64.getEncoder().encodeToString(authString.getBytes());
    }
}
