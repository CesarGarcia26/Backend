package com.example.FormularioAutomatizacion.Web.Controller;

import com.example.FormularioAutomatizacion.Entity.CoberturaN;
import com.example.FormularioAutomatizacion.Entity.CoberturaPyC;
import com.example.FormularioAutomatizacion.Entity.CoberturaS;
import com.example.FormularioAutomatizacion.Entity.CoberturaPirani;
import com.example.FormularioAutomatizacion.Entity.CoberturaRolparts;
import com.example.FormularioAutomatizacion.Entity.CoberturaGondwana;
import com.example.FormularioAutomatizacion.Entity.CoberturaMaquel;
import com.example.FormularioAutomatizacion.Entity.CoberturaAcrrin;

import com.example.FormularioAutomatizacion.repository.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ubicacion")
public class CoberturaController {

    private final CoberturaPyCRepository pycRepo;
    private final CoberturaSRepository sRepo;
    private final CoberturaNRepository nRepo;
    private final CoberturaPiraniRepository piraniRepo;
    private final CoberturaRolpartsRepository rolpartsRepo;
    private final CoberturaGondwanaRepository gondwanaRepo;
    private final CoberturaMaquelRepository maquelRepo;
    private final CoberturaAcrrinRepository acrrinRepo;// ✅ Nuevo

    public CoberturaController(
            CoberturaPyCRepository pycRepo,
            CoberturaSRepository sRepo,
            CoberturaNRepository nRepo,
            CoberturaPiraniRepository piraniRepo,
            CoberturaRolpartsRepository rolpartsRepo,
            CoberturaGondwanaRepository gondwanaRepo,
            CoberturaMaquelRepository maquelRepo,
            CoberturaAcrrinRepository acrrinRepo
    ) {
        this.pycRepo = pycRepo;
        this.sRepo = sRepo;
        this.nRepo = nRepo;
        this.piraniRepo = piraniRepo;
        this.rolpartsRepo = rolpartsRepo;
        this.gondwanaRepo = gondwanaRepo;
        this.maquelRepo = maquelRepo;
        this.acrrinRepo = acrrinRepo;
    }

    // 🔹 PRAGMA / CEIBA / PERCEPTIO
    @GetMapping("/pyc")
    public List<CoberturaPyC> getCoberturasPyC() {
        return pycRepo.findAll();
    }

    // 🔹 SUSANITA
    @GetMapping("/susanita")
    public List<CoberturaS> getCoberturasSusanita() {
        return sRepo.findAll();
    }

    // 🔹 NUVANT
    @GetMapping("/nuvant")
    public List<CoberturaN> getCoberturasNuvant() {
        return nRepo.findAll();
    }

    // 🔥 PIRANI
    @GetMapping("/pirani")
    public List<CoberturaPirani> getCoberturasPirani() {
        return piraniRepo.findAllByOrderByCoberturaAsc();
    }

    // 🔥 ROLPARTS (NUEVO)
    @GetMapping("/rolparts")
    public List<CoberturaRolparts> getCoberturasRolparts() {
        return rolpartsRepo.findAll();
    }

    // 🔵 GONDWANA (Incluye AYT UMBRIA y GONDWANA SERVICIOS)
    @GetMapping("/gondwana")
    public List<CoberturaGondwana> getCoberturasGondwana() {
        return gondwanaRepo.findAll();
    }

    // MAQUEL
    @GetMapping("/maquel")
    public List<CoberturaMaquel> getCoberturasMaquel() {return maquelRepo.findAll();}

    //ACRRIN
    @GetMapping("/acrrin")
    public List<CoberturaAcrrin> getCoberturasAcrrin() {return acrrinRepo.findAll();}
}
