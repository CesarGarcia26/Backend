package com.example.FormularioAutomatizacion.Web.Controller;

import com.example.FormularioAutomatizacion.Dto.DtoSteps.DtoMasterSaludColectiva;
import com.example.FormularioAutomatizacion.Dto.DtoSteps.DtoMasterSaludVida;
import com.example.FormularioAutomatizacion.Service.ServiceEmailSaludColectiva;
import com.example.FormularioAutomatizacion.Service.iServiceImpleRellenado;
import com.example.FormularioAutomatizacion.Service.iServiceImpleSeguroVida;
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

    public StepsController(
            iServiceImpleRellenado rellenadoService,
            ServiceEmailSaludColectiva serviceEmail,
            iServiceImpleSeguroVida seguroVida
    ) {
        this.rellenadoService = rellenadoService;
        this.serviceEmail = serviceEmail;
        this.seguroVida = seguroVida;
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
    public ResponseEntity<byte[]> guardarDatos(@RequestBody DtoMasterSaludVida dto) throws Exception {
        System.out.println("\n========== DATOS RECIBIDOS SALUD VIDA ==========");

        // Generar documento Word con placeholders
        ResponseEntity<byte[]> respuestaWord = seguroVida.fillWordFormVida(dto);
        // Enviar correo con adjunto usando el nuevo mé todo
        serviceEmail.enviarFormularioPorCorreo(dto, respuestaWord.getBody());
        return respuestaWord;
    }
}