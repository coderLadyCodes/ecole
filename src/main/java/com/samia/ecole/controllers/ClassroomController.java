package com.samia.ecole.controllers;

import com.samia.ecole.entities.Classroom;
import com.samia.ecole.exceptions.UnauthorizedException;
import com.samia.ecole.services.ClassroomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("classroom")
public class ClassroomController {
    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PostMapping
    public Classroom createClassroom(@RequestBody Classroom classroom) throws UnauthorizedException {
        return classroomService.createClassroom(classroom);
    }
    @PostMapping("/activation")
    public Map<String, Object> activation(@RequestBody Map<String, String> activation){
        return classroomService.activation(activation);
    }
    @PutMapping("{id}")
    public Classroom updateClassroom(@PathVariable(value = "id") Long id, @RequestBody Classroom classroomDetails){
        return classroomService.updateClassroom(id, classroomDetails);
    }
    @GetMapping
    public List<Classroom> getAllClassrooms(){
       return classroomService.getAllClassrooms();
    }
    @GetMapping("{id}")
    public Classroom getClassroomById(@PathVariable(value = "id") Long id){
        return classroomService.getClassroomById(id);
    }
    @DeleteMapping("{id}")
    public void deleteClassroom(@PathVariable(value = "id") Long id){
        classroomService.deleteClassroom(id);
    }
}
