package org.vacation.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.vacation.beans.UserDto;
import org.vacation.services.JwtUserService;
import org.vacation.services.LdapDirectoryService;
import org.vacation.utils.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUserService userService;
	
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LdapDirectoryService ldapDirectoryService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // first time user authenticate or token expired.
            UserDto userDetails = this.userService.loadUserByUsername(username);


            if (jwtUtil.validateToken(jwt, userDetails)) {
                /**
                 * TODO let use ldapTemplate to retrieve the roles from ldif.
                 */
                List<GrantedAuthority> authorities = toGrantedAuthorities(ldapDirectoryService.getUserRoleFromLdif(username));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    private List<GrantedAuthority> toGrantedAuthorities(List<String> userRoles) {
        return userRoles.stream()
                    .map( role -> {
                        String rolePrefix = "ROLE_";
                        return new SimpleGrantedAuthority(rolePrefix + role);
                    }).collect(toList());
    }

}
