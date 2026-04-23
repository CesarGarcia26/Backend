package com.example.FormularioAutomatizacion.Service;

import com.example.FormularioAutomatizacion.Dto.DtoSteps.DtoMasterSaludVida;
import com.example.FormularioAutomatizacion.Entity.EntityInfoEmpresas;
import com.example.FormularioAutomatizacion.Entity.EntityUser;
import com.example.FormularioAutomatizacion.repository.InfoEmpresaRepository;
import com.example.FormularioAutomatizacion.repository.UserRepository;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

import org.apache.poi.util.Units;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.*;


@Service
public class iServiceImpleSeguroVida {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InfoEmpresaRepository infoEmpresaRepository;

    public ResponseEntity<byte[]> fillWordFormVida(DtoMasterSaludVida datos) throws IOException {
        try (InputStream templateStream = new ClassPathResource(
                "word-templates/150605_Vida_Grupo_Editable.docx"
        ).getInputStream();
             XWPFDocument document = new XWPFDocument(templateStream);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            //Usuario autenticado
            String currentUsername = getCurrentUsername();

            EntityUser user = userRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + currentUsername));

            EntityInfoEmpresas empresa = user.getEmpresa();
            if (empresa == null) {
                throw new RuntimeException("El usuario no tiene una empresa asignada");
            }
            System.out.println(datos.getAlcoholismoActual());
            System.out.println(datos.getDetalleAlcoholismoActual());


            Map<String, String> placeholders = new HashMap<>();

            // STEP 1 - Datos personales
            placeholders.put("{{tipoidenti}}", nullSafe(datos.getTipoIdentificacion()));
            placeholders.put("{{numeroidentificion}}", nullSafe(datos.getNumeroIdentificacion()));
            placeholders.put("{{nombrecompleto}}", nullSafe(datos.getNombreCompleto()));
            placeholders.put("{{sexoty}}", nullSafe(datos.getSexo()));
            placeholders.put("{{fechanacimiento}}", nullSafe(datos.getFechaNacimiento()));
            placeholders.put("{{numero}}", nullSafe(datos.getTelefono()));
            placeholders.put("{{peso}}", nullSafe(datos.getPeso()));
            placeholders.put("{{altura}}", nullSafe(datos.getEstatura()));
            placeholders.put("{{ciudad}}", nullSafe(datos.getCiudadCorrespondencia()));
            placeholders.put("{{departamento}}", nullSafe(datos.getDepartamento()));
            placeholders.put("{{celular}}", nullSafe(datos.getCelular()));
            placeholders.put("{{ubicacion}}", nullSafe(datos.getDireccion()));
            placeholders.put("{{correo}}", nullSafe(datos.getCorreo()));

