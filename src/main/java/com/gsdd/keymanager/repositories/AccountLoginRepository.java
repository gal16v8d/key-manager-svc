package com.gsdd.keymanager.repositories;

import com.gsdd.keymanager.entities.Account;
import com.gsdd.keymanager.entities.AccountLogin;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountLoginRepository extends JpaRepository<AccountLogin, Long> {

  List<AccountLogin> findByAccount(Account account);
}
