package com.example.FormularioAutomatizacion.Service;

import com.example.FormularioAutomatizacion.Dto.DtoSteps.DtoMasterSaludColectiva;
import com.example.FormularioAutomatizacion.Dto.DtoSteps.DtoMasterSaludVida;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncProcessingService {

    private final iServiceImpleSeguroVida seguroVida;
    private final ServiceEmailSaludColectiva serviceEmail;
    private final iServiceImpleRellenado rellenadoService; // ← nuevo

    public AsyncProcessingService(iServiceImpleSeguroVida seguroVida,
                                  ServiceEmailSaludColectiva serviceEmail,
                                  iServiceImpleRellenado rellenadoService) { // ← nuevo
        this.seguroVida = seguroVida;
        this.serviceEmail = serviceEmail;
        this.rellenadoService = rellenadoService; // ← nuevo
    }

    // ✅ Salud Vida — igual que antes
    @Async
    public void procesarEnSegundoPlano(DtoMasterSaludVida dto, String username) {
        try {
            System.out.println("⚙️ Iniciando generación de documento Salud Vida...");
            ResponseEntity<byte[]> respuestaWord = seguroVida.fillWordFormVida(dto, username);
            String empresa = seguroVida.getEmpresaByUsername(username);
            System.out.println("📧 Enviando correo...");
            serviceEmail.enviarFormularioPorCorreo(dto, respuestaWord.getBody(), empresa);
            System.out.println("✅ Salud Vida completado exitosamente");
        } catch (Exception e) {
            System.err.println("❌ Error Salud Vida: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ Salud Colectiva — nuevo
    @Async
    public void procesarColectivaEnSegundoPlano(DtoMasterSaludColectiva dto) {
        try {
            System.out.println("⚙️ Iniciando generación de documento Salud Colectiva...");
            ResponseEntity<byte[]> respuestaWord = rellenadoService.fillWordForm(dto);
            System.out.println("📧 Enviando correo...");
            serviceEmail.enviarFormularioPorCorreo(dto, respuestaWord.getBody());
            System.out.println("✅ Salud Colectiva completado exitosamente");
        } catch (Exception e) {
            System.err.println("❌ Error Salud Colectiva: " + e.getMessage());
            e.printStackTrace();
        }
    }
}