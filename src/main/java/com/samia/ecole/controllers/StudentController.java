//package com.samia.ecole.controllers;
//
//import com.samia.ecole.DTOsAndMappers.StudentDTO;
//import com.samia.ecole.entities.Student;
//import com.samia.ecole.exceptions.CustomException;
//import com.samia.ecole.services.StudentService;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.util.StreamUtils;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.List;
//
//@RestController
//@RequestMapping("/students")
//public class StudentController {
//    //@Value("${ecole.images.userprofiles}")
//    //String userprofileimagepath;
//    private final StudentService studentService;
//
//    public StudentController(StudentService studentService) {
//        this.studentService = studentService;
//    }
//    @GetMapping()
//    public List<StudentDTO> getAllStudents(@RequestBody Student student){
//        return studentService.getAllStudents();
//    }
//    @PostMapping("/student")
//    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO){
//        return studentService.createStudent(studentDTO);
//    }
////    @GetMapping(value = "/images/student/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
////    public void serveImage(@PathVariable("imagename)") String imagename, HttpServletResponse response){
////        try {
////            InputStream inputStream = fileservice.serveImage(userprofileimagepath, imagename);
////            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
////            StreamUtils.copy(inputStream,response.getOutputStream());
////        }catch (FileNotFoundException e){
////            throw new CustomException("File Not Found with the name:" + imagename, HttpStatus.BAD_REQUEST);
////        }catch ( Exception e){
////            e.printStackTrace();
////        }
////    }
//    @GetMapping("{id}")
//    public StudentDTO getStudentById(@PathVariable(value="id") Long id){
//        return studentService.getStudentById(id);
//    }
//    @PutMapping("{id}")
//    public StudentDTO updateStudent(@PathVariable(value="id") Long id, @RequestBody StudentDTO studentDetails){
//        return studentService.updateStudent(id,studentDetails);
//    }
//    @DeleteMapping("{id}")
//    public void deleteStudent(@PathVariable(value="id") Long id){
//        studentService.deleteStudent(id);
//    }
//}
