package com.letsbuild.accounts.repository;

import com.letsbuild.accounts.entity.Accounts;
import com.letsbuild.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts,Long> {
    Accounts findByCustomerId(Long customerId);

    void deleteByCustomerId(Long customerId);
}
