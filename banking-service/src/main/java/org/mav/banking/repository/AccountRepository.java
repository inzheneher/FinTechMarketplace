package org.mav.banking.repository;

import org.mav.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);

    boolean existsBySsn(String ssn);

    Account findByEmail(String email);
}
