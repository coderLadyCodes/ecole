package com.samia.ecole.controllers;

import com.samia.ecole.DTOs.RegularUpdatesDTO;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.exceptions.UnauthorizedException;
import com.samia.ecole.services.RegularUpdatesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/updates")
public class RegularUpdatesController {
    private final RegularUpdatesService regularUpdatesService;

    public RegularUpdatesController(RegularUpdatesService regularUpdatesService) {
        this.regularUpdatesService = regularUpdatesService;
    }
    @PostMapping("/{studentId}")
    public RegularUpdatesDTO createRegularUpdates(@RequestBody RegularUpdatesDTO regularUpdatesDTO, @PathVariable(value="studentId") Long studentId) throws UnauthorizedException {
        LocalDate localDate = regularUpdatesDTO.getLocalDate();

        boolean exists = regularUpdatesService.updateExistsForDate(studentId, localDate);
        if (exists) {
            throw new CustomException("vous avez déja mis à jour ces infos", HttpStatus.CONFLICT);
        }
        return regularUpdatesService.createRegularUpdates(regularUpdatesDTO, studentId);
    }
    @GetMapping
    public List<RegularUpdatesDTO> getAllregulardUpdates(){
            return regularUpdatesService.getAllregulardUpdates();
        }
    @GetMapping("regular/{studentId}")
    public List<RegularUpdatesDTO> getAllregulardUpdatesByStudenId(@PathVariable(value = "studentId") Long studentId){
        return regularUpdatesService.getAllregulardUpdatesByStudenId(studentId);
    }
    @PutMapping("/{id}")
    public RegularUpdatesDTO updateRegularUpdates(@PathVariable(value ="id") Long id, @RequestBody RegularUpdatesDTO regularUpdatesDetails){
        return regularUpdatesService.updateRegularUpdates(id, regularUpdatesDetails);
    }
    @GetMapping("/classroom/{classroomId}")
    public List<RegularUpdatesDTO> getAllUpdatesForClassroom(@PathVariable Long classroomId) {
        return regularUpdatesService.getAllUpdatesForClassroom(classroomId);
    }
    @GetMapping("/{id}")
    public RegularUpdatesDTO getRegularUpdatesById(@PathVariable(value = "id") Long id){
        return regularUpdatesService.getRegularUpdatesById(id);
    }
    @GetMapping("/latest/{studentId}")
    public RegularUpdatesDTO getLatestRegularUpdateByStudentId(@PathVariable(value = "studentId") Long studentId){
        return regularUpdatesService.getLatestRegularUpdateByStudentId(studentId);
    }
    @DeleteMapping("/{id}")
    public void deleteRegularUpdates(@PathVariable(value = "id" ) Long id){
        regularUpdatesService.deleteRegularUpdates(id);
    }
}
