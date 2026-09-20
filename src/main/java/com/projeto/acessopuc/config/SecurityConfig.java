package com.projeto.acessopuc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableWebSecurity 
public class SecurityConfig {
    private final UserConfig userconfig; //fonte dos dados dos usuários (application.properties)

    public SecurityConfig(UserConfig userConfig) {
        this.userconfig = userConfig;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //cadeia de filtros para o securityspring (indica redirecionamentos)
        return http
            .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, "/login/**").permitAll() //para todo GET com esse endereço, deixa entrar
            .requestMatchers(HttpMethod.GET, "/login/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/css/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/register").permitAll()
            .requestMatchers(HttpMethod.POST, "/register").permitAll()
            .requestMatchers(HttpMethod.GET, "/recoverpassword").permitAll()
            .requestMatchers(HttpMethod.POST, "/recoverpassword").permitAll()
            .requestMatchers(HttpMethod.GET, "/resetpassword").permitAll()
            .requestMatchers(HttpMethod.POST, "/resetpassword").permitAll()
            .requestMatchers(HttpMethod.GET, "/error").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN") //procura por permissão com ROLE_ADMIN
            .anyRequest().authenticated()) //se não foi citado acima, só entra com login feito
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler((request, response, authentication) -> { //configuração do login
                    if (authentication.getAuthorities().stream()
                            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
                        response.sendRedirect("/admin"); //indica onde está a tela. O Controller renderiza
                    } else {
                        response.sendRedirect("/home"); //indica onde está a tela. O Controller renderiza
                    }
                })
                .failureHandler((request, response, exception) -> {
                    response.sendRedirect("/error");
                }))
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            .build();
    }

    @Bean 
    public InMemoryUserDetailsManager userDetailService() {
        UserDetails user = User.builder().username(userconfig.getUserUsername()).password(passwordEncoder().encode(userconfig.getUserPassword())).roles("USER").build(); //user (vem do properties) e senha fica escondida; role de usuário comum
        UserDetails admin = User.builder().username(userconfig.getAdminUsername()).password(passwordEncoder().encode(userconfig.getAdminPassword())).roles("ADMIN").build(); //admin, com o mesmo processo de senha escondida; o role muda de "USER" para "ADMIN"
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //criptografa a senha (não tem como alterar para o estado anterior ao criptografado)
    }

}
