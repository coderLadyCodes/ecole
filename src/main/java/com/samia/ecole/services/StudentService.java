//package com.samia.ecole.services;
//
//import com.samia.ecole.DTOsAndMappers.StudentDTO;
//import com.samia.ecole.DTOsAndMappers.StudentDTOMapper;
//import com.samia.ecole.entities.Student;
//import com.samia.ecole.exceptions.CustomException;
//import com.samia.ecole.repositories.StudentRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class StudentService {
//    private final StudentRepository studentRepository;
//
//    public StudentService(StudentRepository studentRepository) {
//        this.studentRepository = studentRepository;
//    }
//    public List<StudentDTO> getAllStudents(){
//        List<Student> students = studentRepository.findAll();
//        return students.stream().map((student -> StudentDTOMapper.mapToStudentDto(student)))
//                .collect(Collectors.toList());
//    }
//    public StudentDTO getStudentById(Long id){
//        Student student = studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));
//        return StudentDTOMapper.mapToStudentDto(student);
//    }
//    public StudentDTO createStudent(StudentDTO studentDTO){
//        Student student = StudentDTOMapper.mapToStudent(studentDTO);
//        if(studentAlreadyExists(student.getId())){
//            throw  new CustomException("student already exists", HttpStatus.CONFLICT);
//        }
//        Student savedStudent = studentRepository.save(student);
//        return StudentDTOMapper.mapToStudentDto(savedStudent);
//    }
//
//    private boolean studentAlreadyExists(Long id) {
//        return studentRepository.findById(id).isPresent();
//    }
//
//    public StudentDTO updateStudent(Long id, StudentDTO studentDetails){
//        Student student = studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));
//        student.setName(studentDetails.getName());
//        student.setProfileImage(studentDetails.getProfileImage());
//        student.setBirthday(studentDetails.getBirthday());
//        student.setPresence(studentDetails.isPresence());
//        student.setCantine(studentDetails.isCantine());
//        Student studentUpdated = studentRepository.save(student);
//        return StudentDTOMapper.mapToStudentDto(studentUpdated);
//    }
//    public void deleteStudent(Long id){
//        Student student = studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));
//        studentRepository.deleteById(id);
//    }
//}
