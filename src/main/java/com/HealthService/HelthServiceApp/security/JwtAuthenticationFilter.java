package com.HealthService.HelthServiceApp.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.HealthService.HelthServiceApp.service.UserAuthService;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserAuthService userAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException
    {
        //System.out.println("JwtAuthenticationFilter");
        final String authorizationHeader = request.getHeader("Authorization");
        //System.out.println("authorizationHeader: " + authorizationHeader);

        String username = null;
        String jwtToken = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
        {

            jwtToken = authorizationHeader.substring(7);
            try {

                username = jwtUtil.getUserNameFromToken(jwtToken);
                //System.out.println("username: " + username);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }

        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails = this.userAuthService.loadUserByUsername(username);
           // System.out.println("userDetails: " + userDetails);
                if (jwtUtil.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    
                }
            

        }
       // System.out.println("JwtAuthenticationFilter chain end");
        chain.doFilter(request, response);
    }

}
