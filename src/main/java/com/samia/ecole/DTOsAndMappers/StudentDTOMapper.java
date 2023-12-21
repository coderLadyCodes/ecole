package com.samia.ecole.DTOsAndMappers;
import com.samia.ecole.entities.Student;

public class StudentDTOMapper {
    public static StudentDTO mapToStudentDto(Student student){
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getProfileImage(),
                student.getBirthday(),
                student.isPresence(),
                student.isCantine()
        );
    }
    public static Student mapToStudent(StudentDTO studentDTO){
        return new Student(
                studentDTO.getId(),
                studentDTO.getName(),
                studentDTO.getProfileImage(),
                studentDTO.getBirthday(),
                studentDTO.isPresence(),
                studentDTO.isCantine()
        );
    }
}
