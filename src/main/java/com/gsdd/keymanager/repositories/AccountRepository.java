package com.gsdd.keymanager.repositories;

import com.gsdd.keymanager.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  Account findByLogin(String login);

  Account findByLoginAndPassword(String login, String password);
}
