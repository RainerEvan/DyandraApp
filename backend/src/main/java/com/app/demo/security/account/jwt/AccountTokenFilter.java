// package com.app.demo.security.account.jwt;

// import java.io.IOException;

// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;


// import org.springframework.web.filter.OncePerRequestFilter;

// import com.app.demo.security.account.details.UserDetailsServiceImpl;

// import lombok.extern.slf4j.Slf4j;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.util.StringUtils;


// @Slf4j
// public class AccountTokenFilter extends OncePerRequestFilter{
//     @Autowired
//     private AccountJwtUtils jwtUtils;
//     @Autowired
//     private UserDetailsServiceImpl userDetailsService;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//         throws ServletException, IOException {
//         try {
//             log.info("Account Auth - Request URL path: {}, Request content type: {}",request.getRequestURI(), request.getContentType());
//             String jwt = parseJwt(request);
//             if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//                 String username = jwtUtils.getUserNameFromJwtToken(jwt);
//                 UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null,
//                     userDetails.getAuthorities());
//                 authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                 SecurityContextHolder.getContext().setAuthentication(authentication);
//                 log.info("Successfully set account authentication: {}", username);
//             }
//         } catch (Exception e) {
//             log.error("Cannot set account authentication: {}", e);
//         }
//         filterChain.doFilter(request, response);
//     }

//     // @Override
//     // protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//     //     String path = request.getRequestURI();

//     //     return !path.contains("/admin");
//     // }

//     private String parseJwt(HttpServletRequest request) {
//         String headerAuth = request.getHeader("Authorization");
//         if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//           return headerAuth.substring(7, headerAuth.length());
//         }
//         return null;
//     }
// }
