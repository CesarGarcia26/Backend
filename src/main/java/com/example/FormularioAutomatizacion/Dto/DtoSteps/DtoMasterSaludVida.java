package com.example.FormularioAutomatizacion.Dto.DtoSteps;

import java.util.List;
import java.util.Map;

public class DtoMasterSaludVida {
    // STEP 1
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombreCompleto;
    private String sexo;
    private String fechaNacimiento;
    private String peso;
    private String estatura;
    private String departamento;
    private String ciudadCorrespondencia;
    private String telefono;
    private String celular;
    private String direccion;
    private String correo;

    // STEP 2
    private String empresa;
    private double salario;
    private double valorAseguradoTotal;
    private String coberturaVidaTier;
    private String valorFinal_CyP;

    // STEP 3
    private int cantidadBeneficiarios;
    private List<Map<String, String>> beneficiarios;
    private String acrecimiento;

    // STEP 4
    private int cantidadAsegurados;
    private List<Map<String, String>> asegurados;

    // STEP 5
    private List<Map<String, String>> valoresAsegurados;
    private List<Map<String, Object>> enfermedadesStep5;

    // STEP 6
    private List<Map<String, Object>> enfermedadesStep6;

    // STEP 7
    private List<Map<String, Object>> enfermedadesStep7;

    // STEP 8
    private List<Map<String, Object>> enfermedadesStep8;

    // STEP 9
    private List<Map<String, Object>> enfermedadesStep9;

    // STEP 10
    private List<Map<String, Object>> enfermedadesStep10;

    // STEP 11
    private Map<String, Object> perdidaFuncional;
    private String alcoholismoUltimosCinco;
    private String alcoholismoActual;
    private String consumeDrogas;
    private String otrasEnfermedades;
    private List<Map<String, String>> detalleAlcoholismoUltimosCinco;
    private List<Map<String, String>> detalleAlcoholismoActual;
    private List<Map<String, String>> detalleOtrasEnfermedades;

    // STEP 13 - VACUNACIÓN
    private String vacunadoCovid;
    private List<Map<String, Object>> vacunadoAsegurados;
    private String autorizacionTratamiento;
    private String firma;

    // ================= GETTERS Y SETTERS =================

