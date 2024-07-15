package com.samia.ecole.services;

import com.samia.ecole.entities.Classroom;
import com.samia.ecole.entities.User;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.repositories.ClassroomRepository;
import com.samia.ecole.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    public ClassroomService(ClassroomRepository classroomRepository, UserRepository userRepository) {
        this.classroomRepository = classroomRepository;
        this.userRepository = userRepository;
    }
    public List<Classroom> getAllClassrooms(){
        return classroomRepository.findAll();
    }
    public  Classroom getClassroomById(Long id){
        return classroomRepository.findById(id).orElseThrow(()-> new CustomException("Cette Classe n'existe pas", HttpStatus.NOT_FOUND));
    }
    public Classroom createClassroom(Classroom classroom){ // NOT SURE WHO CAN CREATE CLASSROOM
        User userContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userid = userContext.getId();
        Classroom classroom1 = new Classroom();
        classroom1.setGrade(classroom.getGrade());
        classroom1.setUserId(userid);
        Classroom savedClassroom = classroomRepository.save(classroom1);
        userContext.setClassroomId(savedClassroom.getId());
        userRepository.save(userContext);

        return savedClassroom;
    }
    public Classroom updateClassroom(Long id, Classroom classroomDetails){
        Classroom classroom = classroomRepository.findById(id).orElseThrow(()-> new CustomException("Cette Classe n'existe pas", HttpStatus.NOT_FOUND));
        classroom.setGrade(classroomDetails.getGrade());
        classroom.setUserId(classroomDetails.getUserId());
        return classroomRepository.save(classroom);
    }
    public void deleteClassroom(Long id){ // NOT SURE WHO CAN DELETE CLASSROOM
        Classroom classroom = classroomRepository.findById(id).orElseThrow(()-> new CustomException("Cette Classe n'existe pas", HttpStatus.NOT_FOUND));
        User userContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<User> userInClassroom = userRepository.findByClassroomId(classroom.getId());
        for(User user : userInClassroom){
            user.setClassroomId(null);
            userRepository.save(user);
        }
        classroomRepository.delete(classroom);
    }
}
