package com.samia.ecole.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;
    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService, JwtFilter jwtFilter) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return
                httpSecurity
                        .cors(Customizer.withDefaults())
                        .csrf(AbstractHttpConfigurer::disable)
//                        .csrf((csrf) -> csrf
//                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                        .authorizeHttpRequests(
                                authorize ->
                                        authorize
                                                .requestMatchers(GET,"/images/**").permitAll()
                                                .requestMatchers(POST,"/signup").permitAll()
                                                .requestMatchers(POST,"/activation").permitAll()
                                                .requestMatchers(POST,"/identification").permitAll()
                                                .requestMatchers(POST,"/connexion").permitAll()
                                                .requestMatchers(POST,"/refresh-token").permitAll()
                                                .requestMatchers(POST,"/change-password").permitAll()
                                                .requestMatchers(POST,"/new-password").permitAll()
                                                .requestMatchers(GET,"/users/{id}").permitAll()//.hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(PUT,"/users/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(GET,"/users").hasAnyAuthority("ROLE_SUPER_ADMIN")
                                                .requestMatchers(GET,"/classroom/{classroomId}/users").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(DELETE,"/users/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN")
                                                .requestMatchers(POST,"/students").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(GET,"/students/student/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(PUT,"/students/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(GET,"/students/user/{userId}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(GET,"/students/classroom/{classroomId}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(GET,"/students/{id}/classroom/{classroomId}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(GET,"/students").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(DELETE,"/students/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(POST,"/posts").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(GET,"/posts/post/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(GET,"/posts").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(DELETE,"/posts/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(PUT,"/posts/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(GET,"/posts/user/{userId}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(POST,"/classroom").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(DELETE,"/classroom/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(PUT,"/classroom/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(GET,"/classroom/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(GET,"/classroom").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(POST,"/classroom/activation").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(GET,"/posts/classroom/{classroomId}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(POST,"/updates/{studentId}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(GET,"/updates").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(GET,"/updates/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(PUT,"/updates/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(GET,"/updates").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(GET,"/updates/classroom/{classroomId}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(GET,"/updates/regular/{studentId}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(DELETE,"/updates/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(POST,"/cahierDeLiaison/{studentId}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(GET,"/cahierDeLiaison/all").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(GET,"/cahierDeLiaison/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(GET,"/cahierDeLiaison/all/{studentId}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PARENT")
                                                .requestMatchers(PUT,"/cahierDeLiaison/update/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .requestMatchers(DELETE,"/cahierDeLiaison/{id}").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN")
                                                .anyRequest().authenticated()
                        )
                        .sessionManagement(httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                        .logout(AbstractHttpConfigurer::disable)
                        .build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type", "*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return daoAuthenticationProvider;
    }
}
