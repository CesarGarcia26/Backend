package com.example.FormularioAutomatizacion.Dto.DtoSteps;

import java.util.List;
import java.util.Map;

public class DtoMasterSaludColectiva {
    // STEP 1
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombreCompleto;
    private String genero;
    private String estadoCivil;
    private String cantidadHijos;
    private String departamento;
    private String ciudadCorrespondencia;
    private String telefono;
    private String direccion;
    private String tipoDireccion;
    private String fechaNacimiento;
    private String plan;
    private String urgencias;
    private String consulta;

    // STEP 2
    private String solicitantePrincipalSeAgrega;
    private String tipoIdentificacionPrincipal;
    private String numeroIdentificacionPrincipal;
    private String nombresApellidosPrincipal;
    private String parentescoPrincipal;
    private String fechaNacimientoPrincipal;
    private String sexoPrincipal;
    private String estadoCivilPrincipal;
    private String pesoKgPrincipal;
    private String estaturaCmPrincipal;
    private String ocupacionPrincipal;
    private String nombreEpsPrincipal;
    private String tipoSolicitantePrincipal;
    private String valorAseguradoPrincipal;
    private String rentaIdealPrincipal;
    private String emergenciaEmiPrincipal;
    private String cantidadPersonasAdicionales;
    private List<Map<String, String>> personasAdicionales;

    // STEP 3
    private String solicitaContinuidad;
    private String tieneExclusion;
    private String especificacion;
    private List<Map<String, String>> enfermedadesCardiacas;
    private List<Map<String, String>> enfermedadesPulmonares;

    // STEP 4
    private List<Map<String, String>> enfermedadesGastrointestinales;
    private List<Map<String, String>> enfermedadesGenitourinarias;
    private List<Map<String, String>> enfermedadesDiabetes;

    // STEP 5
    private List<Map<String, String>> enfermedadesNeurologicas;
    private List<Map<String, String>> enfermedadesOseas;
    private List<Map<String, String>> enfermedadesOjosPiel;

    // STEP 6
    private String step6SolicitaContinuidad;
    private String step6TieneExclusion;
    private String practicaDeportes;
    private List<Map<String, String>> otrasEnfermedades;
    private List<Map<String, String>> deportesRiesgo;

    // STEP 7
    private String consumeDrogas;
    private List<Map<String, String>> personasDrogas;
    private String embarazo;
    private List<Map<String, String>> personasEmbarazo;
    private String covid;
    private List<Map<String, String>> personasCovid;
    private String fumadorBebidas;
    private List<Map<String, String>> personasFumadorBebidas;

    // STEP 8
    private String mujeres;
    private List<Map<String, String>> mujeresInfo;
    private String historial;
    private List<Map<String, String>> historialFamilia;

    // STEP 10 - FIRMA Y DECLARACIÓN
    private String respuesta;
    private String firma;
    private String archivoFirma;

    //  GETTERS Y SETTERS

    // STEP 1
    public String getTipoIdentificacion() { return tipoIdentificacion; }
    public void setTipoIdentificacion(String tipoIdentificacion) { this.tipoIdentificacion = tipoIdentificacion; }

    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { this.numeroIdentificacion = numeroIdentificacion; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getEstadoCivil() { return estadoCivil; }
    public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }

