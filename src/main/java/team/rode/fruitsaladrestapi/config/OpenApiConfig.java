package team.rode.fruitsaladrestapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Fruit Salad API")
                        .description("REST API for salad management")
                        .version("v1.0")
                        .contact(new Contact().name("Mark Rode").email("rode.mark01@gmail.com")));
    }
}

