package com.samia.ecole.services;

import com.samia.ecole.entities.Classroom;
import com.samia.ecole.entities.Role;
import com.samia.ecole.entities.Student;
import com.samia.ecole.entities.User;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.exceptions.UnauthorizedException;
import com.samia.ecole.repositories.ClassroomRepository;
import com.samia.ecole.repositories.StudentRepository;
import com.samia.ecole.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private  final  NotificationService notificationService;
    public ClassroomService(ClassroomRepository classroomRepository, UserRepository userRepository, StudentRepository studentRepository, NotificationService notificationService) {
        this.classroomRepository = classroomRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.notificationService = notificationService;
    }
    public List<Classroom> getAllClassrooms(){
        return classroomRepository.findAll();
    }
    public  Classroom getClassroomById(Long id){
        return classroomRepository.findById(id).orElseThrow(()-> new CustomException("Cette Classe n'existe pas", HttpStatus.NOT_FOUND));
    }
    public Classroom createClassroom(Classroom classroom) throws UnauthorizedException {
        User userContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Role userRole = userContext.getRole();
        if (userRole != Role.ADMIN && userRole != Role.SUPER_ADMIN) {
            throw new UnauthorizedException("User does not have permission to create a classroom.");
        }
        Long userid = userContext.getId();
        Classroom classroom1 = getClassroom(classroom, userid);
        classroom1.setTeacher(userContext.getName());
        Classroom savedClassroom = classroomRepository.save(classroom1);
        notificationService.envoyerCode(savedClassroom);
        userContext.setClassroomId(savedClassroom.getId());
        userRepository.save(userContext);
        return savedClassroom;
    }

    private static Classroom getClassroom(Classroom classroom, Long userid) {
        Classroom classroom1 = new Classroom();
        classroom1.setGrade(classroom.getGrade());
        classroom1.setUserId(userid);
        // set the classroom code
        Random random = new Random();
        int randomInt = random.nextInt(999999999);
        char randomLetter1 = (char) ('A' + random.nextInt(26));
        char randomLetter2 = (char) ('A' + random.nextInt(26));
        String code = String.format("E%cM%04dS%cM", randomLetter1, randomInt, randomLetter2);
        classroom1.setClassroomCode(code);
        return classroom1;
    }

    public Classroom updateClassroom(Long id, Classroom classroomDetails){
        Classroom classroom = classroomRepository.findById(id).orElseThrow(()-> new CustomException("Cette Classe n'existe pas", HttpStatus.NOT_FOUND));
        classroom.setGrade(classroomDetails.getGrade());
        classroom.setUserId(classroomDetails.getUserId());
        classroom.setClassroomCode(classroomDetails.getClassroomCode());
        classroom.setTeacher(classroomDetails.getTeacher());
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
    public Map<String, Object> activation(Map<String, String> activation) {
        String userIdStr = activation.get("userId");
        String classroomCode = activation.get("classroomCode");
        String teacher = activation.get("teacher");
        String kidIdStr = activation.get("kidId");

        if (userIdStr != null && classroomCode != null) {
            Long userId = Long.parseLong(userIdStr);

            // Find the user by ID
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                //Role role = user.getRole();

                if (user.getRole().equals(Role.ADMIN)) {
                    Classroom classroom = classroomRepository.findByClassroomCode(classroomCode);
                    if (classroom != null) {
                        user.setClassroomId(classroom.getId());
                        userRepository.save(user);

                        Map<String, Object> response = new HashMap<>();
                        response.put("classroomId", classroom.getId());
                        response.put("classroomCode", classroomCode);
                        response.put("userId", userId);
                        return response;
                    } else {
                        throw new CustomException("Classroom not found", HttpStatus.NOT_FOUND);
                    }
                } else if ((user.getRole().equals(Role.PARENT)) || (user.getRole().equals(Role.SUPER_ADMIN))){
                    if (teacher != null && kidIdStr != null) {
                        Long kidId = Long.parseLong(kidIdStr);

                        // Find student by ID
                        Optional<Student> optionalStudent = studentRepository.findById(kidId);
                        if (optionalStudent.isPresent()) {
                            Student student = optionalStudent.get();

                            // Find the classroom by classroomCode
                            Classroom classroom = classroomRepository.findByClassroomCode(classroomCode);
                            if (classroom != null) {
                                // Check if the teacher's name matches the classroom's teacher
                                if (!teacher.equals(classroom.getTeacher())) {
                                    throw new CustomException("Le nom de l'enseignant n'est pas correct", HttpStatus.BAD_REQUEST);
                                }

                                // Check if student's grade matches the classroom's grade
                                if (!student.getGrade().equals(classroom.getGrade())) {
                                    throw new CustomException("La classe de l'enfant n'est pas correct", HttpStatus.BAD_REQUEST);
                                }

                                // Check if the student is already assigned to a different classroom
                                if (student.getClassroomId() != null && !student.getClassroomId().equals(classroom.getId())) {
                                    throw new CustomException("Cet élève appartient à une autre classe", HttpStatus.BAD_REQUEST);
                                }

                                // Assign the classroom to the student
                                student.setClassroomId(classroom.getId());
                                studentRepository.save(student);

                                // Assign the classroom to the user
                                user.setClassroomId(classroom.getId());
                                userRepository.save(user);

                                Map<String, Object> response = new HashMap<>();
                                response.put("classroomId", classroom.getId());
                                response.put("classroomCode", classroomCode);
                                response.put("userId", userId);
                                response.put("teacher", classroom.getTeacher());
                                response.put("kidId", kidId);
                                return response;
                            } else {
                                throw new CustomException("Classroom not found", HttpStatus.NOT_FOUND);
                            }
                        } else {
                            throw new CustomException("Student not found", HttpStatus.NOT_FOUND);
                        }
                    } else {
                        throw new CustomException("Missing teacher name or kidId", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    throw new CustomException("Invalid role", HttpStatus.FORBIDDEN);
                }
            } else {
                throw new CustomException("User not found", HttpStatus.NOT_FOUND);
            }
        } else {
            throw new CustomException("Missing userId or classroomCode", HttpStatus.BAD_REQUEST);
        }
    }

}