    public String getCantidadHijos() { return cantidadHijos; }
    public void setCantidadHijos(String cantidadHijos) { this.cantidadHijos = cantidadHijos; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getCiudadCorrespondencia() { return ciudadCorrespondencia; }
    public void setCiudadCorrespondencia(String ciudadCorrespondencia) { this.ciudadCorrespondencia = ciudadCorrespondencia; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTipoDireccion() { return tipoDireccion; }
    public void setTipoDireccion(String tipoDireccion) { this.tipoDireccion = tipoDireccion; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getPlan() { return plan; }
    public void setPlan(String plan) { this.plan = plan; }

    public String getUrgencias() { return urgencias; }
    public void setUrgencias(String urgencias) { this.urgencias = urgencias; }

    public String getConsulta() { return consulta; }
    public void setConsulta(String consulta) { this.consulta = consulta; }

    // STEP 2
    public String getSolicitantePrincipalSeAgrega() { return solicitantePrincipalSeAgrega; }
    public void setSolicitantePrincipalSeAgrega(String solicitantePrincipalSeAgrega) { this.solicitantePrincipalSeAgrega = solicitantePrincipalSeAgrega; }

    public String getTipoIdentificacionPrincipal() { return tipoIdentificacionPrincipal; }
    public void setTipoIdentificacionPrincipal(String tipoIdentificacionPrincipal) { this.tipoIdentificacionPrincipal = tipoIdentificacionPrincipal; }

    public String getNumeroIdentificacionPrincipal() { return numeroIdentificacionPrincipal; }
    public void setNumeroIdentificacionPrincipal(String numeroIdentificacionPrincipal) { this.numeroIdentificacionPrincipal = numeroIdentificacionPrincipal; }

    public String getNombresApellidosPrincipal() { return nombresApellidosPrincipal; }
    public void setNombresApellidosPrincipal(String nombresApellidosPrincipal) { this.nombresApellidosPrincipal = nombresApellidosPrincipal; }

    public String getParentescoPrincipal() { return parentescoPrincipal; }
    public void setParentescoPrincipal(String parentescoPrincipal) { this.parentescoPrincipal = parentescoPrincipal; }

    public String getFechaNacimientoPrincipal() { return fechaNacimientoPrincipal; }
    public void setFechaNacimientoPrincipal(String fechaNacimientoPrincipal) { this.fechaNacimientoPrincipal = fechaNacimientoPrincipal; }

    public String getSexoPrincipal() { return sexoPrincipal; }
    public void setSexoPrincipal(String sexoPrincipal) { this.sexoPrincipal = sexoPrincipal; }

    public String getEstadoCivilPrincipal() { return estadoCivilPrincipal; }
    public void setEstadoCivilPrincipal(String estadoCivilPrincipal) { this.estadoCivilPrincipal = estadoCivilPrincipal; }

    public String getPesoKgPrincipal() { return pesoKgPrincipal; }
    public void setPesoKgPrincipal(String pesoKgPrincipal) { this.pesoKgPrincipal = pesoKgPrincipal; }

    public String getEstaturaCmPrincipal() { return estaturaCmPrincipal; }
    public void setEstaturaCmPrincipal(String estaturaCmPrincipal) { this.estaturaCmPrincipal = estaturaCmPrincipal; }

    public String getOcupacionPrincipal() { return ocupacionPrincipal; }
    public void setOcupacionPrincipal(String ocupacionPrincipal) { this.ocupacionPrincipal = ocupacionPrincipal; }

    public String getNombreEpsPrincipal() { return nombreEpsPrincipal; }
    public void setNombreEpsPrincipal(String nombreEpsPrincipal) { this.nombreEpsPrincipal = nombreEpsPrincipal; }

    public String getTipoSolicitantePrincipal() { return tipoSolicitantePrincipal; }
    public void setTipoSolicitantePrincipal(String tipoSolicitantePrincipal) { this.tipoSolicitantePrincipal = tipoSolicitantePrincipal; }

    public String getValorAseguradoPrincipal() { return valorAseguradoPrincipal; }
    public void setValorAseguradoPrincipal(String valorAseguradoPrincipal) { this.valorAseguradoPrincipal = valorAseguradoPrincipal; }

    public String getRentaIdealPrincipal() { return rentaIdealPrincipal; }
    public void setRentaIdealPrincipal(String rentaIdealPrincipal) { this.rentaIdealPrincipal = rentaIdealPrincipal; }

    public String getEmergenciaEmiPrincipal() { return emergenciaEmiPrincipal; }
    public void setEmergenciaEmiPrincipal(String emergenciaEmiPrincipal) { this.emergenciaEmiPrincipal = emergenciaEmiPrincipal; }

    public String getCantidadPersonasAdicionales() { return cantidadPersonasAdicionales; }
    public void setCantidadPersonasAdicionales(String cantidadPersonasAdicionales) { this.cantidadPersonasAdicionales = cantidadPersonasAdicionales; }

    public List<Map<String, String>> getPersonasAdicionales() { return personasAdicionales; }
    public void setPersonasAdicionales(List<Map<String, String>> personasAdicionales) { this.personasAdicionales = personasAdicionales; }

    // STEP 3
    public String getSolicitaContinuidad() { return solicitaContinuidad; }
    public void setSolicitaContinuidad(String solicitaContinuidad) { this.solicitaContinuidad = solicitaContinuidad; }

    public String getTieneExclusion() { return tieneExclusion; }
    public void setTieneExclusion(String tieneExclusion) { this.tieneExclusion = tieneExclusion; }

    public String getEspecificacion() { return especificacion; }
    public void setEspecificacion(String especificacion) { this.especificacion = especificacion; }

    public List<Map<String, String>> getEnfermedadesCardiacas() { return enfermedadesCardiacas; }
    public void setEnfermedadesCardiacas(List<Map<String, String>> enfermedadesCardiacas) { this.enfermedadesCardiacas = enfermedadesCardiacas; }

    public List<Map<String, String>> getEnfermedadesPulmonares() { return enfermedadesPulmonares; }
    public void setEnfermedadesPulmonares(List<Map<String, String>> enfermedadesPulmonares) { this.enfermedadesPulmonares = enfermedadesPulmonares; }

    // STEP 4
    public List<Map<String, String>> getEnfermedadesGastrointestinales() { return enfermedadesGastrointestinales; }
    public void setEnfermedadesGastrointestinales(List<Map<String, String>> enfermedadesGastrointestinales) { this.enfermedadesGastrointestinales = enfermedadesGastrointestinales; }

    public List<Map<String, String>> getEnfermedadesGenitourinarias() { return enfermedadesGenitourinarias; }
    public void setEnfermedadesGenitourinarias(List<Map<String, String>> enfermedadesGenitourinarias) { this.enfermedadesGenitourinarias = enfermedadesGenitourinarias; }

    public List<Map<String, String>> getEnfermedadesDiabetes() { return enfermedadesDiabetes; }
    public void setEnfermedadesDiabetes(List<Map<String, String>> enfermedadesDiabetes) { this.enfermedadesDiabetes = enfermedadesDiabetes; }

    // STEP 5
    public List<Map<String, String>> getEnfermedadesNeurologicas() { return enfermedadesNeurologicas; }
    public void setEnfermedadesNeurologicas(List<Map<String, String>> enfermedadesNeurologicas) { this.enfermedadesNeurologicas = enfermedadesNeurologicas; }

    public List<Map<String, String>> getEnfermedadesOseas() { return enfermedadesOseas; }
    public void setEnfermedadesOseas(List<Map<String, String>> enfermedadesOseas) { this.enfermedadesOseas = enfermedadesOseas; }

    public List<Map<String, String>> getEnfermedadesOjosPiel() { return enfermedadesOjosPiel; }
    public void setEnfermedadesOjosPiel(List<Map<String, String>> enfermedadesOjosPiel) { this.enfermedadesOjosPiel = enfermedadesOjosPiel; }

    // STEP 6
    public String getStep6SolicitaContinuidad() { return step6SolicitaContinuidad; }
    public void setStep6SolicitaContinuidad(String step6SolicitaContinuidad) { this.step6SolicitaContinuidad = step6SolicitaContinuidad; }

    public String getStep6TieneExclusion() { return step6TieneExclusion; }
    public void setStep6TieneExclusion(String step6TieneExclusion) { this.step6TieneExclusion = step6TieneExclusion; }

    public String getPracticaDeportes() { return practicaDeportes; }
    public void setPracticaDeportes(String practicaDeportes) { this.practicaDeportes = practicaDeportes; }

    public List<Map<String, String>> getOtrasEnfermedades() { return otrasEnfermedades; }
    public void setOtrasEnfermedades(List<Map<String, String>> otrasEnfermedades) { this.otrasEnfermedades = otrasEnfermedades; }

    public List<Map<String, String>> getDeportesRiesgo() { return deportesRiesgo; }
    public void setDeportesRiesgo(List<Map<String, String>> deportesRiesgo) { this.deportesRiesgo = deportesRiesgo; }
    // STEP 7
    public String getConsumeDrogas() { return consumeDrogas; }
    public void setConsumeDrogas(String consumeDrogas) { this.consumeDrogas = consumeDrogas; }

    public List<Map<String, String>> getPersonasDrogas() { return personasDrogas; }
    public void setPersonasDrogas(List<Map<String, String>> personasDrogas) { this.personasDrogas = personasDrogas; }

    public String getEmbarazo() { return embarazo; }
    public void setEmbarazo(String embarazo) { this.embarazo = embarazo; }

    public List<Map<String, String>> getPersonasEmbarazo() { return personasEmbarazo; }
    public void setPersonasEmbarazo(List<Map<String, String>> personasEmbarazo) { this.personasEmbarazo = personasEmbarazo; }

    public String getCovid() { return covid; }
    public void setCovid(String covid) { this.covid = covid; }

    public List<Map<String, String>> getPersonasCovid() { return personasCovid; }
    public void setPersonasCovid(List<Map<String, String>> personasCovid) { this.personasCovid = personasCovid; }

    public List<Map<String, String>> getPersonasFumadorBebidas() { return personasFumadorBebidas; }
    public void setPersonasFumadorBebidas(List<Map<String, String>> personasFumadorBebidas) { this.personasFumadorBebidas = personasFumadorBebidas; }

    public String getFumadorBebidas() { return fumadorBebidas; }
    public void setFumadorBebidas(String fumadorBebidas) { this.fumadorBebidas = fumadorBebidas; }
    // STEP 8
    public String getMujeres() { return mujeres; }
    public void setMujeres(String mujeres) { this.mujeres = mujeres; }

    public List<Map<String, String>> getMujeresInfo() { return mujeresInfo; }
    public void setMujeresInfo(List<Map<String, String>> mujeresInfo) { this.mujeresInfo = mujeresInfo; }

    public String getHistorial() { return historial; }
    public void setHistorial(String historial) { this.historial = historial; }

    public List<Map<String, String>> getHistorialFamilia() { return historialFamilia; }
    public void setHistorialFamilia(List<Map<String, String>> historialFamilia) { this.historialFamilia = historialFamilia; }

    // STEP 10
    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    public String getFirma() { return firma; }
    public void setFirma(String firma) { this.firma = firma; }

    public String getArchivoFirma() { return archivoFirma; }
    public void setArchivoFirma(String archivoFirma) { this.archivoFirma = archivoFirma; }
}
