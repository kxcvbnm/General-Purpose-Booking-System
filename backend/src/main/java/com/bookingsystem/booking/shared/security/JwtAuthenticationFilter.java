package com.bookingsystem.booking.shared.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, 
                                   CustomUserDetailsService customUserDetailsService) {
        
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    // @Override
    // protected boolean shouldNotFilter(HttpServletRequest req) {
    //     String p = req.getServletPath();
    //     return p.startsWith("/api/auth");
    // }

    @Override
    protected void doFilterInternal(HttpServletRequest req, 
                                    HttpServletResponse res, 
                                    FilterChain filterChain) 
        
        throws ServletException, IOException {

        String auth = req.getHeader(HttpHeaders.AUTHORIZATION);
        if(auth == null || !auth.startsWith("Bearer ")) {
            filterChain.doFilter(req, res);
            return;
        }

        String token = auth.substring(7);
            try{
                var jws = jwtService.parse(token);
                if(jwtService.isAccessToken(token)) {
                    String email = jws.getBody().get("email", String.class);
                    UserDetails user = customUserDetailsService.loadUserByUsername(email);
                    var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(authToken); 
                }    
            } catch(JwtException ex) {
                req.setAttribute("auth.error", "invalid_token");
            } 
            filterChain.doFilter(req, res);
    }
}
