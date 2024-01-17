package cz.eg.hr.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Javascript Framework API")
                .description("This API helps with aggregating information about popular Javascript Frameworks, " +
                    "including versions, their EOS, and individual framework rating")
                .version("1.0"));
    }
}
