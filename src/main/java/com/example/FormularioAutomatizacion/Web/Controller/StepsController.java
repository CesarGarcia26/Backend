package com.example.FormularioAutomatizacion.Web.Controller;

import com.example.FormularioAutomatizacion.Dto.DtoSteps.DtoMasterSaludColectiva;
import com.example.FormularioAutomatizacion.Dto.DtoSteps.DtoMasterSaludVida;
import com.example.FormularioAutomatizacion.Service.AsyncProcessingService;
import com.example.FormularioAutomatizacion.Service.ServiceEmailSaludColectiva;
import com.example.FormularioAutomatizacion.Service.iServiceImpleRellenado;
import com.example.FormularioAutomatizacion.Service.iServiceImpleSeguroVida;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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


     // Recibe y procesa formularios de Salud Colectiva

    @PostMapping
    public ResponseEntity<byte[]> generarFormulario(@RequestBody DtoMasterSaludColectiva formulario) throws Exception {
        System.out.println("\n===============================================");
        System.out.println("FORMULARIO SALUD COLECTIVA RECIBIDO");
        System.out.println("===============================================");

        ResponseEntity<byte[]> respuestaWord = rellenadoService.fillWordForm(formulario);
        // Enviar correo con adjunto usando el nuevo mét odo
        serviceEmail.enviarFormularioPorCorreo(formulario, respuestaWord.getBody());

        return respuestaWord;


    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarDatos(@RequestBody DtoMasterSaludVida dto, Authentication authentication) throws Exception {
        System.out.println("\n========== DATOS RECIBIDOS SALUD VIDA ==========");
        String username = authentication.getName();
        asyncProcessingService.procesarEnSegundoPlano(dto, username);
        return ResponseEntity.ok(Map.of("message", "Formulario recibido, procesando..."));
    }
}