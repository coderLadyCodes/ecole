package com.samia.ecole.services;

import com.samia.ecole.DTOs.StudentDTO;
import com.samia.ecole.entities.Student;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public StudentDTO mapToStudentDto(Student student){
        StudentDTO studentDTO =  new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setProfileImage(student.getProfileImage());
        studentDTO.setBirthday(student.getBirthday());
        studentDTO.setPresence(student.getPresence());
        studentDTO.setCantine(student.getCantine());
        return studentDTO;
    }
    public Student mapToStudent(StudentDTO studentDTO){
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setProfileImage(studentDTO.getProfileImage());
        student.setBirthday(studentDTO.getBirthday());
        student.setPresence(studentDTO.getPresence());
        student.setCantine(studentDTO.getCantine());
        return student;
    }
    public List<StudentDTO> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        return students.stream().map((this::mapToStudentDto))
                .collect(Collectors.toList());
    }
    public StudentDTO getStudentById(Long id){
        Student student = studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));
        return mapToStudentDto(student);
    }
    public StudentDTO createStudent(StudentDTO studentDTO){
        if (studentDTO == null) {
            throw new IllegalArgumentException("StudentDTO cannot be null");
        }
        Student student = mapToStudent(studentDTO);
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
        student.setPresence(studentDetails.getPresence());
        student.setCantine(studentDetails.getCantine());
        Student studentUpdated = studentRepository.save(student);
        return mapToStudentDto(studentUpdated);
    }
    public void deleteStudent(Long id){
        Student student = studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));
        studentRepository.deleteById(id);
    }
}
