package rs.ac.singidunum.edrivebackend.config.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Radim filter");

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        assert authHeader != null;
        Claims claims = jwtService.extractClaimsFromToken(authHeader.substring(7));
        // If Username exist check it

        // Create Security Context

        // Odluci gde ces cuvati Security Context

        // Add SecurityToken to the context (UsernameAndPasswordToken, JwtAuthT0ken)


        filterChain.doFilter(request, response);
    }
}
