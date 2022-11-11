// package com.app.demo.security.client.details;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// import com.app.demo.security.account.details.UserDetailsServiceImpl;

// import lombok.AllArgsConstructor;

// @Component
// @AllArgsConstructor
// public class ClientAuthProvider implements AuthenticationProvider{

//     @Autowired
//     private final UserDetailsServiceImpl userDetailsService;
//     @Autowired
//     private final PasswordEncoder passwordEncoder;

//     @Override
//     public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//         String username = authentication.getName();
//         String password = authentication.getCredentials().toString();

//         UserDetails userDetails = userDetailsService.loadUserByUsername(username);

//         if(userDetails != null){
//             if(passwordEncoder.matches(password, userDetails.getPassword())){
//                 return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
//             }
//         }
        
//         throw new BadCredentialsException("Invalid credentials");
//     }

//     @Override
//     public boolean supports(Class<?> authentication) {
//         return authentication.equals(UsernamePasswordAuthenticationToken.class);
//     }
    
    
// }

