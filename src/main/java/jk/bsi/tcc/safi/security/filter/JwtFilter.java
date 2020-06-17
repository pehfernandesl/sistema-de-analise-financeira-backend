package jk.bsi.tcc.safi.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jk.bsi.tcc.safi.service.SafiUserDetailsService;
import jk.bsi.tcc.safi.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  private final TokenService tokenService;
  private final SafiUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                  HttpServletResponse httpServletResponse,
                                  FilterChain filterChain) throws ServletException, IOException {
    final String header = httpServletRequest.getHeader(tokenService.getAuthorizationHeader());

    String token = null;
    String email = null;

    if (tokenService.isHeaderValid(header)) {
      token = Arrays.asList(header.split(" ")).get(tokenService.getTokenIndex());
      email = tokenService.extractSubject(token);
    }

    if (Objects.nonNull(email) &&
      Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(email);

      if (tokenService.validateToken(token, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
          .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
