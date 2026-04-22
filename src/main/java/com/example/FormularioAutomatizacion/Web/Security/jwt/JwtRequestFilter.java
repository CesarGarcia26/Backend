package com.example.FormularioAutomatizacion.Web.Security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.Collections;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String SECRET_KEY = "tor_ux_is_my_key_1_2_3_tor_ux_is_my_key_1_2_3";
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String username = null;
        String jwt = null;

        // 🔐 CAMBIO CRÍTICO: Leer token desde cookie en lugar de Authorization header
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        // Si no hay cookie, intentar leer desde Authorization header (para compatibilidad)
        if (jwt == null) {
            final String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
            }
        }

        // Validar el token
        if (jwt != null) {
            try {
                username = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody()
                        .getSubject();
            } catch (ExpiredJwtException e) {
                System.out.println("Token expirado: " + e.getMessage());
                // El frontend manejará el 401
            } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                System.out.println("Token inválido: " + e.getMessage());
            }
        }

        // Autenticar al usuario si el token es válido
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = new User(username, "", Collections.emptyList());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}