package online.gemfpt.BE.Repository;

import online.gemfpt.BE.entity.Account;
import online.gemfpt.BE.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthenticationRepository  extends JpaRepository<Account,Long> {
    Account findAccountByEmail (String email);




   // List<Account> findAccounts();
            // lấy 1 account thì find account - lấy 1 danh sách thì thêm s

}
