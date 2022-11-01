package com.app.demo.security.account.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.demo.model.Accounts;
import com.app.demo.repository.AccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Accounts account = accountRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Account with current username cannot be found: "+username));

        return UserDetailsImpl.build(account);
    }
}
