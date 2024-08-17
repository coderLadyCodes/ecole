package com.samia.ecole.controllers;

import com.samia.ecole.DTOs.RegularUpdatesDTO;
import com.samia.ecole.exceptions.UnauthorizedException;
import com.samia.ecole.services.RegularUpdatesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("updates")
public class RegularUpdatesController {
    private final RegularUpdatesService regularUpdatesService;

    public RegularUpdatesController(RegularUpdatesService regularUpdatesService) {
        this.regularUpdatesService = regularUpdatesService;
    }
    @PostMapping("{studentId}")
    public RegularUpdatesDTO createRegularUpdates(@RequestBody RegularUpdatesDTO regularUpdatesDTO, @PathVariable(value="studentId") Long studentId) throws UnauthorizedException {
        return regularUpdatesService.createRegularUpdates(regularUpdatesDTO, studentId);
    }
    @GetMapping
    public List<RegularUpdatesDTO> getAllregulardUpdates(){
            return regularUpdatesService.getAllregulardUpdates();
        }
    @PutMapping("{id}")
    public RegularUpdatesDTO updateRegularUpdates(@PathVariable(value ="id") Long id, @RequestBody RegularUpdatesDTO regularUpdatesDetails){
        return regularUpdatesService.updateRegularUpdates(id, regularUpdatesDetails);
    }
    @GetMapping("{id}")
    public RegularUpdatesDTO getRegularUpdatesById(@PathVariable(value = "id") Long id){
        return regularUpdatesService.getRegularUpdatesById(id);
    }
    @DeleteMapping("{id}")
    public void deleteRegularUpdates(@PathVariable(value = "id" ) Long id){
        regularUpdatesService.deleteRegularUpdates(id);
    }
}
