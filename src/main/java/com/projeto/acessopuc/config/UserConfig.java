package com.projeto.acessopuc.config;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component //Spring cria um objeto dela, guarda e entrega pra quem precisar
public class UserConfig {
    @Value("${app.user.username}")
    private String userUsername;

    @Value("${app.user.password}")
    private String userPassword;
    
    @Value("${app.admin.username}")
    private String adminUsername;
    
    @Value("${app.admin.password}")
    private String adminPassword;

    public String getUserUsername() {
        return userUsername;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }
}