            //Datos empresa
            placeholders.put("{{nombreempresa}}", nullSafe(empresa.getNombreEmpresa()));
            placeholders.put("{{nit}}", nullSafe(empresa.getNit()));
            placeholders.put("{{valorfinal}}", formatearMoneda(datos.getValorFinal_CyP()));


// =====================================
// 🔹 COMBINACIÓN DE TODAS LAS ENFERMEDADES Y ALCOHOLISMO EN UNA SOLA LISTA
// =====================================

// Lista de todos los steps de enfermedades
            List<List<Map<String, Object>>> enfermedadesPorStep = Arrays.asList(
                    datos.getEnfermedadesStep5(),
                    datos.getEnfermedadesStep6(),
                    datos.getEnfermedadesStep7(),
                    datos.getEnfermedadesStep8(),
                    datos.getEnfermedadesStep9(),
                    datos.getEnfermedadesStep10()
            );

// Lista combinada de todas las enfermedades y alcoholismo
            List<Map<String, String>> detalleEnfermedadesCombinadas = new ArrayList<>();

// 1️⃣ Enfermedades steps 5-10
            for (List<Map<String, Object>> enfermedadesStep : enfermedadesPorStep) {
                if (enfermedadesStep == null) continue;

                for (Map<String, Object> enfermedad : enfermedadesStep) {
                    if (enfermedad == null) continue;

                    String nombre = Objects.toString(enfermedad.get("nombre"), "").trim();
                    if (nombre.isEmpty()) continue;

                    Object aseguradosValue = enfermedad.get("asegurados");
                    if (!(aseguradosValue instanceof List<?> listaAsegurados)) continue;

                    for (Object obj : listaAsegurados) {
                        if (!(obj instanceof Map<?, ?> map)) continue;

                        String asegurado = Objects.toString(map.get("asegurado"), "").trim();
                        if (asegurado.isEmpty()) continue;

                        Map<String, String> fila = new HashMap<>();
                        fila.put("numAsegurado", asegurado);
                        fila.put("medico", Objects.toString(map.get("medico"), ""));
                        fila.put("institucion", Objects.toString(map.get("institucion"), ""));
                        fila.put("eps", Objects.toString(map.get("eps"), ""));
                        fila.put("enfermedad", nombre);

                        detalleEnfermedadesCombinadas.add(fila);
                    }
                }
            }

// 2️⃣ Alcoholismo últimos 5 años
            if (datos.getDetalleAlcoholismoUltimosCinco() != null) {
                for (Map<String, String> item : datos.getDetalleAlcoholismoUltimosCinco()) {
                    Map<String, String> fila = new HashMap<>();
                    fila.put("numAsegurado", nullSafe(item.get("asegurado")));
                    fila.put("medico", nullSafe(item.get("medico")));
                    fila.put("institucion", nullSafe(item.get("institucion")));
                    fila.put("eps", nullSafe(item.get("eps")));
                    fila.put("enfermedad", nullSafe(item.get("observacion"))); // ✅ Cambiado
                    detalleEnfermedadesCombinadas.add(fila);
                }
            }

// 3️⃣ Alcoholismo actual
            if (datos.getDetalleAlcoholismoActual() != null) {
                for (Map<String, String> item : datos.getDetalleAlcoholismoActual()) {
                    Map<String, String> fila = new HashMap<>();
                    fila.put("numAsegurado", nullSafe(item.get("asegurado")));
                    fila.put("medico", nullSafe(item.get("medico")));
                    fila.put("institucion", nullSafe(item.get("institucion")));
                    fila.put("eps", nullSafe(item.get("eps")));
                    fila.put("enfermedad", nullSafe(item.get("observacion"))); // ✅ Cambiado
                    detalleEnfermedadesCombinadas.add(fila);
                }
            }

// 🔢 Máximo de filas que soporta el Word
            final int MAX_ENFERMEDADES = 5;
            while (detalleEnfermedadesCombinadas.size() < MAX_ENFERMEDADES) {
                // rellenar con vacíos para no romper placeholders
                Map<String, String> filaVacia = new HashMap<>();
                filaVacia.put("numAsegurado", "");
                filaVacia.put("medico", "");
                filaVacia.put("institucion", "");
                filaVacia.put("eps", "");
                filaVacia.put("enfermedad", "");
                detalleEnfermedadesCombinadas.add(filaVacia);
            }

// =====================================
// 🔹 Reemplazo en placeholders del Word
// =====================================
            for (int i = 0; i < MAX_ENFERMEDADES; i++) {
                Map<String, String> fila = detalleEnfermedadesCombinadas.get(i);
                placeholders.put("{{asegurado" + i + "}}", fila.get("numAsegurado"));
                placeholders.put("{{doctor" + i + "}}", fila.get("medico"));
                placeholders.put("{{institucion" + i + "}}", fila.get("institucion"));
                placeholders.put("{{eps" + i + "}}", fila.get("eps"));
                placeholders.put("{{enfermedad" + i + "}}", fila.get("enfermedad"));
            }


            if ("masculino".equalsIgnoreCase(datos.getSexo())) {
                placeholders.put("{m}", "X");
                placeholders.put("{s}", "");
            } else if ("femenino".equalsIgnoreCase(datos.getSexo())) {
                placeholders.put("{s}", "x");
                placeholders.put("{m}", "");
            } else {
                placeholders.put("{s}", "");
                placeholders.put("{m}", "");
            }
            // Datos de la empresa
            placeholders.put("{{nombreempresa}}", nullSafe(empresa.getNombreEmpresa()));
            placeholders.put("{{nit}}", nullSafe(empresa.getNit()));
            placeholders.put("{{codigoasesor}}", nullSafe(empresa.getCodigoAse()));

            //segundo stepv beneficiarios

