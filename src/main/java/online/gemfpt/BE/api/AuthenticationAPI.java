package online.gemfpt.BE.api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.gemfpt.BE.entity.Account;
import online.gemfpt.BE.Service.AuthenticationService;
import online.gemfpt.BE.enums.RoleEnum;
import online.gemfpt.BE.exception.AccountNotFoundException;
import online.gemfpt.BE.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// nhan request tu fontend
@RestController
@SecurityRequirement(name="api")
@CrossOrigin("*")
public class AuthenticationAPI {
    @Autowired
    AuthenticationService authenticationService;

     @PostMapping("/createAccount")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = authenticationService.createAccount(request);
        return ResponseEntity.ok(account);
    }
       @PostMapping("/login")
    public ResponseEntity login (@RequestBody LoginRequest loginRequest){
        Account account = authenticationService.login(loginRequest);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/register")
    public ResponseEntity Register (@RequestBody RegisterRequest responseRequest){
        Account  account = authenticationService.register(responseRequest);
        return  ResponseEntity.ok(account);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity Getallaccount (){
         // Lấy thông tin người dùng hiện tại từ context
        Account currentAccount = authenticationService.getCurrentAccount();

        // Kiểm tra xem người dùng hiện tại có vai trò là ADMIN hay không
        if (currentAccount.getRole() != RoleEnum .ADMIN) {
            // Nếu không phải ADMIN, trả về lỗi hoặc xử lý phù hợp
            return ResponseEntity.status(HttpStatus .FORBIDDEN).build();
        }

        List<Account> account = authenticationService.all();
        return  ResponseEntity.ok(account);
    }


     @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        try {
            Account account = authenticationService.getAccountById(id);
            return ResponseEntity.ok(account);
        } catch (AccountNotFoundException  e) {
            return ResponseEntity.notFound().build();
        }
    }


     @GetMapping("/email/{email}")
    public ResponseEntity<Account> getAccountByEmail(@PathVariable String email) {
        try {
            Account account = authenticationService.getAccountByEmail(email);
            return ResponseEntity.ok(account);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
