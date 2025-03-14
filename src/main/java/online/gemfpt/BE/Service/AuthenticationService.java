package online.gemfpt.BE.Service;

import online.gemfpt.BE.entity.Account;
import online.gemfpt.BE.Repository.AuthenticationRepository;
import online.gemfpt.BE.enums.RoleEnum;
import online.gemfpt.BE.exception.AccountNotFoundException;
import online.gemfpt.BE.exception.BadRequestException;
import online.gemfpt.BE.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Account register(RegisterRequest registerRequest) {
    if (registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty() ||
            registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty()) {
        throw new BadRequestException("Missing required fields");
    }

    // Kiểm tra xem role có được cung cấp và có phải là HOCSINH hoặc PHUHUYNH không
    if (registerRequest.getRole() == null ||
        !(registerRequest.getRole() == RoleEnum.HOCSINH || registerRequest.getRole() == RoleEnum.PHUHUYNH)) {
        throw new BadRequestException("Invalid role. Only HOCSINH and PHUHUYNH roles are allowed.");
    }

    Account account = new Account();
    account.setEmail(registerRequest.getEmail());
    account.setRole(registerRequest.getRole());
    account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    System.out.println("Registering: " + registerRequest.getEmail());

    return authenticationRepository.save(account);
}


    public Account login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            ));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid email or password");
        }

        Account account = authenticationRepository.findAccountByEmail(loginRequest.getEmail());
        if (account == null) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = tokenService.generateToken(account);
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setEmail(account.getEmail());
        accountResponse.setToken(token);
        accountResponse.setId(account.getId());
        accountResponse.setRole(account.getRole());

        return accountResponse;
    }

    public List<Account> all() {
        return authenticationRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return authenticationRepository.findAccountByEmail(email);
    }

    public Account getCurrentAccount() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().stream()
                    .findFirst()
                    .map(Object::toString)
                    .orElse("ROLE_NOT_FOUND");
        }
        return "ROLE_NOT_FOUND";
    }

    private boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals(role));
        }
        return false;
    }

    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    public boolean isManager() {
        return hasRole("MANAGER");
    }

    public boolean isCurrentAccount(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername().equals(email);
        }
        return false;
    }

    public Account getAccountById(Long id) {
        return authenticationRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id));
    }

    public Account getAccountByEmail(String email) {
        Account account = authenticationRepository.findAccountByEmail(email);
        if (account == null) {
            throw new AccountNotFoundException("Account not found with email: " + email);
        }
        return account;
    }


public Account updateAccountRoleByEmail(UpdateAccountRoleByEmailRequest request) {
    Account account = authenticationRepository.findAccountByEmail(request.getEmail());
    if (account == null) {
        throw new AccountNotFoundException("Account not found with email: " + request.getEmail());
    }
    account.setRole(request.getRole());
    return authenticationRepository.save(account);
}



//     public Account createAccount(CreateAccountRequest request) {
//        if (request.getEmail() == null || request.getEmail().isEmpty() ||
//            request.getPassword() == null || request.getPassword().isEmpty() ||
//            request.getName() == null || request.getName().isEmpty()) {
//            throw new BadRequestException("Missing required fields");
//        }
//
//        if (authenticationRepository.findAccountByEmail(request.getEmail()) != null) {
//            throw new BadRequestException("Email already exists");
//        }
//
//        Account account = new Account();
//        account.setEmail(request.getEmail());
//        account.setPassword(passwordEncoder.encode(request.getPassword()));
//        account.setName(request.getName());
//        account.setRole(RoleEnum.STAFF);
//
//        return authenticationRepository.save(account);
//    }
}
