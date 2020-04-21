package com.utm.stanislav.parkingapp.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Smart Parking",
        version = "v1",
        description = "Swagger Documentation for Smart Parking Application",
        contact = @Contact(name = "Sănduță Stanislav",
                url = "https://hunyahiko.github.io/smart-parking-system/",
                email = "ssanduta@inthergroup.com"),
        license = @License(name = "MIT"))
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfiguration {
}
