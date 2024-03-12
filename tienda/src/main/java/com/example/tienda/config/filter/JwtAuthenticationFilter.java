package com.example.tienda.config.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.tienda.Model.Entity.UserEntity;
import com.example.tienda.Model.Repository.UserRepository;
import com.example.tienda.Model.Service.Implementacion.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired 
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
                String authHeader = request.getHeader("Authorization");

                if(authHeader == null || !authHeader.startsWith("Bearer ")){
                    filterChain.doFilter(request, response);
                    return;
                }

                String jwt = authHeader.split(" ")[1];
                String username = jwtService.extractUsername(jwt);

                UserEntity user = userRepository.findByUsername(username).get();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
                
                filterChain.doFilter(request, response);
    }

}
