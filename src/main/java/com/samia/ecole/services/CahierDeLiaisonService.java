package com.samia.ecole.services;

import com.samia.ecole.DTOs.CahierDeLiaisonDTO;
import com.samia.ecole.entities.CahierDeLiaison;
import com.samia.ecole.entities.Role;
import com.samia.ecole.entities.Student;
import com.samia.ecole.entities.User;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.exceptions.UnauthorizedException;
import com.samia.ecole.repositories.CahierDeLiaisonRepository;
import com.samia.ecole.repositories.StudentRepository;
import com.samia.ecole.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CahierDeLiaisonService {
    private final CahierDeLiaisonRepository cahierDeLiaisonRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public CahierDeLiaisonService(CahierDeLiaisonRepository cahierDeLiaisonRepository, StudentRepository studentRepository, UserRepository userRepository) {
        this.cahierDeLiaisonRepository = cahierDeLiaisonRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public CahierDeLiaisonDTO mapToCahierDeLiaisonDTO(CahierDeLiaison cahierDeLiaison){
        CahierDeLiaisonDTO cahierDeLiaisonDTO = new CahierDeLiaisonDTO();
        cahierDeLiaisonDTO.setId(cahierDeLiaison.getId());
        cahierDeLiaisonDTO.setTeacherId(cahierDeLiaison.getTeacher().getId());
        cahierDeLiaisonDTO.setStudentId(cahierDeLiaison.getStudent().getId());
        cahierDeLiaisonDTO.setTeacherName(cahierDeLiaison.getTeacherName());
        cahierDeLiaisonDTO.setTitle(cahierDeLiaison.getTitle());
        cahierDeLiaisonDTO.setContent(cahierDeLiaison.getContent());
        cahierDeLiaisonDTO.setDateTime(cahierDeLiaison.getDateTime());
        cahierDeLiaisonDTO.setModifiedAt(cahierDeLiaison.getModifiedAt());
        return cahierDeLiaisonDTO;
    }
    public CahierDeLiaison mapToCahierDeLiaison(CahierDeLiaisonDTO cahierDeLiaisonDTO){
        CahierDeLiaison cahierDeLiaison = new CahierDeLiaison();
        cahierDeLiaison.setId(cahierDeLiaisonDTO.getId());
        User teacher = new User();
        teacher.setId(cahierDeLiaisonDTO.getTeacherId());
        cahierDeLiaison.setTeacher(teacher);

        Student student = new Student();
        student.setId(cahierDeLiaisonDTO.getStudentId());
        cahierDeLiaison.setStudent(student);

        cahierDeLiaison.setTeacherName(cahierDeLiaisonDTO.getTeacherName());
        cahierDeLiaison.setTitle(cahierDeLiaisonDTO.getTitle());
        cahierDeLiaison.setContent(cahierDeLiaisonDTO.getContent());
        cahierDeLiaison.setDateTime(cahierDeLiaisonDTO.getDateTime());
        cahierDeLiaison.setModifiedAt(cahierDeLiaisonDTO.getModifiedAt());
        return cahierDeLiaison;
    }
    public CahierDeLiaisonDTO createCahierDeLiaison(CahierDeLiaisonDTO cahierDeLiaisonDTO, Long studentId) throws UnauthorizedException {
        if (studentId == null) {
            throw new IllegalArgumentException("The studentId must not be null.");
        }
        if (cahierDeLiaisonDTO == null) {
            throw new IllegalArgumentException("regularUpdatesDTO cannot be null.");
        }
        User userContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Role userRole = userContext.getRole();
        if( userRole != Role.SUPER_ADMIN){
            throw new UnauthorizedException("Vous ne pouvez paas creer de Cahier de Liaison");
        }
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new CustomException("eleve n'esiste pas", HttpStatus.NOT_FOUND));
        CahierDeLiaison cahierDeLiaison = mapToCahierDeLiaison(cahierDeLiaisonDTO);
        cahierDeLiaison.setTeacher(userContext);
        cahierDeLiaison.setStudent(student);
        cahierDeLiaison.setTeacherName(userContext.getName());
        cahierDeLiaison.setDateTime(LocalDateTime.now());
        cahierDeLiaison.setModifiedAt(LocalDateTime.now());
        CahierDeLiaison cahierDeLiaisonSaved = cahierDeLiaisonRepository.save(cahierDeLiaison);
        return mapToCahierDeLiaisonDTO(cahierDeLiaisonSaved);
    }
    public List<CahierDeLiaisonDTO> getAllCahierDeLiaison(){
        List<CahierDeLiaison> cahierDeLiaisons = cahierDeLiaisonRepository.findAll();
        return cahierDeLiaisons.stream().map(this::mapToCahierDeLiaisonDTO)
        .collect(Collectors.toList());
    }
    public CahierDeLiaisonDTO getCahierDeLiaisonById(Long id){
        CahierDeLiaison cahierDeLiaison = cahierDeLiaisonRepository.findById(id).orElseThrow(()-> new CustomException("Cahier De Liaison non trouvés", HttpStatus.NOT_FOUND));
        return mapToCahierDeLiaisonDTO(cahierDeLiaison);
    }
    public List<CahierDeLiaisonDTO>getAllCahierDeLiaisonByStudentId(Long studentId){
        List<CahierDeLiaison> cahierDeLiaisonList = cahierDeLiaisonRepository.findByStudentId(studentId);
        return cahierDeLiaisonList.stream().map(this::mapToCahierDeLiaisonDTO)
                .collect(Collectors.toList());
    }
    public CahierDeLiaisonDTO updateCahierDeLiaison(Long id, CahierDeLiaisonDTO cahierDeLiaisonDetails){
        CahierDeLiaison cahierDeLiaison = cahierDeLiaisonRepository.findById(id).orElseThrow(()-> new CustomException("cet update n'existe pas", HttpStatus.NOT_FOUND));
        Student student = studentRepository.findById(cahierDeLiaisonDetails.getStudentId())
                .orElseThrow(() -> new CustomException("Student not found", HttpStatus.NOT_FOUND));
        User teacher = userRepository.findById(cahierDeLiaisonDetails.getTeacherId())
                .orElseThrow(() -> new CustomException("Parent not found", HttpStatus.NOT_FOUND));
        cahierDeLiaison.setTeacher(teacher);
        cahierDeLiaison.setStudent(student);
        cahierDeLiaison.setTeacherName(teacher.getName());
        cahierDeLiaison.setTitle(cahierDeLiaisonDetails.getTitle());
        cahierDeLiaison.setContent(cahierDeLiaisonDetails.getContent());
        cahierDeLiaison.setDateTime(cahierDeLiaisonDetails.getDateTime());
        cahierDeLiaison.setModifiedAt(cahierDeLiaisonDetails.getModifiedAt());
        CahierDeLiaison cahierDeLiaisonSaved = cahierDeLiaisonRepository.save(cahierDeLiaison);
        return mapToCahierDeLiaisonDTO(cahierDeLiaisonSaved);
    }
    public void deleteCahierDeLiaison(Long id){
        CahierDeLiaison cahierDeLiaison = cahierDeLiaisonRepository.findById(id).orElseThrow(()-> new CustomException("cet cahier de liaison n'existe pas", HttpStatus.NOT_FOUND));
        cahierDeLiaisonRepository.delete(cahierDeLiaison);
    }
}
