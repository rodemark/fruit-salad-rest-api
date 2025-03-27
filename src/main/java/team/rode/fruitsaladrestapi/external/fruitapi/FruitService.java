package team.rode.fruitsaladrestapi.external.fruitapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import team.rode.fruitsaladrestapi.exceptionHandling.exceptions.ExternalApiException;
import team.rode.fruitsaladrestapi.external.fruitapi.DTO.FruitResponseDto;

@Service
public class FruitService {

    private final WebClient webClient;

    @Autowired
    public FruitService(WebClient.Builder webClientBuilder,
                        @Value("${external.fruitapi.url}") String externalApiUrl) {
        this.webClient = webClientBuilder.baseUrl(externalApiUrl).build();
    }

    public FruitResponseDto getFruitByName(String name) {
        FruitResponseDto fruitResponseDto = getFruitByNameFromAPI(name).block();

        if (fruitResponseDto == null || fruitResponseDto.getNutritions() == null) {
            throw new ExternalApiException("There is no information about the fruit or its nutrients for: "
                    + name);
        }

        return fruitResponseDto;
    }

    private Mono<FruitResponseDto> getFruitByNameFromAPI(String name) {
        return webClient.get()
                .uri("/api/fruit/{name}", name)
                .retrieve()
                .onStatus(
                        status -> status.value() != HttpStatus.OK.value(),
                        clientResponse -> Mono.error(new ExternalApiException(
                                "Unexpected response status: " + clientResponse.statusCode() + " for: " + name))
                )
                .bodyToMono(FruitResponseDto.class);
    }
}
