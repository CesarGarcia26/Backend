package com.example.FormularioAutomatizacion.Web.Controller;

import com.example.FormularioAutomatizacion.Entity.EntityUser;
import com.example.FormularioAutomatizacion.Web.Security.jwt.JwtUtil;
import com.example.FormularioAutomatizacion.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ubicacion")
public class AuthController {

    @Autowired
    private UserRepository usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpServletResponse response) {
        String username = body.get("username");
        String password = body.get("password");

        EntityUser user = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (passwordEncoder.matches(password, user.getPassword())) {

            String nombreEmpresa = (user.getEmpresa() != null)
                    ? user.getEmpresa().getNombreEmpresa()
                    : "Sin empresa";

            String token = JwtUtil.generateToken(
                    user.getUsername(),
                    nombreEmpresa,
                    Collections.singletonMap("email", user.getEmail())
            );

            // ✅ Configuración correcta para producción
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);       // ✅ HTTPS en producción
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            cookie.setAttribute("SameSite", "None"); // ✅ permite cross-domain

            response.addCookie(cookie);

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("username", user.getUsername());
            responseBody.put("empresa", nombreEmpresa);
            responseBody.put("email", user.getEmail());
            responseBody.put("message", "Login exitoso");

            return ResponseEntity.ok(responseBody);

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }

        String username = authentication.getName();
        EntityUser user = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String nombreEmpresa = (user.getEmpresa() != null)
                ? user.getEmpresa().getNombreEmpresa()
                : "Sin empresa";

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("username", user.getUsername());
        userInfo.put("empresa", nombreEmpresa);
        userInfo.put("email", user.getEmail());

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);          // ✅ HTTPS en producción
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "None"); // ✅ permite cross-domain

        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Logout exitoso"));
    }
}