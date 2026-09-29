package com.devcareeros.security;

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

import java.io.IOException;

/**
 * This filter runs ONCE for every single incoming HTTP request (that's what
 * "OncePerRequestFilter" means), BEFORE the request reaches our controllers.
 *
 * Its job:
 *   1. Look for a header:  Authorization: Bearer <token>
 *   2. If present and valid, figure out which user it belongs to.
 *   3. Tell Spring Security "this request is authenticated as this user",
 *      so our @RestController methods can use @AuthenticationPrincipal
 *      or SecurityContextHolder to know who is calling the API.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // remove "Bearer " prefix

            if (jwtUtil.isTokenValid(token)) {
                String email = jwtUtil.getEmailFromToken(token);

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Tell Spring Security: this request is now authenticated.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Always continue the filter chain, whether or not we authenticated.
        filterChain.doFilter(request, response);
    }
}
