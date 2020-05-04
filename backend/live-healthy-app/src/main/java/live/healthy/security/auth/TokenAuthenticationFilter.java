package live.healthy.security.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import live.healthy.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


//Filter koji ce presretati svaki zahtev klijenta ka serveru
//Sem nad putanjama navedenim u WebSecurityConfig.configure(WebSecurity web)
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private TokenUtils tokenUtils;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String username;
		String authToken = tokenUtils.getToken(request);

		if (authToken != null) {
			username = tokenUtils.getUsernameFromToken(authToken);

			if (username != null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				if (tokenUtils.validateToken(authToken, userDetails)) {
					TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
					authentication.setToken(authToken);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}

		chain.doFilter(request, response);
	}

}