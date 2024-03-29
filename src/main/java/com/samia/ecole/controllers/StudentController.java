package com.samia.ecole.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.samia.ecole.DTOs.StudentDTO;
import com.samia.ecole.services.FileUploadUtil;
import com.samia.ecole.services.StudentService;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@MultipartConfig
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping()
    public List<StudentDTO> getAllStudents(){
        return studentService.getAllStudents();
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDTO createStudent(@RequestPart String studentDTO,
                                    @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile)throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        StudentDTO studentdto = mapper.readValue(studentDTO, StudentDTO.class);
       // studentdto.setUserId(userId);
        if (multipartFile != null && !multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            int extensionIndex = fileName.lastIndexOf('.');
            String fileNameWithoutExtension = fileName.substring(0, extensionIndex);

            String shortenedFileName = fileNameWithoutExtension.substring(0, Math.min(fileNameWithoutExtension.length(), 10));

            String profileImageName = shortenedFileName + fileName.substring(extensionIndex);

            studentdto.setProfileImage(profileImageName);

            StudentDTO savedStudent = studentService.createStudent(studentdto);

            String uploadDir = "images/" + savedStudent.getId();

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            return savedStudent;
        } else {
            return studentService.createStudent(studentdto);
        }
    }
    @GetMapping("{id}")
    public StudentDTO getStudentById(@PathVariable(value="id") Long id){
        return studentService.getStudentById(id);
    }
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDTO updateStudent(@PathVariable(value = "id") Long id, @RequestPart String studentDetails,
                                    @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        StudentDTO studentDetail = mapper.readValue(studentDetails, StudentDTO.class);
        //studentDetail.setUserId(userId);
        StudentDTO originalStudent =  studentService.getStudentById(id);
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            int extensionIndex = fileName.lastIndexOf('.');

            String fileNameWithoutExtension = fileName.substring(0, extensionIndex);

            String shortenedFileName = fileNameWithoutExtension.substring(0, Math.min(fileNameWithoutExtension.length(), 10));

            String profileImageName = shortenedFileName + fileName.substring(extensionIndex);

            studentDetail.setProfileImage(profileImageName);

            StudentDTO updatedStudent = studentService.updateStudent(id, studentDetail);

            String uploadDir = "images/" + updatedStudent.getId();

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            if (originalStudent.getProfileImage() != null && !originalStudent.getProfileImage().isEmpty()) {
                String previousPath = uploadDir + "/" + originalStudent.getProfileImage();
                FileUploadUtil.deleteFile(previousPath);
            }
            return updatedStudent;
        }else {
            return studentService.updateStudent(id,studentDetail);
        }
    }
    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable(value="id") Long id){
        studentService.deleteStudent(id);
    }
}
