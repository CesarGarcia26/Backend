package com.example.FormularioAutomatizacion.Service;

import com.example.FormularioAutomatizacion.Dto.CiudadDto;
import com.example.FormularioAutomatizacion.Dto.DepartamentoDto;
import com.example.FormularioAutomatizacion.Dto.EnfermedaDto;
import com.example.FormularioAutomatizacion.repository.CiudadRepository;
import com.example.FormularioAutomatizacion.repository.DepartmentRepository;
import com.example.FormularioAutomatizacion.repository.EnfermedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class iServiceImpl {

    @Autowired
    private DepartmentRepository departamentoRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private EnfermedadRepository enfermedadRepository;

    /* ================= DEPARTAMENTOS ================= */

    public List<DepartamentoDto> obtenerTodosDepartamentos() {
        try {
            return departamentoRepository.findAllByOrderByNombreAsc()
                    .stream()
                    .map(DepartamentoDto::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener departamentos", e);
        }
    }

    public DepartamentoDto obtenerDepartamentoPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        return departamentoRepository.findById(id)
                .map(DepartamentoDto::new)
                .orElseThrow(() ->
                        new RuntimeException("Departamento no encontrado con ID: " + id));
    }

    /* ================= CIUDADES ================= */

    public List<CiudadDto> obtenerCiudadesPorDepartamento(Long departamentoId) {
        if (departamentoId == null) {
            throw new IllegalArgumentException("El ID del departamento no puede ser nulo");
        }

        try {
            return ciudadRepository.findByDepartamentoIdOrderByNombreAsc(departamentoId)
                    .stream()
                    .map(CiudadDto::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error al obtener ciudades para el departamento: " + departamentoId, e);
        }
    }

    /* ================= ENFERMEDADES ================= */

    public List<EnfermedaDto> obtenerEnfermedades() {
        try {
            return enfermedadRepository.findByActivoTrue()
                    .stream()
                    .map(e -> new EnfermedaDto(e.getId(), e.getNombre()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener enfermedades", e);
        }
    }
}
