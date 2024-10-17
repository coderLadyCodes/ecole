package com.samia.ecole.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.samia.ecole.DTOs.StudentDTO;
import com.samia.ecole.services.FileUploadUtil;
import com.samia.ecole.services.StudentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.util.List;

@RestController
@MultipartConfig
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final FileUploadUtil fileUploadUtil;

    public StudentController(StudentService studentService, FileUploadUtil fileUploadUtil) {
        this.studentService = studentService;
        this.fileUploadUtil = fileUploadUtil;
    }
    @GetMapping()
    public List<StudentDTO> getAllStudents(){
        return studentService.getAllStudents();
    }
    @GetMapping("/user/{userId}")
    public List<StudentDTO> getStudentsByUserId(@PathVariable(value="userId") Long userId){
        return studentService.getStudentsByUserId(userId);
    }
    @GetMapping("/classroom/{classroomId}")
    public List<StudentDTO> getStudentsByClassroomId(@PathVariable(value="classroomId") Long classroomId){
        return studentService.getStudentsByClassroomId(classroomId);
    }
    @GetMapping("{id}/classroom/{classroomId}")
    public StudentDTO getStudentByIdAndClassroomId(@PathVariable(value = "id") Long id, @PathVariable(value = "classroomId") Long classroomId){
        return studentService.getStudentByIdAndClassroomId(id, classroomId);
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDTO createStudent(@RequestPart String studentDTO,
                                    @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile)throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        StudentDTO studentdto = mapper.readValue(studentDTO, StudentDTO.class);

        //if (multipartFile != null && !multipartFile.isEmpty()){
            //String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            //int extensionIndex = fileName.lastIndexOf('.');
            //String fileNameWithoutExtension = fileName.substring(0, extensionIndex);

            //String shortenedFileName = fileNameWithoutExtension.substring(0, Math.min(fileNameWithoutExtension.length(), 10));

            //String profileImageName = shortenedFileName + fileName.substring(extensionIndex);
            //String imageUrl = fileUploadUtil.uploadFile(multipartFile);

            //studentdto.setProfileImage(imageUrl);

            //StudentDTO savedStudent = studentService.createStudent(studentdto);

            //String uploadDir = "images/" + savedStudent.getId();

            //FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            //return studentService.createStudent(studentdto);
        //} else {
           // return studentService.createStudent(studentdto);
        //}
        return studentService.createStudent(studentdto, multipartFile);
    }
    @GetMapping("/student/{id}")
    public StudentDTO getStudentById(@PathVariable(value="id") Long id){
        return studentService.getStudentById(id);
    }
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDTO updateStudent(@PathVariable(value = "id") Long id,
                                    @RequestPart String studentDetails,
                                    @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        StudentDTO studentDetail = mapper.readValue(studentDetails, StudentDTO.class);
        //StudentDTO originalStudent = studentService.getStudentById(id);
        //String oldImageUrl = originalStudent.getProfileImage();

        //if (multipartFile != null && !multipartFile.isEmpty()) {
            //try {
                //String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

                //int extensionIndex = fileName.lastIndexOf('.');

                //String fileNameWithoutExtension = fileName.substring(0, extensionIndex);

                //String shortenedFileName = fileNameWithoutExtension.substring(0, Math.min(fileNameWithoutExtension.length(), 10));

                //String profileImageName = shortenedFileName + fileName.substring(extensionIndex);
                //String newImageUrl = fileUploadUtil.uploadFile(multipartFile);

                //studentDetail.setProfileImage(newImageUrl);

                //StudentDTO updatedStudent = studentService.updateStudent(id, studentDetail);

                //if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                    //String publicId = extractPublicIdFromUrl(oldImageUrl);
                    //fileUploadUtil.deleteFile(publicId);
                //}
                //return updatedStudent;
            //} catch (Exception e) {
                //throw new IOException("Error updating user and image: " + e.getMessage());
            //}

            //String uploadDir = "images/" + updatedStudent.getId();

            //FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            //if (originalStudent.getProfileImage() != null && !originalStudent.getProfileImage().isEmpty()) {
            //String previousPath = uploadDir + "/" + originalStudent.getProfileImage();
            //FileUploadUtil.deleteFile(previousPath);
            //}
            //return updatedStudent;
            //}else {
             //}
            return studentService.updateStudent(id, studentDetail,multipartFile);
    }
//    private String extractPublicIdFromUrl(String imageUrl) {
//        String[] urlParts = imageUrl.split("/");
//        String fileName = urlParts[urlParts.length - 1];
//        return fileName.substring(0, fileName.lastIndexOf('.'));
//    }

    //@PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable(value="id") Long id){
        studentService.deleteStudent(id);
    }
}
