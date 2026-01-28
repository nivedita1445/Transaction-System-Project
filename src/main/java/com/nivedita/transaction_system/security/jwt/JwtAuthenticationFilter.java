package com.nivedita.transaction_system.security.jwt;

import com.nivedita.transaction_system.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // ✅ Skip if no token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String email = null;

        try {
            email = jwtUtil.extractEmail(token);
        } catch (Exception e) {
            System.out.println("❌ JWT parsing failed: " + e.getMessage());
        }

        System.out.println("\n================ JWT FILTER DEBUG ================");
        System.out.println("REQUEST URI = " + request.getRequestURI());
        System.out.println("TOKEN = " + token);
        System.out.println("EMAIL FROM TOKEN = " + email);

        // ✅ If email extracted & no authentication exists yet
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            var userDetails = userDetailsService.loadUserByUsername(email);

            System.out.println("AUTHORITIES FROM DB = " + userDetails.getAuthorities());

            if (jwtUtil.validateToken(token)) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("✅ AUTHENTICATION SET SUCCESSFULLY");
            } else {
                System.out.println("❌ JWT validation failed");
            }
        }

        System.out.println("=================================================\n");

        filterChain.doFilter(request, response);
    }
}
