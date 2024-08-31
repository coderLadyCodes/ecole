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
        regularUpdatesDTO.setModifiedAt(regularUpdates.getModifiedAt());
        regularUpdatesDTO.setLocalDate(regularUpdates.getLocalDate());
        regularUpdatesDTO.setAbsent(regularUpdates.getAbsent());
        regularUpdatesDTO.setHasCantine(regularUpdates.getHasCantine());
        regularUpdatesDTO.setGarderie(regularUpdates.getGarderie());
        return regularUpdatesDTO;
    }
    public RegularUpdates mapToRegularUpdates(RegularUpdatesDTO regularUpdatesDTO){
        RegularUpdates regularUpdates = new RegularUpdates();
        regularUpdates.setId(regularUpdatesDTO.getId());
        User parent = new User();
        parent.setId(regularUpdatesDTO.getParentId());
        regularUpdates.setParent(parent);
        Student student = new Student();
        student.setId(regularUpdatesDTO.getStudentId());
        regularUpdates.setStudent(student);
        regularUpdates.setLocalDateTime(regularUpdatesDTO.getLocalDateTime());
        regularUpdates.setModifiedAt(regularUpdatesDTO.getModifiedAt());
        regularUpdates.setLocalDate(regularUpdatesDTO.getLocalDate());
        regularUpdates.setAbsent(regularUpdatesDTO.getAbsent());
        regularUpdates.setHasCantine(regularUpdatesDTO.getHasCantine());
        regularUpdates.setGarderie(regularUpdatesDTO.getGarderie());
        return regularUpdates;
    }
    public RegularUpdatesDTO createRegularUpdates(RegularUpdatesDTO regularUpdatesDTO, Long studentId) throws UnauthorizedException {
        if (studentId == null) {
            throw new IllegalArgumentException("The studentId must not be null.");
        }
        if (regularUpdatesDTO == null) {
            throw new IllegalArgumentException("regularUpdatesDTO cannot be null.");
        }
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
        regularUpdates.setModifiedAt(LocalDateTime.now());
        RegularUpdates savedRegularUpdates = regularUpdatesRepository.save(regularUpdates);
         return mapToRegularUpdatesDTO(savedRegularUpdates);
    }
    public RegularUpdatesDTO getLatestRegularUpdateByStudentId(Long studentId){
        RegularUpdates regularUpdates = regularUpdatesRepository.findLatestByStudentId(studentId).orElseThrow(() -> new CustomException("No regular updates found for student with ID " + studentId, HttpStatus.NOT_FOUND));
        return mapToRegularUpdatesDTO(regularUpdates);
    }
    public List<RegularUpdatesDTO> getAllregulardUpdates(){
        List<RegularUpdates> regularUpdates = regularUpdatesRepository.findAll();
        return regularUpdates.stream().map((this::mapToRegularUpdatesDTO))
                .collect(Collectors.toList());
    }
    public List<RegularUpdatesDTO> getAllregulardUpdatesByStudenId(Long studentId){
        List<RegularUpdates> regularUpdates = regularUpdatesRepository.findByStudentId(studentId);
        return regularUpdates.stream().map((this::mapToRegularUpdatesDTO))
                .collect(Collectors.toList());
    }
    public RegularUpdatesDTO getRegularUpdatesById(Long id){
        RegularUpdates regularUpdates = regularUpdatesRepository.findById(id).orElseThrow(()-> new CustomException("Updates non trouvés", HttpStatus.NOT_FOUND));
        return mapToRegularUpdatesDTO(regularUpdates);
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
        regularUpdates.setModifiedAt(regularUpdatesDetails.getModifiedAt());
        regularUpdates.setLocalDate(regularUpdatesDetails.getLocalDate());
        RegularUpdates regularUpdatesUpdated =  regularUpdatesRepository.save(regularUpdates);
        return mapToRegularUpdatesDTO(regularUpdatesUpdated);
    }
    public void deleteRegularUpdates(Long id){
        RegularUpdates regularUpdates = regularUpdatesRepository.findById(id).orElseThrow(()-> new CustomException("cet update n'existe pas", HttpStatus.NOT_FOUND));
        regularUpdatesRepository.delete(regularUpdates);
    }
}
