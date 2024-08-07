package com.samia.ecole.services;

import com.samia.ecole.DTOs.StudentDTO;
import com.samia.ecole.DTOs.UserDTO;
import com.samia.ecole.entities.Student;
import com.samia.ecole.entities.User;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.repositories.StudentRepository;
import com.samia.ecole.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository, UserService userService) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }
    public StudentDTO mapToStudentDto(Student student){
        StudentDTO studentDTO =  new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setProfileImage(student.getProfileImage());
        studentDTO.setBirthday(student.getBirthday());
        studentDTO.setClasse(student.getClasse());
        studentDTO.setPresence(student.getPresence());
        studentDTO.setCantine(student.getCantine());
        studentDTO.setUserId(student.getUser().getId());
        return studentDTO;
    }
    public Student mapToStudent(StudentDTO studentDTO){
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setProfileImage(studentDTO.getProfileImage());
        student.setBirthday(studentDTO.getBirthday());
        student.setClasse(studentDTO.getClasse());
        student.setPresence(studentDTO.getPresence());
        student.setCantine(studentDTO.getCantine());
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

    // GET A LIST OF STUDENTS BY USER ID THAT BELONGS TO THE SAME CLASSROOM ID
    public List<StudentDTO> getStudentsByClassroomId(Long classroomId){
        List<UserDTO> users = userService.getUsersByClassroomId(classroomId);
        List<StudentDTO> allStudents = new ArrayList<>();
        for (UserDTO user : users){
            List<StudentDTO> students = getStudentsByUserId(user.getId());
            allStudents.addAll(students);
        }
        return allStudents;
    }
    public StudentDTO getStudentById(Long id){
        Student student = studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));
        return mapToStudentDto(student);
    }
//    public StudentDTO createStudent(StudentDTO studentDTO){
//        if (studentDTO == null) {
//            throw new IllegalArgumentException("StudentDTO cannot be null");
//        }
//        Long userId = studentDTO.getUserId();
//        if (userId == null) {
//            throw new IllegalArgumentException("userId cannot be null for creating a student");
//        }
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (optionalUser.isEmpty()){
//            throw new UserNotFoundException("User not found for userId: " + userId);
//        }
//
//        User user = optionalUser.get();
//        Student student = mapToStudent(studentDTO);
//        student.setUser(user);
//        if(student.getId() != null && studentAlreadyExists(student.getId())){
//            throw  new CustomException("student already exists", HttpStatus.CONFLICT);
//        }
//        Student savedStudent = studentRepository.save(student);
//        return mapToStudentDto(savedStudent);
//    }
    public StudentDTO createStudent(StudentDTO studentDTO){
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
        Student savedStudent = studentRepository.save(student);
        return mapToStudentDto(savedStudent);
    }
    private boolean studentAlreadyExists(Long id) {
        return studentRepository.findById(id).isPresent();
    }

    public StudentDTO updateStudent(Long id, StudentDTO studentDetails){
        Student student = studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));
        student.setName(studentDetails.getName());
        student.setProfileImage(studentDetails.getProfileImage());
        student.setBirthday(studentDetails.getBirthday());
        student.setClasse(studentDetails.getClasse());
        student.setPresence(studentDetails.getPresence());
        student.setCantine(studentDetails.getCantine());
        Student studentUpdated = studentRepository.save(student);
        return mapToStudentDto(studentUpdated);
    }
    public void deleteStudent(Long id){     // ONLY ADMIN AND SUPER ADMIN CAN DO THAT
        Student student = studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));
        User user = student.getUser();
        if (user != null){
            user.getStudentList().remove(student);
        }
        studentRepository.delete(student);
    }
}
