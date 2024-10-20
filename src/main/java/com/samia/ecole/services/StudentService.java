package com.samia.ecole.services;

import com.samia.ecole.DTOs.StudentDTO;
import com.samia.ecole.entities.Classroom;
import com.samia.ecole.entities.Student;
import com.samia.ecole.entities.User;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.repositories.ClassroomRepository;
import com.samia.ecole.repositories.StudentRepository;
import com.samia.ecole.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ClassroomRepository classroomRepository;
    private final FileUploadUtil fileUploadUtil;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository, UserService userService, ClassroomRepository classroomRepository, FileUploadUtil fileUploadUtil) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.classroomRepository = classroomRepository;
        this.fileUploadUtil = fileUploadUtil;
    }
    public StudentDTO mapToStudentDto(Student student){
        StudentDTO studentDTO =  new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setProfileImage(student.getProfileImage());
        studentDTO.setBirthday(student.getBirthday());
        studentDTO.setUserId(student.getUser().getId());
        studentDTO.setGrade(student.getGrade());
        return studentDTO;
    }
    public Student mapToStudent(StudentDTO studentDTO){
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setProfileImage(studentDTO.getProfileImage());
        student.setBirthday(studentDTO.getBirthday());
        student.setGrade(studentDTO.getGrade());
        User user = new User();
        user.setId(studentDTO.getUserId());
        student.setUser(user);
        return student;
    }

    // ONLY SUPER_ADMIN AND ADMIN CAN GET THE ALL STUDENTS OF ALL PARENTS
    public List<StudentDTO> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        return students.stream().map((this::mapToStudentDto))
                .collect(Collectors.toList());
    }

    // EACH PARENT GETS THEIR STUDENTS LIST
    public List<StudentDTO> getStudentsByUserId(Long userId){
        List<Student> students = studentRepository.getStudentsByUserId(userId);
        return students.stream().map((this::mapToStudentDto))
                .collect(Collectors.toList());
    }

    // TO GET A LIST OF STUDENTS BY CLASSROOM ID AND GRADE AND TEACHER
    public List<StudentDTO> getStudentsByClassroomId(Long classroomId) {// I SHOULD'VE CALLED IT: getStudentsByClassroomIdGradeTeacher() == FLEMME!
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new CustomException("Classroom not found", HttpStatus.NOT_FOUND));

        List<Student> students = studentRepository.findByClassroomIdAndGrade(classroomId, classroom.getGrade());

        List<Student> filteredStudents = students.stream()
                .filter(student -> {
                    Classroom studentClassroom = classroomRepository.findById(student.getClassroomId())
                            .orElseThrow(() -> new CustomException("Classroom not found", HttpStatus.NOT_FOUND));
                    return studentClassroom.getTeacher().equals(classroom.getTeacher());
                })
                .toList();

        return filteredStudents.stream()
                .map(this::mapToStudentDto)
                .collect(Collectors.toList());
    }
    public StudentDTO getStudentByIdAndClassroomId(Long id, Long classroomId){
        Student student = studentRepository.findByIdAndClassroomId(id, classroomId)
                .orElseThrow(() -> new CustomException("Student not found", HttpStatus.NOT_FOUND));
        return mapToStudentDto(student);
    }
    public StudentDTO getStudentById(Long id){
        Student student = studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));
        return mapToStudentDto(student);
    }
    public StudentDTO createStudent(StudentDTO studentDTO, MultipartFile multipartFile) throws IOException {
        if (studentDTO == null) {
            throw new IllegalArgumentException("StudentDTO cannot be null");
        }

        User userContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userContext.getId();
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null for creating a student");
        }

        Student student = mapToStudent(studentDTO);
        student.setUser(userContext);
        if(student.getId() != null && studentAlreadyExists(student.getId())){
            throw  new CustomException("student already exists", HttpStatus.CONFLICT);
        }
        if (multipartFile != null && !multipartFile.isEmpty()){
            String imageUrl = fileUploadUtil.uploadFile(multipartFile);
            student.setProfileImage(imageUrl);
        }

        Student savedStudent = studentRepository.save(student);
        return mapToStudentDto(savedStudent);
    }
    private boolean studentAlreadyExists(Long id) {
        return studentRepository.findById(id).isPresent();
    }
@Transactional
public StudentDTO updateStudent(Long id, StudentDTO studentDetails, MultipartFile multipartFile) throws IOException {
        //Student student = studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));
        Student originalStudent = studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));
        String oldImageUrl = originalStudent.getProfileImage();

    if (multipartFile != null && !multipartFile.isEmpty()) {
        String newImageUrl = fileUploadUtil.uploadFile(multipartFile);
        studentDetails.setProfileImage(newImageUrl);

        if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
            String publicId = extractPublicIdFromUrl(oldImageUrl);
            fileUploadUtil.deleteFile(publicId);
        }
    } else {
        studentDetails.setProfileImage(oldImageUrl);
    }
    originalStudent.setName(studentDetails.getName());
    originalStudent.setProfileImage(studentDetails.getProfileImage());
    originalStudent.setBirthday(studentDetails.getBirthday());
    originalStudent.setGrade(studentDetails.getGrade());
/*//        student.setClasse(studentDetails.getClasse());
//        student.setAbsence(studentDetails.getAbsence());
//        student.setCantine(studentDetails.getCantine());
//        student.setGarderie(studentDetails.getGarderie());*/
        Student studentUpdated = studentRepository.save(originalStudent);
        return mapToStudentDto(studentUpdated);
    }
    private String extractPublicIdFromUrl(String imageUrl) {
        String[] urlParts = imageUrl.split("/");
        String fileName = urlParts[urlParts.length - 1];
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }
    public void deleteStudent(Long id){     // ONLY ADMIN AND SUPER ADMIN CAN DO THAT
        Student student = studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));

        if (student.getProfileImage() != null) {
            String publicId = extractPublicIdFromUrl(student.getProfileImage());
            try {
                fileUploadUtil.deleteFile(publicId);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete image from Cloudinary: " + e.getMessage());
            }
        }
        User user = student.getUser();
        if (user != null){
            user.getStudentList().remove(student);
        }
        studentRepository.delete(student);
    }
}