    // STEP 1
    public String getTipoIdentificacion() { return tipoIdentificacion; }
    public void setTipoIdentificacion(String tipoIdentificacion) { this.tipoIdentificacion = tipoIdentificacion; }

    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { this.numeroIdentificacion = numeroIdentificacion; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getPeso() { return peso; }
    public void setPeso(String peso) { this.peso = peso; }

    public String getEstatura() { return estatura; }
    public void setEstatura(String estatura) { this.estatura = estatura; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getCiudadCorrespondencia() { return ciudadCorrespondencia; }
    public void setCiudadCorrespondencia(String ciudadCorrespondencia) { this.ciudadCorrespondencia = ciudadCorrespondencia; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    // STEP 2
    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    public double getValorAseguradoTotal() { return valorAseguradoTotal; }
    public void setValorAseguradoTotal(double valorAseguradoTotal) { this.valorAseguradoTotal = valorAseguradoTotal; }

    public String getCoberturaVidaTier() { return coberturaVidaTier; }
    public void setCoberturaVidaTier(String coberturaVidaTier) { this.coberturaVidaTier = coberturaVidaTier; }

    public String getValorFinal_CyP(){return valorFinal_CyP; }
    public void setValorFinal_CyP(String valorFinal_CyP){this.valorFinal_CyP=valorFinal_CyP;}
    // STEP 3
    public int getCantidadBeneficiarios() { return cantidadBeneficiarios; }
    public void setCantidadBeneficiarios(int cantidadBeneficiarios) { this.cantidadBeneficiarios = cantidadBeneficiarios; }

    public List<Map<String, String>> getBeneficiarios() { return beneficiarios; }
    public void setBeneficiarios(List<Map<String, String>> beneficiarios) { this.beneficiarios = beneficiarios; }

    public String getAcrecimiento() { return acrecimiento; }
    public void setAcrecimiento(String acrecimiento) { this.acrecimiento = acrecimiento; }

    // STEP 4
    public int getCantidadAsegurados() { return cantidadAsegurados; }
    public void setCantidadAsegurados(int cantidadAsegurados) { this.cantidadAsegurados = cantidadAsegurados; }

    public List<Map<String, String>> getAsegurados() { return asegurados; }
    public void setAsegurados(List<Map<String, String>> asegurados) { this.asegurados = asegurados; }

    // STEP 5
    public List<Map<String, String>> getValoresAsegurados() { return valoresAsegurados; }
    public void setValoresAsegurados(List<Map<String, String>> valoresAsegurados) { this.valoresAsegurados = valoresAsegurados; }

    public List<Map<String, Object>> getEnfermedadesStep5() { return enfermedadesStep5; }
    public void setEnfermedadesStep5(List<Map<String, Object>> enfermedadesStep5) { this.enfermedadesStep5 = enfermedadesStep5; }

    // STEP 6
    public List<Map<String, Object>> getEnfermedadesStep6() { return enfermedadesStep6; }
    public void setEnfermedadesStep6(List<Map<String, Object>> enfermedadesStep6) { this.enfermedadesStep6 = enfermedadesStep6; }

    // STEP 7
    public List<Map<String, Object>> getEnfermedadesStep7() { return enfermedadesStep7; }
    public void setEnfermedadesStep7(List<Map<String, Object>> enfermedadesStep7) { this.enfermedadesStep7 = enfermedadesStep7; }

    // STEP 8
    public List<Map<String, Object>> getEnfermedadesStep8() { return enfermedadesStep8; }
    public void setEnfermedadesStep8(List<Map<String, Object>> enfermedadesStep8) { this.enfermedadesStep8 = enfermedadesStep8; }

    // STEP 9
    public List<Map<String, Object>> getEnfermedadesStep9() { return enfermedadesStep9; }
    public void setEnfermedadesStep9(List<Map<String, Object>> enfermedadesStep9) { this.enfermedadesStep9 = enfermedadesStep9; }

    // STEP 10
    public List<Map<String, Object>> getEnfermedadesStep10() { return enfermedadesStep10; }
    public void setEnfermedadesStep10(List<Map<String, Object>> enfermedadesStep10) { this.enfermedadesStep10 = enfermedadesStep10; }

    // STEP 11
    public Map<String, Object> getPerdidaFuncional() { return perdidaFuncional; }
    public void setPerdidaFuncional(Map<String, Object> perdidaFuncional) { this.perdidaFuncional = perdidaFuncional; }

    public String getAlcoholismoUltimosCinco() { return alcoholismoUltimosCinco; }
    public void setAlcoholismoUltimosCinco(String alcoholismoUltimosCinco) { this.alcoholismoUltimosCinco = alcoholismoUltimosCinco; }

    public String getAlcoholismoActual() { return alcoholismoActual; }
    public void setAlcoholismoActual(String alcoholismoActual) { this.alcoholismoActual = alcoholismoActual; }

    public String getConsumeDrogas() { return consumeDrogas; }
    public void setConsumeDrogas(String consumeDrogas) { this.consumeDrogas = consumeDrogas; }

    public String getOtrasEnfermedades() {return otrasEnfermedades;}
    public void setOtrasEnfermedades(String otrasEnfermedades) {
        this.otrasEnfermedades = otrasEnfermedades;
    }

    public List<Map<String, String>> getDetalleOtrasEnfermedades() {
        return detalleOtrasEnfermedades;
    }
    public void setDetalleOtrasEnfermedades(List<Map<String, String>> detalleOtrasEnfermedades) {
        this.detalleOtrasEnfermedades = detalleOtrasEnfermedades;
    }
    public List<Map<String, String>> getDetalleAlcoholismoUltimosCinco() {
        return detalleAlcoholismoUltimosCinco;
    }

    public void setDetalleAlcoholismoUltimosCinco(List<Map<String, String>> detalleAlcoholismoUltimosCinco) {
        this.detalleAlcoholismoUltimosCinco = detalleAlcoholismoUltimosCinco;
    }

    public List<Map<String, String>> getDetalleAlcoholismoActual() {
        return detalleAlcoholismoActual;
    }

    public void setDetalleAlcoholismoActual(List<Map<String, String>> detalleAlcoholismoActual) {
        this.detalleAlcoholismoActual = detalleAlcoholismoActual;
    }

    // STEP 13 - VACUNACIÓN
    public String getVacunadoCovid() { return vacunadoCovid; }
    public void setVacunadoCovid(String vacunadoCovid) { this.vacunadoCovid = vacunadoCovid; }

    public List<Map<String, Object>> getVacunadoAsegurados() { return vacunadoAsegurados; }
    public void setVacunadoAsegurados(List<Map<String, Object>> vacunadoAsegurados) { this.vacunadoAsegurados = vacunadoAsegurados; }

    public String getAutorizacionTratamiento() { return autorizacionTratamiento; }
    public void setAutorizacionTratamiento(String autorizacionTratamiento) { this.autorizacionTratamiento = autorizacionTratamiento; }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

}