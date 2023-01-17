package com.project.dyandra.security.account.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.dyandra.model.Accounts;
import com.project.dyandra.repository.AccountRepository;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Accounts account = accountRepository.findByUserId(userId)
            .orElseThrow(() -> new UsernameNotFoundException("Account with current user id cannot be found: "+userId));

        return AccountDetailsImpl.build(account);
    }
}