            //System.out.println("beneficiarios recibidos:");
            //if (datos.getBeneficiarios() != null) {
            //  for (int i = 0; i < datos.getBeneficiarios().size(); i++) {
            //    System.out.println("Asegurado " + i + ": " + datos.getBeneficiarios().get(i));
            //}
            //}
            final int MAX_PERSONAS = 6;
            List<Map<String, String>> personas = datos.getBeneficiarios();
            for (int i = 0; i < MAX_PERSONAS; i++) {
                if (personas != null && i < personas.size()) {
                    Map<String, String> persona = personas.get(i);
                    placeholders.put("{{ce" + i + "}}", nullSafe(persona.get("tipoIdentificacion")));
                    placeholders.put("{{num" + i + "}}", nullSafe(persona.get("numeroIdentificacion")));
                    placeholders.put("{{beneficiario" + i + "}}", nullSafe(persona.get("nombreCompleto")));
                    placeholders.put("{{porcen" + i + "}}", nullSafe(persona.get("porcentaje")));

                } else {
                    placeholders.put("{{ce" + i + "}}", "");
                    placeholders.put("{{num" + i + "}}", "");
                    placeholders.put("{{beneficiario" + i + "}}", "");
                    placeholders.put("{{porcen" + i + "}}", "");
                }
            }


            //if (datos.getAsegurados() != null) {
            //  for (int i = 0; i < datos.getAsegurados().size(); i++) {
            //    System.out.println("Asegurado " + i + ": " + datos.getAsegurados().get(i));
            // }
            //}
            final int max_grupo = 5;
            List<Map<String, String>> grupo = datos.getAsegurados();
            for (int i = 0; i < max_grupo; i++) {
                if (grupo != null && i < grupo.size()) {
                    Map<String, String> persona = grupo.get(i);
                    placeholders.put("{{gf" + i + "}}", nullSafe(persona.get("tipoIdentificacion")));
                    placeholders.put("{{identificacion" + i + "}}", nullSafe(persona.get("numeroIdentificacion")));
                    placeholders.put("{{nombregf" + i + "}}", nullSafe(persona.get("nombreCompleto")));
                    placeholders.put("{{correogf" + i + "}}", nullSafe(persona.get("correo")));
                    placeholders.put("{{telefonogf" + i + "}}", nullSafe(persona.get("telefono")));
                    placeholders.put("{{nacimintogf" + i + "}}", nullSafe(persona.get("fechaNacimiento")));
                    placeholders.put("{{s" + i + "}}", nullSafe(persona.get("sexo")));
                    placeholders.put("{{k" + i + "}}", nullSafe(persona.get("peso")));
                    placeholders.put("{{h" + i + "}}", nullSafe(persona.get("estatura")));
                    placeholders.put("{{parentescogf" + i + "}}", nullSafe(persona.get("parentesco")));


                } else {
                    placeholders.put("{{gf" + i + "}}", "");
                    placeholders.put("{{identificacion" + i + "}}", "");
                    placeholders.put("{{nombregf" + i + "}}", "");
                    placeholders.put("{{correogf" + i + "}}", "");
                    placeholders.put("{{telefonogf" + i + "}}", "");
                    placeholders.put("{{nacimintogf" + i + "}}", "");
                    placeholders.put("{{s" + i + "}}", "");
                    placeholders.put("{{k" + i + "}}", "");
                    placeholders.put("{{h" + i + "}}", "");
                    placeholders.put("{{parentescogf" + i + "}}", "");
                }
            }
            String respuesta = normalize(datos.getAlcoholismoUltimosCinco());
            if (respuesta.equals("si")) {
                placeholders.put("{alco}", "X");
                placeholders.put("{hol}", "");
            } else if (respuesta.equals("no")) {
                placeholders.put("{alco}", "");
                placeholders.put("{hol}", "X");
            } else {
                placeholders.put("{alco}", "");
                placeholders.put("{hol}", "");
            }

            String reponse = normalize(datos.getAlcoholismoActual());
            if (reponse.equals("si")) {
                placeholders.put("{droga}", "X");
                placeholders.put("{diccio}", "");
            } else if (reponse.equals("no")) {
                placeholders.put("{droga}", "");
                placeholders.put("{diccio}", "X");
            } else {
                placeholders.put("{droga}", "");
                placeholders.put("{diccio}", "");
            }

            String reponsens = normalize(datos.getConsumeDrogas());
            if (reponsens.equals("si")) {
                placeholders.put("{estim}", "X");
                placeholders.put("{ulante}", "");
            } else if (reponsens.equals("no")) {
                placeholders.put("{estim}", "");
                placeholders.put("{ulante}", "X");
            } else {
                placeholders.put("{estim}", "");
                placeholders.put("{ulante}", "");
            }

