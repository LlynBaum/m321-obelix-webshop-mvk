package ch.bbw.obelix.webshop.service;

import ch.bbw.obelix.quarry.api.QuarryApi;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@Service
public class MenhirWebClientService {

    private final WebClient.Builder webClientBuilder;

    public MenhirWebClientService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public QuarryApi createClient(){
        var webClient = webClientBuilder
                .baseUrl("http://localhost:8081")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .defaultIfEmpty("Could not find Menhir.")
                                .flatMap(msg -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, msg)))
                )
                .build();

        var httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();
        return httpServiceProxyFactory.createClient(QuarryApi.class);
    }
}
