package dev.cucei.siiapi.config;

import tools.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${app.api-keys-hashed:}")
    private String apiKeysHashed;

    @jakarta.annotation.PostConstruct
    public void logConfig() {
        if (apiKeysHashed == null || apiKeysHashed.isBlank()) {
            log.warn("API_KEYS_HASHED is EMPTY — all write requests will be rejected");
        } else {
            log.info("API keys configured ({} hash(es) loaded)", apiKeysHashed.split(",").length);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/", "/api/", "/api/v2/").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v2/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(apiKeyFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private OncePerRequestFilter apiKeyFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                String method = request.getMethod();
                String path = request.getRequestURI();

                if ("GET".equals(method) || "OPTIONS".equals(method)) {
                    filterChain.doFilter(request, response);
                    return;
                }

                String apiKey = request.getHeader("X-API-Key");
                log.debug("API Key filter: method={}, path={}, hasKey={}", method, path, apiKey != null);

                if (apiKey == null || apiKey.isBlank()) {
                    log.warn("Missing API key for {} {}", method, path);
                    sendError(response, HttpStatus.UNAUTHORIZED, "Missing X-API-Key header");
                    return;
                }

                if (!isValidApiKey(apiKey)) {
                    log.warn("Invalid API key for {} {}", method, path);
                    sendError(response, HttpStatus.UNAUTHORIZED, "Invalid API key");
                    return;
                }

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken("apikey", null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);

                filterChain.doFilter(request, response);
            }
        };
    }

    private void sendError(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, message);
        problem.setTitle(status.getReasonPhrase());
        problem.setType(URI.create("https://api.cucei.dev/errors/" + status.series().name().toLowerCase()));
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), problem);
    }

    private boolean isValidApiKey(String apiKey) {
        if (apiKeysHashed == null || apiKeysHashed.isBlank()) {
            log.warn("No API keys configured (app.api-keys-hashed is empty)");
            return false;
        }

        String hashed = sha256(apiKey);
        log.debug("Computed hash: {} (configured hashes: {})", hashed, apiKeysHashed.length() > 16 ? apiKeysHashed.substring(0, 16) + "..." : apiKeysHashed);
        String[] validHashes = apiKeysHashed.split(",");
        for (String validHash : validHashes) {
            if (hashed.equalsIgnoreCase(validHash.trim())) {
                return true;
            }
        }
        return false;
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}
