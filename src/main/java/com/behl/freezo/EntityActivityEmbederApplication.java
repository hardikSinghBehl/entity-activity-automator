package com.behl.freezo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.Data;

@SpringBootApplication
public class EntityActivityEmbederApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntityActivityEmbederApplication.class, args);
    }

}

@Data
@ConfigurationProperties(prefix = "com.behl.freezo")
class OpenApiConfigurationProperties {

    private Swagger swagger = new Swagger();

    @Data
    public class Swagger {
        private String title;
        private String description;
        private String apiVersion;
        private Contact contact = new Contact();
        private Security security = new Security();

        @Data
        public class Contact {
            private String email;
            private String name;
            private String url;
        }

        @Data
        public class Security {
            private String name;
            private String scheme;
            private String bearerFormat;
        }
    }

}

@Configuration
@EnableConfigurationProperties(OpenApiConfigurationProperties.class)
@AllArgsConstructor
class OpenApiConfiguration {

    private final OpenApiConfigurationProperties openApiConfigurationProperties;

    @Bean
    public OpenAPI customOpenAPI() {
        final var properties = openApiConfigurationProperties.getSwagger();
        final var security = properties.getSecurity();
        final var contact = properties.getContact();
        final var info = new Info().title(properties.getTitle()).version(properties.getApiVersion())
                .description(properties.getDescription())
                .contact(new Contact().email(contact.getEmail()).name(contact.getName()).url(contact.getUrl()));

        return new OpenAPI().info(info).addSecurityItem(new SecurityRequirement().addList(security.getName()))
                .components(new Components().addSecuritySchemes(security.getName(),
                        new SecurityScheme().name(security.getName()).type(SecurityScheme.Type.HTTP)
                                .scheme(security.getScheme()).bearerFormat(security.getBearerFormat())));
    }
}
