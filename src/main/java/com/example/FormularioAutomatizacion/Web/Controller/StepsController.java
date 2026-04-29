package com.example.FormularioAutomatizacion.Web.Controller;

import com.example.FormularioAutomatizacion.Dto.DtoSteps.DtoMasterSaludColectiva;
import com.example.FormularioAutomatizacion.Dto.DtoSteps.DtoMasterSaludVida;
import com.example.FormularioAutomatizacion.Service.AsyncProcessingService;
import com.example.FormularioAutomatizacion.Service.ServiceEmailSaludColectiva;
import com.example.FormularioAutomatizacion.Service.iServiceImpleRellenado;
import com.example.FormularioAutomatizacion.Service.iServiceImpleSeguroVida;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ubicacion")
public class StepsController {

    private final iServiceImpleRellenado rellenadoService;
    private final ServiceEmailSaludColectiva serviceEmail;
    private final iServiceImpleSeguroVida seguroVida;
    private final AsyncProcessingService asyncProcessingService;

    public StepsController(
            iServiceImpleRellenado rellenadoService,
            ServiceEmailSaludColectiva serviceEmail,
            iServiceImpleSeguroVida seguroVida,
            AsyncProcessingService asyncProcessingService
    ) {
        this.rellenadoService = rellenadoService;
        this.serviceEmail = serviceEmail;
        this.seguroVida = seguroVida;
        this.asyncProcessingService = asyncProcessingService;
    }

    // ✅ Salud Colectiva — público, async
    @PostMapping("/guardar-colectiva")
    public ResponseEntity<Map<String, String>> generarFormulario(
            @RequestBody DtoMasterSaludColectiva formulario) {

        asyncProcessingService.procesarColectivaEnSegundoPlano(formulario);
        return ResponseEntity.ok(Map.of("mensaje", "Formulario recibido. El documento será enviado al correo."));
    }

    // ✅ Salud Vida — requiere JWT, lee username del contexto
    @PostMapping("/guardar")
    public ResponseEntity<Map<String, String>> guardarDatos(
            @RequestBody DtoMasterSaludVida dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // ← viene del token JWT

        asyncProcessingService.procesarEnSegundoPlano(dto, username);
        return ResponseEntity.ok(Map.of("mensaje", "Formulario recibido. El documento será enviado al correo."));
    }
}