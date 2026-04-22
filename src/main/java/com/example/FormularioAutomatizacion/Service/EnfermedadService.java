package com.example.FormularioAutomatizacion.Service;

import com.example.FormularioAutomatizacion.Dto.EnfermedaDto;
import com.example.FormularioAutomatizacion.Entity.EntityEnfermedad;
import com.example.FormularioAutomatizacion.repository.EnfermedadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnfermedadService {

    private final EnfermedadRepository enfermedadRepository;

    public EnfermedadService(EnfermedadRepository enfermedadRepository) {
        this.enfermedadRepository = enfermedadRepository;
    }

    public List<EnfermedaDto> obtenerEnfermedades() {
        return enfermedadRepository.findByActivoTrue()
                .stream()
                .map(e -> new EnfermedaDto(e.getId(), e.getNombre()))
                .toList();
    }
}