            String acre = normalize(datos.getAcrecimiento());
            if (acre.equals("si")) {
                placeholders.put("{acre}", "X");
                placeholders.put("{cimi}", "");
            } else if (acre.equals("no")) {
                placeholders.put("{acre}", "");
                placeholders.put("{cimi}", "X");
            } else {
                placeholders.put("{acre}", "");
                placeholders.put("{cimi}", "");
            }

            //valor asegurado grupo familiar

            //if (datos.getValoresAsegurados() != null) {
            //  for (int i = 0; i < datos.getValoresAsegurados().size(); i++) {
            //    System.out.println("Asegurado " + i + ": " + datos.getValoresAsegurados().get(i));
            //}
            // }
            final int max = 5;
            List<Map<String, String>> valor = datos.getValoresAsegurados();
            for (int i = 0; i < max; i++) {
                if (valor != null && i < valor.size()) {
                    Map<String, String> persona = valor.get(i);
                    placeholders.put("{{gfvalorasegurado" + i + "}}", nullSafe(persona.get("valorAsegurado")));


                } else {
                    placeholders.put("{{gfvalorasegurado" + i + "}}", "");

                }
            }

            System.out.println("OTRAS ENFERMEDADES FLAG 👉 " + datos.getOtrasEnfermedades());
            System.out.println("DETALLE OTRAS 👉 " + datos.getDetalleOtrasEnfermedades());

            // ===============================
// STEP 11 - MARCAR X OTRAS ENFERMEDADES
// ===============================
            String otrasResp = normalize(datos.getOtrasEnfermedades());

            if ("si".equals(otrasResp)) {
                placeholders.put("{otras_si}", "X");
                placeholders.put("{otras_no}", "");
            } else if ("no".equals(otrasResp)) {
                placeholders.put("{otras_si}", "");
                placeholders.put("{otras_no}", "X");
            } else {
                placeholders.put("{otras_si}", "");
                placeholders.put("{otras_no}", "");
            }


            // ===============================
// STEP 11 - OTRAS ENFERMEDADES (PLACEHOLDERS LIMPIOS)
// ===============================
            final int MAX_OTRAS_ENF = 5;
            for (int i = 0; i < MAX_OTRAS_ENF; i++) {
                placeholders.put("{{otrasenf_asegurado" + i + "}}", "");
                placeholders.put("{{otrasenf_eps" + i + "}}", ""); // ✅ NUEVO
                placeholders.put("{{otrasenf_medico" + i + "}}", "");
                placeholders.put("{{otrasenf_institucion" + i + "}}", "");
                placeholders.put("{{otrasenf_obs" + i + "}}", "");
            }

            // ===============================
// STEP 11 - OTRAS ENFERMEDADES / CONDICIONES MÉDICAS
// ===============================
            if (datos.getDetalleOtrasEnfermedades() != null
                    && !datos.getDetalleOtrasEnfermedades().isEmpty()) {

                int idx = 0;
                for (Map<String, String> item : datos.getDetalleOtrasEnfermedades()) {
                    if (idx >= MAX_OTRAS_ENF) break;
                    placeholders.put("{{otrasenf_asegurado" + idx + "}}", nullSafe(item.get("asegurado")));
                    placeholders.put("{{otrasenf_eps" + idx + "}}", nullSafe(item.get("eps")));
                    placeholders.put("{{otrasenf_medico" + idx + "}}", nullSafe(item.get("medico")));
                    placeholders.put("{{otrasenf_institucion" + idx + "}}", nullSafe(item.get("institucion")));
                    placeholders.put("{{otrasenf_obs" + idx + "}}", nullSafe(item.get("observacion")));
                    idx++;
                }
            }


            // STEP 13 - Vacunación COVID
            String vacunado = normalize(datos.getVacunadoCovid());

            // 🔒 Validaciones legales obligatorias
            if (datos.getFirma() == null || datos.getFirma().isBlank()) {
                throw new IllegalArgumentException("La firma del solicitante es obligatoria.");
            }

            if (!"si".equalsIgnoreCase(normalize(datos.getAutorizacionTratamiento()))) {
                throw new IllegalArgumentException("Debe aceptar el tratamiento de datos personales.");
            }

