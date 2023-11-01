package com.samia.ecole.services;

import com.samia.ecole.entities.Student;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }
    public Student getStudentById(Long id){
        return studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));
    }
    public Student createStudent(Student student){
        if(studentAlreadyExists(student.getId())){
            throw  new CustomException("student already exists", HttpStatus.CONFLICT);
        }
        return studentRepository.save(student);
    }

    private boolean studentAlreadyExists(Long id) {
        return studentRepository.findById(id).isPresent();
    }

    public Student updateStudent(Long id, Student studentDetails){
        Student student = studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));
        student.setName(studentDetails.getName());
        student.setProfileImage(studentDetails.getProfileImage());
        student.setBirthday(studentDetails.getBirthday());
        student.setPresence(studentDetails.isPresence());
        student.setCantine(studentDetails.isCantine());
        student.setUser(studentDetails.getUser());
        return studentRepository.save(student);
    }
    public void deleteStudent(Long id){
        Student student = studentRepository.findById(id).orElseThrow(()-> new CustomException("Student not found", HttpStatus.NOT_FOUND));
        studentRepository.deleteById(id);
    }
}
