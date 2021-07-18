package org.test.model.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import java.util.List;

import org.test.model.domain.Account;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account>{
    public List<Account> findAllAccounts(){
        return this.findAll().list();
    }

    public Account findByUserId(String userId){
        return this.find("userId", userId).firstResult();
    }
    @Transactional
    public Account persistAndReturn(Account account){
        Account newAccount = null;
        this.persist(account);
        newAccount = this.find("userId",account.getUserId()).firstResult();
        return newAccount;
    }

    
}
