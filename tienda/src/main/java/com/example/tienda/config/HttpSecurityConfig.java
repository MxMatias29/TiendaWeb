package com.example.tienda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.tienda.config.filter.JwtAuthenticationFilter;
import com.example.tienda.util.Permisos;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrfConfig -> csrfConfig.disable())
            .sessionManagement(sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
            //.authorizeHttpRequests(builderRequestMatchers());

        return http.build();
    }

    private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> builderRequestMatchers() {
        return authConfig -> {
                // Metodos Publicos
                authConfig.requestMatchers(HttpMethod.POST, "/api/login").permitAll();
                authConfig.requestMatchers(HttpMethod.GET, "/api/tienda").permitAll();
                authConfig.requestMatchers("/error").permitAll();
                // Metodos Privados
                authConfig.requestMatchers(HttpMethod.GET, "/api/producto").hasAuthority(Permisos.READ_ALL_PRODUCTS_ADMIN.name());
                authConfig.requestMatchers(HttpMethod.POST, "/api/producto").hasAuthority(Permisos.SAVE_ONE_PRODUCT.name());

                authConfig.anyRequest().denyAll(); // Deniega el acceso a una ruta no especificada 

        };
    }

}
