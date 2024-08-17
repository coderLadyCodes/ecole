package com.samia.ecole.services;

import com.samia.ecole.DTOs.RegularUpdatesDTO;
import com.samia.ecole.entities.RegularUpdates;
import com.samia.ecole.entities.Role;
import com.samia.ecole.entities.Student;
import com.samia.ecole.entities.User;
import com.samia.ecole.exceptions.CustomException;
import com.samia.ecole.exceptions.UnauthorizedException;
import com.samia.ecole.repositories.RegularUpdatesRepository;
import com.samia.ecole.repositories.StudentRepository;
import com.samia.ecole.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegularUpdatesService {
    private final RegularUpdatesRepository regularUpdatesRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public RegularUpdatesService(RegularUpdatesRepository regularUpdatesRepository, StudentRepository studentRepository, UserRepository userRepository) {
        this.regularUpdatesRepository = regularUpdatesRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }
    public RegularUpdatesDTO mapToRegularUpdatesDTO(RegularUpdates regularUpdates){
        RegularUpdatesDTO regularUpdatesDTO = new RegularUpdatesDTO();
        regularUpdatesDTO.setId(regularUpdates.getId());
        regularUpdatesDTO.setStudentId(regularUpdates.getStudent().getId());
        regularUpdatesDTO.setParentId(regularUpdates.getParent().getId());
        regularUpdatesDTO.setLocalDateTime(regularUpdates.getLocalDateTime());
        regularUpdatesDTO.setAbsent(regularUpdates.getAbsent());
        regularUpdatesDTO.setHasCantine(regularUpdates.getHasCantine());
        regularUpdatesDTO.setGarderie(regularUpdates.getGarderie());
        return regularUpdatesDTO;
    }
    public RegularUpdates mapToRegularUpdates(RegularUpdatesDTO regularUpdatesDTO){
        RegularUpdates regularUpdates = new RegularUpdates();
        regularUpdates.setId(regularUpdatesDTO.getId());
        // Fetch the full User entity (Parent)
        User parent = userRepository.findById(regularUpdatesDTO.getParentId())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        regularUpdates.setParent(parent);
//        User parent = new User();
//        parent.setId(regularUpdatesDTO.getParentId());
//        regularUpdates.setParent(parent);
        // Fetch the full Student entity
        Student student = studentRepository.findById(regularUpdatesDTO.getStudentId())
                .orElseThrow(() -> new CustomException("Student not found", HttpStatus.NOT_FOUND));
        regularUpdates.setStudent(student);
//        Student student = new Student();
//        student.setId(regularUpdatesDTO.getStudentId());
//        regularUpdates.setStudent(student);
        regularUpdates.setLocalDateTime(regularUpdatesDTO.getLocalDateTime());
        regularUpdates.setAbsent(regularUpdatesDTO.getAbsent());
        regularUpdates.setHasCantine(regularUpdatesDTO.getHasCantine());
        regularUpdates.setGarderie(regularUpdatesDTO.getGarderie());
        return regularUpdates;
    }
    public List<RegularUpdatesDTO> getAllregulardUpdates(){
        List<RegularUpdates> regularUpdates = regularUpdatesRepository.findAll();
        return regularUpdates.stream().map((this::mapToRegularUpdatesDTO))
                .collect(Collectors.toList());
    }
    public RegularUpdatesDTO getRegularUpdatesById(Long id){
        RegularUpdates regularUpdates = regularUpdatesRepository.findById(id).orElseThrow(()-> new CustomException("Updates non trouvés", HttpStatus.NOT_FOUND));
        return mapToRegularUpdatesDTO(regularUpdates);
    }
    public RegularUpdatesDTO createRegularUpdates(RegularUpdatesDTO regularUpdatesDTO, Long studentId) throws UnauthorizedException {
        User userContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Role userRole = userContext.getRole();
        if(userRole != Role.PARENT && userRole != Role.SUPER_ADMIN){
            throw new UnauthorizedException("Vous ne pouvez paas creer des Updates");
        }
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new CustomException("eleve n'esiste pas", HttpStatus.NOT_FOUND));
        RegularUpdates regularUpdates = mapToRegularUpdates(regularUpdatesDTO);
        regularUpdates.setStudent(student);
        regularUpdates.setParent(userContext);
        regularUpdates.setLocalDateTime(LocalDateTime.now());
        RegularUpdates savedRegularUpdates = regularUpdatesRepository.save(regularUpdates);
         return mapToRegularUpdatesDTO(savedRegularUpdates);
    }
    public RegularUpdatesDTO updateRegularUpdates(Long id, RegularUpdatesDTO regularUpdatesDetails){
        RegularUpdates regularUpdates = regularUpdatesRepository.findById(id).orElseThrow(()-> new CustomException("cet update n'existe pas", HttpStatus.NOT_FOUND));

        Student student = studentRepository.findById(regularUpdatesDetails.getStudentId())
                .orElseThrow(() -> new CustomException("Student not found", HttpStatus.NOT_FOUND));
        User parent = userRepository.findById(regularUpdatesDetails.getParentId())
                .orElseThrow(() -> new CustomException("Parent not found", HttpStatus.NOT_FOUND));
        regularUpdates.setAbsent(regularUpdatesDetails.getAbsent());
        regularUpdates.setHasCantine(regularUpdatesDetails.getHasCantine());
        regularUpdates.setGarderie(regularUpdatesDetails.getGarderie());
        regularUpdates.setStudent(student);
        regularUpdates.setParent(parent);
        regularUpdates.setLocalDateTime(regularUpdatesDetails.getLocalDateTime());
        RegularUpdates regularUpdatesUpdated =  regularUpdatesRepository.save(regularUpdates);
        return mapToRegularUpdatesDTO(regularUpdatesUpdated);
    }
    public void deleteRegularUpdates(Long id){
        RegularUpdates regularUpdates = regularUpdatesRepository.findById(id).orElseThrow(()-> new CustomException("cet update n'existe pas", HttpStatus.NOT_FOUND));
        regularUpdatesRepository.delete(regularUpdates);
    }
}
