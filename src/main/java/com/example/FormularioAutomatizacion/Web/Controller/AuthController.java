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

            // Validar empresa para evitar NullPointer
            String nombreEmpresa = (user.getEmpresa() != null)
                    ? user.getEmpresa().getNombreEmpresa()
                    : "Sin empresa";

            // Generar el token
            String token = JwtUtil.generateToken(
                    user.getUsername(),
                    nombreEmpresa,
                    Collections.singletonMap("email", user.getEmail())
            );

            // 🔐 CAMBIO CRÍTICO: Enviar token en cookie httpOnly
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true); // No accesible desde JavaScript (protección XSS)
            cookie.setSecure(false); // ⚠️ Cambiar a true en producción (requiere HTTPS)
            cookie.setPath("/");
            cookie.setMaxAge(3600); // 1 hora en segundos
            cookie.setAttribute("SameSite", "Lax"); // Protección CSRF

            response.addCookie(cookie);

            // Retornar datos del usuario (SIN el token)
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

    // 🆕 NUEVO ENDPOINT: Verificar si el usuario está autenticado
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

    // 🆕 NUEVO ENDPOINT: Logout (limpiar cookie)
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // ⚠️ true en producción
        cookie.setPath("/");
        cookie.setMaxAge(0); // Eliminar cookie inmediatamente

        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Logout exitoso"));
    }
}