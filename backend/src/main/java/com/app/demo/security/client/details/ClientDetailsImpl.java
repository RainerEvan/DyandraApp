// package com.app.demo.security.client.details;

// import java.util.Collection;
// import java.util.UUID;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import com.app.demo.model.Applications;
// import com.app.demo.model.Reports;
// import com.fasterxml.jackson.annotation.JsonIgnore;

// import lombok.AllArgsConstructor;
// import lombok.Data;

// @Data
// @AllArgsConstructor
// public class ClientDetailsImpl implements UserDetails{
//     private UUID id;
//     private String clientId;
//     @JsonIgnore
//     private String reportId;
//     private Collection<? extends GrantedAuthority> authorities;

//     public static ClientDetailsImpl build(Reports report){

//         Applications application = report.getConnection().getApplication();

//         return new ClientDetailsImpl(
//             application.getId(),
//             application.getClientId(),
//             report.getReportId(),
//             null
//         );
//     }

//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         return authorities;
//     }

//     @Override
//     public String getPassword() {
//         return reportId;
//     }

//     @Override
//     public String getUsername() {
//         return clientId;
//     }

//     @Override
//     public boolean isAccountNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isAccountNonLocked() {
//         return true;
//     }

//     @Override
//     public boolean isCredentialsNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isEnabled() {
//         return true;
//     }
// }
