package online.gemfpt.BE.Controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.gemfpt.BE.Service.SurveyService;
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

    @Autowired
    private SurveyService surveyService;

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

    @GetMapping("/test")
public ResponseEntity<String> testEndpoint() {
    return ResponseEntity.ok("test");
}

//-------------------------------------------------//
 @PostMapping("/creatervey")
    public ResponseEntity<String> createSurvey(@RequestBody SurveyRequest request) {
        surveyService.createSurvey(request);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/get-all-sevey")
    public ResponseEntity<List<SurveyResponse>> getAllSurveys() {
        List<SurveyResponse> responses = surveyService.getAllSurveys();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-sevey{id}")
    public ResponseEntity<SurveyResponse> getSurveyById(@PathVariable Long id) {
        SurveyResponse response = surveyService.getSurveyById(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

      @PutMapping("/Update-servey-{id}")
    public ResponseEntity<SurveyResponse> updateSurvey(@PathVariable Long id, @RequestBody SurveyRequest request) {
        SurveyResponse response = surveyService.updateSurvey(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Xóa một khảo sát theo ID.
     * @param id ID của khảo sát cần xóa.
     * @return "OK" nếu xóa thành công.
     */
    @DeleteMapping("/deleteservey{id}")
    public ResponseEntity<String> deleteSurvey(@PathVariable Long id) {
        String result = surveyService.deleteSurvey(id);
        return ResponseEntity.ok(result);
    }


}
