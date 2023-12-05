package com.samia.ecole.controllers;

import com.samia.ecole.entities.Student;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.services.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@RestController
public class StudentController {
    //@Value("${ecole.images.userprofiles}")
    //String userprofileimagepath;
    private final StudentService studentService;
   // private final FileService fileservice;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("/students")
    public List<Student> getAllStudents(@RequestBody Student student){
        return studentService.getAllStudents();
    }
    @PostMapping("/student")
    public Student createStudent(@RequestBody Student student){
        return studentService.createStudent(student);
    }
//    @GetMapping(value = "/images/student/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public void serveImage(@PathVariable("imagename)") String imagename, HttpServletResponse response){
//        try {
//            InputStream inputStream = fileservice.serveImage(userprofileimagepath, imagename);
//            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//            StreamUtils.copy(inputStream,response.getOutputStream());
//        }catch (FileNotFoundException e){
//            throw new CustomException("File Not Found with the name:" + imagename, HttpStatus.BAD_REQUEST);
//        }catch ( Exception e){
//            e.printStackTrace();
//        }
//    }
    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable(value="id") Long id){
        return studentService.getStudentById(id);
    }
    @PutMapping("/student/update/{id}")
    public Student updateStudent(@PathVariable(value="id") Long id, @RequestBody Student studentDetails){
        return studentService.updateStudent(id,studentDetails);
    }
    @DeleteMapping("/student/delete/{id}")
    public void deleteStudent(@PathVariable(value="id") Long id){
        studentService.deleteStudent(id);
    }
}
