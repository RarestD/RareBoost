package rare.creations.RareBoost.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import rare.creations.RareBoost.entity.Constellation;
import rare.creations.RareBoost.util.WebClientConfig;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestServices {


    ConfigurableApplicationContext applicationContext;

    private final String baseUrl = "https://discord.com/api/v9/channels/";

    private final WebClient.Builder requests;

    public RequestServices(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.requests = applicationContext.getBean(WebClientConfig.class).webClientBuilder();
    }


    public Map<String, String> dict(String key, String value){
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put(key, value);
        return stringMap;
    }

    public String sendConstellation(Constellation constellation){
        Map<String, String> content = dict("content", constellation.getMessage());
        String url = baseUrl + constellation.getDestination() + "/messages?limit=10";
        System.out.println(constellation.getMessage());
        System.out.println(constellation.getDestination());
        System.out.println(url);

        try {
            return requests.build().post()
                    .uri(url)
                    .header("authorization", constellation.getAuth())
                    .body(BodyInserters.fromValue(content))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }catch (Exception e){
            if (e.getMessage().contains("401 Unauthorized")){
                throw e;
            }else if (e.getMessage().contains("404 Not")) {
                throw e;
            }else  if (e.getMessage().contains("403")){
                throw e;
            }
            System.out.println(e.getMessage());
            System.out.println("Error Sending");
            System.out.println("A constellation has been stopped...!");
            throw e;
        }
    }

}