            if (vacunado.equals("si")) {
                placeholders.put("{siva}", "X");
                placeholders.put("{nova}", "");

                List<Map<String, Object>> lista = datos.getVacunadoAsegurados();

                for (int i = 0; i < 5; i++) {
                    if (lista != null && i < lista.size()) {
                        Map<String, Object> item = lista.get(i);
                        Object numObj = item.get("numeroAsegurado");
                        String numero = (numObj != null) ? numObj.toString() : "";
                        placeholders.put("{{n" + (i + 1) + "}}", numero); // ✅ AQUÍ
                    } else {
                        placeholders.put("{{n" + (i + 1) + "}}", "");
                    }
                }

            } else if (vacunado.equals("no")) {
                placeholders.put("{siva}", "");
                placeholders.put("{nova}", "X");

                for (int i = 1; i <= 5; i++) {
                    placeholders.put("{{n" + i + "}}", "");
                }
            }

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                replacePlaceholders(paragraph, placeholders);
            }
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            replacePlaceholders(paragraph, placeholders);
                        }
                    }
                }
            }


            try {
                byte[] firmaBytes = decodeBase64Image(datos.getFirma());
                insertSignatureImage(document, "{{FIRMA_SOLICITANTE}}", firmaBytes);
            } catch (Exception e) {
                throw new RuntimeException("Error insertando la firma en el documento", e);
            }


            document.write(outputStream);
            byte[] fileBytes = outputStream.toByteArray();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"formulario_vida.docx\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileBytes);
        }
    }

    private byte[] decodeBase64Image(String base64Image) {
        if (base64Image.contains(",")) {
            base64Image = base64Image.split(",")[1];
        }
        return Base64.getDecoder().decode(base64Image);
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new RuntimeException("No hay usuario autenticado");
    }

    private String formatearMoneda(String valor) {
        if (valor == null || valor.isEmpty()) {
            return "$0";
        }

        try {
            // Limpiar el string de cualquier formato previo (signos de pesos, puntos, comas)
            String valorLimpio = valor.replaceAll("[^\\d]", "");

            // Si está vacío después de limpiar, retornar $0
            if (valorLimpio.isEmpty()) {
                return "$0";
            }

            // Convertir a número
            double numero = Double.parseDouble(valorLimpio);

            // Formatear con punto como separador de miles (estilo colombiano)
            NumberFormat formatoColombia = NumberFormat.getNumberInstance(new Locale("es", "CO"));
            return "$" + formatoColombia.format(numero);

        } catch (NumberFormatException e) {
            System.err.println("Error formateando moneda: " + valor);
            return "$0";
        }
    }

    private String normalize(String value) {
        if (value == null) return "";
        return value
                .trim()
                .replace("í", "i")
                .replace("Í", "I")
                .toLowerCase();
    }

    private void replacePlaceholders(XWPFParagraph paragraph, Map<String, String> placeholders) {
        StringBuilder paragraphText = new StringBuilder();
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text != null) paragraphText.append(text);
        }

        String replacedText = paragraphText.toString();

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            replacedText = replacedText.replace(entry.getKey(),
                    entry.getValue() != null ? entry.getValue() : "");
        }

        if (!replacedText.equals(paragraphText.toString())) {
            clearRuns(paragraph);
            XWPFRun newRun = paragraph.createRun();
            newRun.setFontSize(6);
            newRun.setText(replacedText, 0);
        }
    }

    private void clearRuns(XWPFParagraph paragraph) {
        int runs = paragraph.getRuns().size();
        for (int i = runs - 1; i >= 0; i--) {
            paragraph.removeRun(i);
        }
    }

    private void insertSignatureImage(
            XWPFDocument document,
            String placeholder,
            byte[] imageBytes
    ) throws Exception {

        // 📄 Párrafos normales
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            if (insertIfMatch(paragraph, placeholder, imageBytes)) return;
        }

        // 📊 Tablas
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        if (insertIfMatch(paragraph, placeholder, imageBytes)) return;
                    }
                }
            }
        }
    }

    private boolean insertIfMatch(
            XWPFParagraph paragraph,
            String placeholder,
            byte[] imageBytes
    ) throws Exception {

        String text = paragraph.getText();
        if (text != null && text.contains(placeholder)) {
            clearRuns(paragraph);
            XWPFRun run = paragraph.createRun();
            run.addPicture(
                    new ByteArrayInputStream(imageBytes),
                    Document.PICTURE_TYPE_PNG,
                    "firma.png",
                    Units.toEMU(70),
                    Units.toEMU(30)
            );
            return true;
        }
        return false;
    }


    private String nullSafe(String value) {
        return value != null ? value : "";
    }
}