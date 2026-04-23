package com.example.FormularioAutomatizacion.Service;

import com.example.FormularioAutomatizacion.Dto.DtoSteps.DtoMasterSaludColectiva;
import com.example.FormularioAutomatizacion.Dto.DtoSteps.DtoMasterSaludVida;
import com.example.FormularioAutomatizacion.Entity.EntityInfoEmpresas;
import com.example.FormularioAutomatizacion.Entity.correos;
import com.example.FormularioAutomatizacion.Web.Security.jwt.JwtUtil;
import com.example.FormularioAutomatizacion.repository.CorreosRepository;
import com.example.FormularioAutomatizacion.repository.InfoEmpresaRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
public class ServiceEmailSaludColectiva {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CorreosRepository correoRepo;

    @Autowired
    private InfoEmpresaRepository empresaRepo;

    // -----------------------------------------
    //  COLORES PARA CADA EMPRESA
    // -----------------------------------------
    private String[] getColorsByEmpresa(String empresa) {
        if (empresa == null) return new String[]{"#667eea", "#764ba2"};

        switch (empresa.toLowerCase()) {
            case "ceiba":
                return new String[]{"#667eea", "#764ba2"};
            case "pragma":
                return new String[]{"#6a1b9a", "#4a0072"};
            case "susanita":
                return new String[]{"#ff4d4d", "#800000"};
            case "nuvant":
                return new String[]{"#ff6647", "#FC2C00"};
            default:
                return new String[]{"#0d47a1", "#b71c1c"};

        }
    }

    // -----------------------------------------
    //  OBTENER EMPRESA DESDE EL TOKEN JWT
    // -----------------------------------------
    private String obtenerEmpresaDelToken() {
        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();

                // Buscar token en cookies
                String token = null;
                if (request.getCookies() != null) {
                    for (Cookie cookie : request.getCookies()) {
                        if ("token".equals(cookie.getName())) {
                            token = cookie.getValue();
                            break;
                        }
                    }
                }

                // Buscar en header si no está en cookie
                if (token == null) {
                    String authHeader = request.getHeader("Authorization");
                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        token = authHeader.substring(7);
                    }
                }

                if (token != null) {
                    return JwtUtil.getEmpresaFromToken(token);
                }
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error al extraer empresa del token: " + e.getMessage());
        }
        return null;
    }

    // -----------------------------------------
    //  ENVÍA UN CORREO INDIVIDUAL
    // -----------------------------------------
    private void enviarCorreoIndividual(
            String destinatario,
            String tipoFormulario,
            String nombreCompleto,
            String empresa,
            byte[] fileBytes
    ) throws Exception {

        String[] colors = getColorsByEmpresa(empresa);
        String colorPrimario = colors[0];
        String colorSecundario = colors[1];

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(destinatario);
        helper.setSubject("Solicitud de " + tipoFormulario + " - " + nombreCompleto.toUpperCase());

        String htmlMsg = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
        </head>
        <body style="margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, %s 0%%, %s 100%%); min-height: 100vh;">
            <table width="100%%" cellpadding="0" cellspacing="0" style="padding: 40px 20px;">
                <tr>
                    <td align="center">
                        <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 16px; box-shadow: 0 20px 60px rgba(0,0,0,0.3); overflow: hidden;">
                            
                            <tr>
                                <td style="background: linear-gradient(135deg, %s 0%%, %s 100%%); padding: 40px 30px; text-align: center;">
                                    <h1 style="color: #ffffff; margin: 0; font-size: 28px;">Solicitud de %s</h1>
                                    <p style="color: rgba(255,255,255,0.95); margin-top: 10px;">Generado para %s</p>
                                </td>
                            </tr>

                            <tr>
                                <td style="padding: 40px 30px;">
                                    <p style="color: #333; font-size: 15px; line-height: 1.8;">
                                        Tu solicitud de <strong style="color: %s;">%s</strong> ha sido recibida.
                                    </p>
                                    <p style="color: #333; font-size: 15px; line-height: 1.8;">
                                        Adjunto encontrarás el formulario con la información proporcionada.
                                    </p>

                                    <div style="background-color: #fff3cd; border-left: 4px solid #ffc107; padding: 15px; border-radius: 8px;">
                                        ⚠️ Revisa que toda la información sea correcta.
                                    </div>

                                    <p style="color: #888; font-size: 13px; text-align: center; margin-top: 30px;">
                                        Si tienes dudas, contáctanos.
                                    </p>
                                </td>
                            </tr>

                            <tr>
                                <td style="background-color: #f8f9fa; padding: 30px; text-align: center;">
                                    <p style="color: %s; font-size: 18px; font-weight: 600;">
                                        Equipo PactoArrubla
                                    </p>
                                </td>
                            </tr>

                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        """.formatted(
                colorPrimario, colorSecundario,
                colorPrimario, colorSecundario,
                tipoFormulario, empresa,
                colorPrimario, tipoFormulario,
                colorPrimario
        );

        helper.setText(htmlMsg, true);

        helper.addAttachment(
                "formulario-" + tipoFormulario.replace(" ", "_").toLowerCase() + ".docx",
                new ByteArrayResource(fileBytes)
        );

        mailSender.send(message);
        System.out.println("📧 Enviado a: " + destinatario);
    }

    // -----------------------------------------
    //  ENVÍA A TODOS LOS CORREOS DE LA EMPRESA
    // -----------------------------------------
    public void enviarFormularioPorCorreo(
            String tipoFormulario,
            String nombreCompleto,
            byte[] fileBytes
    ) throws Exception {
        String empresa = obtenerEmpresaDelToken();
        enviarFormularioPorCorreo(tipoFormulario, nombreCompleto, fileBytes, empresa);
    }

    public void enviarFormularioPorCorreo(
            String tipoFormulario,
            String nombreCompleto,
            byte[] fileBytes,
            String empresa
    ) throws Exception {
        EntityInfoEmpresas empresaEntity =
                empresaRepo.findByNombreEmpresa(empresa)
                        .orElseThrow(() -> new RuntimeException("❌ Empresa no encontrada: " + empresa));

        long idEmpresa = empresaEntity.getIdEmpresa();
        List<correos> correos = correoRepo.findByIdEmpresa(idEmpresa);

        if (correos.isEmpty())
            throw new RuntimeException("❌ La empresa no tiene correos registrados");

        for (correos correo : correos) {
            enviarCorreoIndividual(
                    correo.getCorreo(),
                    tipoFormulario,
                    nombreCompleto,
                    empresa,
                    fileBytes
            );
        }
    }

    public void enviarFormularioPorCorreo(DtoMasterSaludColectiva dto, byte[] fileBytes) throws Exception {
        enviarFormularioPorCorreo("Salud Colectiva", dto.getNombreCompleto(), fileBytes);
    }

    public void enviarFormularioPorCorreo(DtoMasterSaludVida dto, byte[] fileBytes) throws Exception {
        enviarFormularioPorCorreo("Vida Grupo", dto.getNombreCompleto(), fileBytes);
    }

    public void enviarFormularioPorCorreo(DtoMasterSaludVida dto, byte[] fileBytes, String empresa) throws Exception {
        enviarFormularioPorCorreo("Vida Grupo", dto.getNombreCompleto(), fileBytes, empresa);
    }
}