package com.example.FormularioAutomatizacion.Service;

import com.example.FormularioAutomatizacion.Dto.DtoSteps.DtoMasterSaludColectiva;
import com.example.FormularioAutomatizacion.Entity.EntityInfoEmpresas;
import com.example.FormularioAutomatizacion.Entity.EntityUser;
import com.example.FormularioAutomatizacion.repository.InfoEmpresaRepository;
import com.example.FormularioAutomatizacion.repository.UserRepository;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class iServiceImpleRellenado {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InfoEmpresaRepository infoEmpresaRepository;

    public ResponseEntity<byte[]> fillWordForm(DtoMasterSaludColectiva datos) throws IOException {
        try (InputStream templateStream = new ClassPathResource(
                "word-templates/150238_SOLICITUD_DE_SEGURO_COLECTIVO_DE_SALUD_Editable.docx"
        ).getInputStream();
             XWPFDocument document = new XWPFDocument(templateStream);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // Obtener el usuario autenticado
            String currentUsername = getCurrentUsername();

            // Buscar el usuario y su empresa
            EntityUser user = userRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + currentUsername));

            EntityInfoEmpresas empresa = user.getEmpresa();
            if (empresa == null) {
                throw new RuntimeException("El usuario no tiene una empresa asignada");
            }

            Map<String, String> placeholders = new HashMap<>();

            // Datos del formulario existentes
            placeholders.put("{{tipoiden}}", nullSafe(datos.getTipoIdentificacion()));
            placeholders.put("{{numeroidentificacion________}}", datos.getNumeroIdentificacion());
            placeholders.put("{{nombrecompleto}}", datos.getNombreCompleto());
            placeholders.put("{{numerotelefonico}}", datos.getTelefono());
            placeholders.put("{{direccioncorrespondencia}}", datos.getDireccion());
            placeholders.put("{{cantidaddehijos}}", datos.getCantidadHijos());
            placeholders.put("{{sexo}}", nullSafe(datos.getGenero()));
            placeholders.put("{{ciudadcorrespondencia}}", datos.getCiudadCorrespondencia());
            placeholders.put("{{departamentocorrespondencia}}", datos.getDepartamento());
            placeholders.put("{{plan}}", datos.getPlan());
            placeholders.put("{{fechaingreso}}", datos.getFechaNacimiento());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaCreacion = LocalDateTime.now().format(formatter);
            placeholders.put("{{fechacreacion_______}}", fechaCreacion);
            placeholders.put("{{especifique}}", datos.getEspecificacion());

            // NUEVOS PLACEHOLDERS PARA DATOS DE LA EMPRESA
            placeholders.put("{{nombreempresa}}", nullSafe(empresa.getNombreEmpresa()));
            placeholders.put("{{nit}}", nullSafe(empresa.getNit()));
            placeholders.put("{{codigoasesor}}", nullSafe(empresa.getCodigoAse()));


            // Datos persona principal
            placeholders.put("{{tipoidentificacioP}}", nullSafe(datos.getTipoIdentificacionPrincipal()));
            placeholders.put("{{numeroidentificacionP}}", nullSafe(datos.getNumeroIdentificacionPrincipal()));
            placeholders.put("{{nombrecompletoP}}", nullSafe(datos.getNombresApellidosPrincipal()));
            placeholders.put("{{parentescoP}}", nullSafe(datos.getParentescoPrincipal()));
            placeholders.put("{{fechanacimientoP}}", nullSafe(datos.getFechaNacimientoPrincipal()));
            placeholders.put("{{sexoP}}", nullSafe(datos.getSexoPrincipal()));
            placeholders.put("{{estadocivilP}}", nullSafe(datos.getEstadoCivilPrincipal()));
            placeholders.put("{{pesoP}}", nullSafe(datos.getPesoKgPrincipal()));
            placeholders.put("{{estaturaP}}", nullSafe(datos.getEstaturaCmPrincipal()));
            placeholders.put("{{ocupacionP}}", nullSafe(datos.getOcupacionPrincipal()));
            placeholders.put("{{nombreepsP}}", nullSafe(datos.getNombreEpsPrincipal()));
            placeholders.put("{{estadoCivil}}", nullSafe(datos.getEstadoCivil()));
            placeholders.put("{{tipodireccion}}", nullSafe(datos.getTipoDireccion()));
            placeholders.put("{{firma}}", nullSafe(datos.getFirma()));
            String genero = nullSafe(datos.getGenero());

            // ===============================
            // EXTRAPRIMA O EXCLUSIÓN LEGAL
            // ===============================
            String legal = nullSafe(datos.getTieneExclusion());
            if ("Si".equalsIgnoreCase(legal)) {
                placeholders.put("{extras}", "X");
                placeholders.put("{extran}", "");
            } else if ("No".equalsIgnoreCase(legal)) {
                placeholders.put("{extras}", "");
                placeholders.put("{extran}", "X");
            } else {
                placeholders.put("{extras}", "");
                placeholders.put("{extran}", "");
            }

            // ===============================
            // Personas adicionales
            // ===============================
            final int MAX_PERSONAS = 5;
            List<Map<String, String>> personas = datos.getPersonasAdicionales();
            for (int i = 0; i < MAX_PERSONAS; i++) {
                if (personas != null && i < personas.size()) {
                    Map<String, String> persona = personas.get(i);
                    placeholders.put("{{tipoidentificacio" + i + "}}", nullSafe(persona.get("tipoIdentificacion")));
                    placeholders.put("{{numeroidentificacion" + i + "}}", nullSafe(persona.get("numeroIdentificacion")));
                    placeholders.put("{{nombrecompleto" + i + "}}", nullSafe(persona.get("nombresApellidos")));
                    placeholders.put("{{parentesco" + i + "}}", nullSafe(persona.get("parentesco")));
                    placeholders.put("{{fechanacimiento" + i + "}}", nullSafe(persona.get("fechaNacimiento")));
                    placeholders.put("{{sexo" + i + "}}", nullSafe(persona.get("sexo")));
                    placeholders.put("{{estadocivil" + i + "}}", nullSafe(persona.get("estadoCivil")));
                    placeholders.put("{{peso" + i + "}}", nullSafe(persona.get("pesoKg")));
                    placeholders.put("{{estatura" + i + "}}", nullSafe(persona.get("estaturaCm")));
                    placeholders.put("{{ocupacion" + i + "}}", nullSafe(persona.get("ocupacion")));
                    placeholders.put("{{nombreeps" + i + "}}", nullSafe(persona.get("nombreEps")));
                } else {
                    placeholders.put("{{tipoidentificacio" + i + "}}", "");
                    placeholders.put("{{numeroidentificacion" + i + "}}", "");
                    placeholders.put("{{nombrecompleto" + i + "}}", "");
                    placeholders.put("{{parentesco" + i + "}}", "");
                    placeholders.put("{{fechanacimiento" + i + "}}", "");
                    placeholders.put("{{sexo" + i + "}}", "");
                    placeholders.put("{{estadocivil" + i + "}}", "");
                    placeholders.put("{{peso" + i + "}}", "");
                    placeholders.put("{{estatura" + i + "}}", "");
                    placeholders.put("{{ocupacion" + i + "}}", "");
                    placeholders.put("{{nombreeps" + i + "}}", "");
                }
            }

            // ===============================
            // Mujeres solicitantes
            // ===============================
            final int MAX_MUJERES = 5;
            List<Map<String, String>> mujeresInfo = datos.getMujeresInfo();
            if (mujeresInfo == null) mujeresInfo = new ArrayList<>();

// Inicializar placeholders vacíos
            for (int i = 0; i < MAX_MUJERES; i++) {
                placeholders.put("{{mujer" + i + "}}", "");
                placeholders.put("{{medicoM" + i + "}}", "");
                placeholders.put("{{centroM" + i + "}}", "");
                placeholders.put("{{resultadoM" + i + "}}", "");
            }

            placeholders.put("{{fecha1}}", "");
            placeholders.put("{{fecha2}}", "");

            int contadorCitologiasSi = 0;

            for (int i = 0; i < MAX_MUJERES; i++) {
                if (i < mujeresInfo.size()) {
                    Map<String, String> mujer = mujeresInfo.get(i);

                    String nombre = nullSafe(mujer.get("nombreSolicitantem"));
                    String citologia = nullSafe(mujer.get("citologia"));
                    String fecha = nullSafe(mujer.get("fecha"));
                    String medico = nullSafe(mujer.get("medicoTratante"));
                    String centro = nullSafe(mujer.get("centroMedico"));
                    String resultado = nullSafe(mujer.get("resultados"));

                    if (citologia.equalsIgnoreCase("si")) citologia = "Sí";
                    else if (citologia.equalsIgnoreCase("no")) citologia = "No";

                    // Manejo especial de fechas (igual que ya tienes)
                    if ("Sí".equals(citologia)) {
                        if (contadorCitologiasSi == 0 && !fecha.isEmpty()) {
                            placeholders.put("{{fecha1}}", fecha);
                            contadorCitologiasSi++;
                        } else if (contadorCitologiasSi == 1 && !fecha.isEmpty()) {
                            placeholders.put("{{fecha2}}", fecha);
                            contadorCitologiasSi++;
                        }
                    }

                    // Texto principal mujer
                    String texto = nombre + ": " + citologia;
                    placeholders.put("{{mujer" + i + "}}", nombre); //texto);

                    // 🆕 Nuevos campos

                    placeholders.put("{{medicoM" + i + "}}", medico);
                    placeholders.put("{{centroM" + i + "}}", centro);
                    placeholders.put("{{resultadoM" + i + "}}", resultado);
                }
            }

            // Manejo de urgencias
            String urgencias = nullSafe(datos.getUrgencias());
            if ("si".equalsIgnoreCase(urgencias)) {
                placeholders.put("{su}", "X");
                placeholders.put("{nu}", "");
            } else if ("no".equalsIgnoreCase(urgencias)) {
                placeholders.put("{su}", "");
                placeholders.put("{nu}", "X");
            } else {
                placeholders.put("{su}", "");
                placeholders.put("{nu}", "");
            }

            // ===============================
            // HISTORIAL FAMILIAR
            // ===============================
            final int MAX_FAMILIA = 4;
            List<Map<String, String>> personass = datos.getHistorialFamilia();
            if (personass == null) personass = new ArrayList<>();

            boolean tieneHistorialFamiliar = false;
            for (int i = 0; i < MAX_FAMILIA; i++) {
                if (i < personass.size()) {
                    Map<String, String> persona = personass.get(i);
                    placeholders.put("{{paren" + i + "}}", nullSafe(persona.get("parentesco")));
                    placeholders.put("{{numasegurado" + i + "}}", nullSafe(persona.get("numero")));
                    placeholders.put("{{enfermedad" + i + "}}", nullSafe(persona.get("enfermedad")));
                    placeholders.put("{{edadase" + i + "}}", nullSafe(persona.get("edadDiagnostico")));
                    placeholders.put("{{causamuerte" + i + "}}", nullSafe(persona.get("causaMuerte")));
                    placeholders.put("{{edadmu" + i + "}}", nullSafe(persona.get("edadMuerte")));
                    tieneHistorialFamiliar = true;
                } else {
                    placeholders.put("{{paren" + i + "}}", "");
                    placeholders.put("{{numasegurado" + i + "}}", "");
                    placeholders.put("{{enfermedad" + i + "}}", "");
                    placeholders.put("{{edadase" + i + "}}", "");
                    placeholders.put("{{causamuerte" + i + "}}", "");
                    placeholders.put("{{edadmu" + i + "}}", "");
                }
            }

            // Marcar SI/NO historial familiar
            if (tieneHistorialFamiliar) {
                placeholders.put("{mi}", "X");
                placeholders.put("{fa}", "");
            } else {
                placeholders.put("{mi}", "");
                placeholders.put("{fa}", "X");
            }

            // ===============================
            // EMBARAZO
            // ===============================
            final int MAX_EMB = 4;
            List<Map<String, String>> embarazo = datos.getPersonasEmbarazo();
            if (embarazo == null) embarazo = new ArrayList<>();

            boolean tieneEmbarazo = false;
            for (int i = 0; i < MAX_EMB; i++) {
                if (i < embarazo.size()) {
                    String nombreEmb = nullSafe(embarazo.get(i).get("nombre"));
                    placeholders.put("{{nombreem" + i + "}}", nombreEmb);
                    if (!nombreEmb.isEmpty()) {
                        tieneEmbarazo = true;
                    }
                } else {
                    placeholders.put("{{nombreem" + i + "}}", "");
                }
            }

            // Marcar SI/NO embarazo
            if (tieneEmbarazo) {
                placeholders.put("{em}", "X");
                placeholders.put("{ba}", "");
            } else {
                placeholders.put("{em}", "");
                placeholders.put("{ba}", "X");
            }

            // ===============================
            // DROGAS (MARIHUANA)
            // ===============================
            final int MAX_DROGA = 4;
            List<Map<String, String>> droga = datos.getPersonasDrogas();
            if (droga == null) droga = new ArrayList<>();

            boolean tieneDrogas = false;
            for (int i = 0; i < MAX_DROGA; i++) {
                if (i < droga.size()) {
                    String nombreDroga = nullSafe(droga.get(i).get("nombre"));
                    placeholders.put("{{cunsur" + i + "}}", nombreDroga);
                    if (!nombreDroga.isEmpty()) {
                        tieneDrogas = true;
                    }
                } else {
                    placeholders.put("{{cunsur" + i + "}}", "");
                }
            }

            // Marcar SI/NO drogas
            if (tieneDrogas) {
                placeholders.put("{mar}", "X");
                placeholders.put("{mas}", "");
            } else {
                placeholders.put("{mar}", "");
                placeholders.put("{mas}", "X");
            }

            // ===============================
            // COVID
            // ===============================
            final int MAX_COVID = 3;
            List<Map<String, String>> covid = datos.getPersonasCovid();
            if (covid == null) covid = new ArrayList<>();

            boolean tieneCovid = false;
            for (int i = 0; i < MAX_COVID; i++) {
                if (i < covid.size()) {
                    String covidInfo = nullSafe(covid.get(i).get("requerioUCI"));
                    placeholders.put("{{covidnumer" + i + "}}", covidInfo);
                    if (!covidInfo.isEmpty()) {
                        tieneCovid = true;
                    }
                } else {
                    placeholders.put("{{covidnumer" + i + "}}", "");
                }
            }

            // Marcar SI/NO covid
            if (tieneCovid) {
                placeholders.put("{co}", "X");
                placeholders.put("{vi}", "");
            } else {
                placeholders.put("{co}", "");
                placeholders.put("{vi}", "X");
            }

// ===============================
// FUMADOR O BEBIDAS EMBRIAGANTES
// ===============================
            final int MAX_FUMBE = 5;
            List<Map<String, String>> fumbe = datos.getPersonasFumadorBebidas();
            if (fumbe == null) fumbe = new ArrayList<>();

            boolean tieneFumadorBebidas = false;

            for (int i = 0; i < MAX_FUMBE; i++) {
                if (i < fumbe.size()) {

                    Map<String, String> persona = fumbe.get(i);

                    String nombre = nullSafe(persona.get("nombre"));
                    String cantidad = nullSafe(persona.get("cantidad"));
                    String frecuencia = nullSafe(persona.get("frecuencia"));

                    placeholders.put("{{nf" + i + "}}", nombre);
                    placeholders.put("{{cf" + i + "}}", cantidad);
                    placeholders.put("{{ff" + i + "}}", frecuencia);

                    if (!nombre.isEmpty() || !cantidad.isEmpty() || !frecuencia.isEmpty()) {
                        tieneFumadorBebidas = true;
                    }

                } else {
                    placeholders.put("{{nf" + i + "}}", "");
                    placeholders.put("{{cf" + i + "}}", "");
                    placeholders.put("{{ff" + i + "}}", "");
                }
            }

// Marcar SI / NO fumador o bebidas
            if (tieneFumadorBebidas) {
                placeholders.put("{fum}", "X");
                placeholders.put("{fm}", "");
            } else {
                placeholders.put("{fum}", "");
                placeholders.put("{fm}", "X");
            }


            // Manejo de respuesta
            String respuesta = nullSafe(datos.getRespuesta());
            if ("SI".equalsIgnoreCase(respuesta)) {
                placeholders.put("{sid}", "X");
                placeholders.put("{nod}", "");
            } else if ("no".equalsIgnoreCase(respuesta)) {
                placeholders.put("{sid}", "");
                placeholders.put("{nod}", "X");
            } else {
                placeholders.put("{sid}", "");
                placeholders.put("{nod}", "");
            }

            // ===============================
            // DEPORTES EXTREMOS
            // ===============================
            final int MAX_DEPORTES = 5;
            List<Map<String, String>> deportes = datos.getDeportesRiesgo();
            if (deportes == null) deportes = new ArrayList<>();

            boolean tieneDeportes = false;

            for (int i = 0; i < MAX_DEPORTES; i++) {

                if (i < deportes.size()) {

                    Map<String, String> deporte = deportes.get(i);

                    String deporteQuien = nullSafe(deporte.get("numeroSolicitante"));
                    String deporteNombre = nullSafe(deporte.get("deporte"));
                    String frecuencia = nullSafe(deporte.get("frecuencia"));

                    placeholders.put("{{deporteQuien" + i + "}}", deporteQuien);
                    placeholders.put("{{deporte" + i + "}}", deporteNombre);
                    placeholders.put("{{frecuencia" + i + "}}", frecuencia);

                    if (!deporteQuien.isEmpty() || !deporteNombre.isEmpty() || !frecuencia.isEmpty()) {
                        tieneDeportes = true;
                    }

                } else {
                    // Si no hay más deportes rellenas con vacío igual que antes
                    placeholders.put("{{deporteQuien" + i + "}}", "");
                    placeholders.put("{{deporte" + i + "}}", "");
                    placeholders.put("{{frecuencia" + i + "}}", "");
                }
            }

            // Marcar SI/NO deportes extremos
            if (tieneDeportes) {
                placeholders.put("{dep}", "X");
                placeholders.put("{dp}", "");
            } else {
                placeholders.put("{dep}", "");
                placeholders.put("{dp}", "X");
            }

            final int MAX_SOL = 5;

            mapSolicitantePlaceholders(placeholders, datos.getEnfermedadesCardiacas(), "c", MAX_SOL);
            mapSolicitantePlaceholders(placeholders, datos.getEnfermedadesPulmonares(), "p", MAX_SOL);
            mapSolicitantePlaceholders(placeholders, datos.getEnfermedadesGastrointestinales(), "g", MAX_SOL);
            mapSolicitantePlaceholders(placeholders, datos.getEnfermedadesGenitourinarias(), "ñ", MAX_SOL);
            mapSolicitantePlaceholders(placeholders, datos.getEnfermedadesDiabetes(), "d", MAX_SOL);
            mapSolicitantePlaceholders(placeholders, datos.getEnfermedadesNeurologicas(), "n", MAX_SOL);
            mapSolicitantePlaceholders(placeholders, datos.getEnfermedadesOseas(), "o", MAX_SOL);
            mapSolicitantePlaceholders(placeholders, datos.getEnfermedadesOjosPiel(), "a", MAX_SOL);
            mapSolicitantePlaceholders(placeholders, datos.getOtrasEnfermedades(), "x", MAX_SOL);

            // ===============================
            // Doctor y fecha para enfermedades
            // ===============================
            final int MAX_ENF = 5;
            mapEnfermedades(placeholders, datos.getEnfermedadesCardiacas(), "cenfer", "fc", MAX_ENF);
            mapEnfermedades(placeholders, datos.getEnfermedadesPulmonares(), "penfer", "fp", MAX_ENF);
            mapEnfermedades(placeholders, datos.getEnfermedadesGastrointestinales(), "genfer", "fg", MAX_ENF);
            mapEnfermedades(placeholders, datos.getEnfermedadesGenitourinarias(), "geenfer", "fge", MAX_ENF);
            mapEnfermedades(placeholders, datos.getEnfermedadesDiabetes(), "denfer", "fd", MAX_ENF);
            mapEnfermedades(placeholders, datos.getEnfermedadesNeurologicas(), "nenfer", "fn", MAX_ENF);
            mapEnfermedades(placeholders, datos.getEnfermedadesOseas(), "oenfer", "fo", MAX_ENF);
            mapEnfermedades(placeholders, datos.getEnfermedadesOjosPiel(), "aenfer", "foa", MAX_ENF);
            mapEnfermedades(placeholders, datos.getOtrasEnfermedades(), "saenfer", "fs", MAX_ENF);


            // ===============================
            // Reemplazo placeholders
            // ===============================
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

            // Guardar archivo
            document.write(outputStream);
            byte[] fileBytes = outputStream.toByteArray();

            String userHome = System.getProperty("user.home");
            String desktopPath = userHome + File.separator + "Desktop" + File.separator + "docs";
            File folder = new File(desktopPath);
            if (!folder.exists()) folder.mkdirs();

            File outputFile = new File(folder, "formulario_generado.docx");
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(fileBytes);
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"formulario.docx\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileBytes);
        }
    }


    // Obtiene el username del usuario autenticado actual
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new RuntimeException("No hay usuario autenticado");
    }

    private void mapSolicitantePlaceholders(Map<String, String> placeholders,
                                            List<Map<String, String>> enfermedades,
                                            String prefix,
                                            int max) {
        if (enfermedades == null) enfermedades = new ArrayList<>();

        for (int i = 0; i < max; i++) {
            String key = "{" + prefix + i + "}";
            if (i < enfermedades.size() && enfermedades.get(i) != null) {
                String numeroSolicitante = enfermedades.get(i).get("numeroSolicitante");
                placeholders.put(key, nullSafe(numeroSolicitante));
            } else {
                placeholders.put(key, "");
            }
        }
    }

    private void mapEnfermedades(Map<String, String> placeholders,
                                 List<Map<String, String>> enfermedades,
                                 String doctorPrefix,
                                 String fechaPrefix,
                                 int max) {
        if (enfermedades == null) enfermedades = new ArrayList<>();

        for (int i = 0; i < max; i++) {
            if (i < enfermedades.size()) {
                Map<String, String> enfermedad = enfermedades.get(i);
                placeholders.put("{{" + doctorPrefix + i + "}}", nullSafe(enfermedad.get("doctor")));
                placeholders.put("{" + fechaPrefix + i + "}", nullSafe(enfermedad.get("fecha")));
            } else {
                placeholders.put("{{" + doctorPrefix + i + "}}", "");
                placeholders.put("{" + fechaPrefix + i + "}", "");
            }
        }
    }

    private void replacePlaceholders(XWPFParagraph paragraph, Map<String, String> placeholders) {
        StringBuilder paragraphText = new StringBuilder();
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text != null) paragraphText.append(text);
        }

        String replacedText = paragraphText.toString();

        if (replacedText.contains("{{firma}}")) {
            clearRuns(paragraph);
            String firmaBase64 = placeholders.get("{{firma}}");
            if (firmaBase64 != null && !firmaBase64.isEmpty()) {
                try {
                    if (firmaBase64.contains(",")) firmaBase64 = firmaBase64.split(",")[1];
                    byte[] imageBytes = Base64.getDecoder().decode(firmaBase64);
                    XWPFRun run = paragraph.createRun();
                    try (InputStream imageStream = new ByteArrayInputStream(imageBytes)) {
                        run.addPicture(imageStream,
                                Document.PICTURE_TYPE_PNG,
                                "firma.png",
                                Units.toEMU(70),
                                Units.toEMU(30));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    XWPFRun errorRun = paragraph.createRun();
                    errorRun.setText("");
                }
            }
            return;
        }

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            replacedText = replacedText.replace(entry.getKey(),
                    entry.getValue() != null ? entry.getValue() : "");
        }

        if (!replacedText.equals(paragraphText.toString())) {
            clearRuns(paragraph);
            XWPFRun newRun = paragraph.createRun();

            // Si el texto es solo "X" o "x", usar tamaño 4, sino tamaño 6
            if ("X".equals(replacedText.trim()) || "X".equals(replacedText.trim())) {
                newRun.setFontSize(4);
            } else {
                newRun.setFontSize(5);
            }

            newRun.setText(replacedText, 0);
        }
    }

    private void clearRuns(XWPFParagraph paragraph) {
        int runs = paragraph.getRuns().size();
        for (int i = runs - 1; i >= 0; i--) {
            paragraph.removeRun(i);
        }
    }

    private String nullSafe(String value) {
        return value != null ? value : "";
    }
}