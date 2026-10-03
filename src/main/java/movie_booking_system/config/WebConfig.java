package movie_booking_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply CORS configuration to all endpoints in the application
                        .allowedOrigins("http://localhost:5173") // Allow requests coming from your React Vite frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow standard HTTP CRUD operations
                        .allowedHeaders("*") // Allow all request headers (including Authorization for JWT)
                        .allowCredentials(true); // Allow sending credentials/cookies if needed
            }
        };
    }
}

