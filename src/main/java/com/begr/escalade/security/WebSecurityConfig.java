package com.begr.escalade.security;


import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        User.UserBuilder users = User.withDefaultPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser(users.username("ben").password("test123").roles("ADMIN"))
                .withUser(users.username("john").password("test123").roles("CONTRIBUTEUR"))
                .withUser(users.username("mary").password("test123").roles("UTILISATEUR"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()//toutes les requêtes http doivent être authentifiées
                .and()
                .formLogin()
                    .loginPage("/showMyLoginPage") // adresse du formulaire de login - utiliser la méthode GET // adresse de la page de validation du formulaire - Méthode POST
                    .permitAll()// Tout le monde peut voir la login page..
                .and()
                .logout().permitAll()// Tout le monde peut voir la page logout
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied"); // chemin vers la page 403 custom

    }

}
