package com.example.tienda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tienda.Model.DTO.Auth.AuthenticationRequest;
import com.example.tienda.Model.DTO.Auth.AuthenticationResponse;
import com.example.tienda.Model.Service.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PreAuthorize("permitAll")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (@RequestBody @Valid AuthenticationRequest authRequest){
        
        AuthenticationResponse jwtDto = authenticationService.login(authRequest);

        return ResponseEntity.ok(jwtDto);
    }
    
    @PreAuthorize("permitAll")
    @GetMapping("/tienda")
    public String publicAccessEndPoint(){
        return "Esto es publico" ;
    }

}
