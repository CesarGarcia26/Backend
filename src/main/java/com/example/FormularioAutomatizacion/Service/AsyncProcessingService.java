package com.example.FormularioAutomatizacion.Service;

import com.example.FormularioAutomatizacion.Dto.DtoSteps.DtoMasterSaludVida;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncProcessingService {

    private final iServiceImpleSeguroVida seguroVida;
    private final ServiceEmailSaludColectiva serviceEmail;

    public AsyncProcessingService(iServiceImpleSeguroVida seguroVida,
                                  ServiceEmailSaludColectiva serviceEmail) {
        this.seguroVida = seguroVida;
        this.serviceEmail = serviceEmail;
    }

    @Async
    public void procesarEnSegundoPlano(DtoMasterSaludVida dto) {
        try {
            System.out.println("⚙️ Iniciando generación de documento...");
            ResponseEntity<byte[]> respuestaWord = seguroVida.fillWordFormVida(dto);

            System.out.println("📧 Enviando correo...");
            serviceEmail.enviarFormularioPorCorreo(dto, respuestaWord.getBody());

            System.out.println("✅ Proceso completado exitosamente");
        } catch (Exception e) {
            System.err.println("❌ Error en procesamiento async: " + e.getMessage());
            e.printStackTrace();
        }
    }
}