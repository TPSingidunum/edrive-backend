package rs.ac.singidunum.edrivebackend.config.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Component
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // Set origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Set methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // Allow cookies or auth headers
    }
}
