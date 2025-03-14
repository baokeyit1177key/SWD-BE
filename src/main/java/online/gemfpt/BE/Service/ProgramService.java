package online.gemfpt.BE.service;

import online.gemfpt.BE.model.ProgramRequest;
import online.gemfpt.BE.model.ProgramResponse;
import online.gemfpt.BE.entity.Account;
import online.gemfpt.BE.entity.Program;
import online.gemfpt.BE.Repository.AuthenticationRepository;
import online.gemfpt.BE.Repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramService {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private AuthenticationRepository accountRepository;

    public ProgramResponse createProgram(ProgramRequest request) {
        Program program = new Program();
        program.setName(request.getName());
        program.setDescription(request.getDescription());
        program.setStartDate(request.getStartDate());
        program.setEndDate(request.getEndDate());
        program.setCreatedAt(LocalDateTime.now());
        program.setUpdatedAt(LocalDateTime.now());

        Program savedProgram = programRepository.save(program);
        return mapToResponse(savedProgram);
    }

    public List<ProgramResponse> getAllPrograms() {
        return programRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProgramResponse getProgramById(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found"));
        return mapToResponse(program);
    }

    public ProgramResponse updateProgram(Long id, ProgramRequest request) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        program.setName(request.getName());
        program.setDescription(request.getDescription());
        program.setStartDate(request.getStartDate());
        program.setEndDate(request.getEndDate());
        program.setUpdatedAt(LocalDateTime.now());

        Program updatedProgram = programRepository.save(program);
        return mapToResponse(updatedProgram);
    }

    public void deleteProgram(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found"));
        programRepository.delete(program);
    }

    public ProgramResponse registerProgram(Long programId, Long userId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found"));
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Account> participants = program.getParticipants();
        if (!participants.contains(account)) {
            participants.add(account);
            program.setParticipants(participants);
            Program updatedProgram = programRepository.save(program);
            return mapToResponse(updatedProgram);
        }
        return mapToResponse(program);
    }

    public List<ProgramResponse> getProgramsByUserId(Long userId) {
        return programRepository.findByParticipantsId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProgramResponse mapToResponse(Program program) {
        ProgramResponse response = new ProgramResponse();
        response.setId(program.getId());
        response.setName(program.getName());
        response.setDescription(program.getDescription());
        response.setStartDate(program.getStartDate());
        response.setEndDate(program.getEndDate());
        response.setParticipantCount(program.getParticipants() != null ? program.getParticipants().size() : 0);
        response.setCreatedAt(program.getCreatedAt());
        response.setUpdatedAt(program.getUpdatedAt());
        return response;
    }
}