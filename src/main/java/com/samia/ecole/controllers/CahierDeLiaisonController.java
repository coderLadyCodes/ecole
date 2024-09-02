package com.samia.ecole.controllers;

import com.samia.ecole.DTOs.CahierDeLiaisonDTO;
import com.samia.ecole.exceptions.UnauthorizedException;
import com.samia.ecole.services.CahierDeLiaisonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cahierDeLiaison")
public class CahierDeLiaisonController {
    private final CahierDeLiaisonService cahierDeLiaisonService;
    public CahierDeLiaisonController(CahierDeLiaisonService cahierDeLiaisonService) {
        this.cahierDeLiaisonService = cahierDeLiaisonService;
    }
    @PostMapping("/{studentId}")
    public CahierDeLiaisonDTO createCahierDeLiaison(@RequestBody CahierDeLiaisonDTO cahierDeLiaisonDTO, @PathVariable(value="studentId") Long studentId) throws UnauthorizedException {
        return cahierDeLiaisonService.createCahierDeLiaison(cahierDeLiaisonDTO, studentId);
    }
    @GetMapping("/all")
    public List<CahierDeLiaisonDTO> getAllCahierDeLiaison(){
        return cahierDeLiaisonService.getAllCahierDeLiaison();
    }
    @GetMapping("/{id}")
    public CahierDeLiaisonDTO getCahierDeLiaisonById(@PathVariable(value = "id") Long id){
        return cahierDeLiaisonService.getCahierDeLiaisonById(id);
    }
    @GetMapping("/all/{studentId}")
    public List<CahierDeLiaisonDTO> getAllCahierDeLiaisonByStudentId(@PathVariable(value = "studentId") Long studentId){
        return cahierDeLiaisonService.getAllCahierDeLiaisonByStudentId(studentId);
    }
    @PutMapping("/update/{id}")
    public CahierDeLiaisonDTO updateCahierDeLiaison(@PathVariable(value = "id") Long id, @RequestBody CahierDeLiaisonDTO cahierDeLiaisonDetails){
        return cahierDeLiaisonService.updateCahierDeLiaison(id, cahierDeLiaisonDetails);
    }
    @DeleteMapping("/{id}")
    public void deleteCahierDeLiaison(@PathVariable(value = "id" ) Long id){
        cahierDeLiaisonService.deleteCahierDeLiaison(id);
    }

}
