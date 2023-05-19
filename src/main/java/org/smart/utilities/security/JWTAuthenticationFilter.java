package org.smart.utilities.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JWTGenerator tokenGenerator;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    if (request.getMethod().equals("OPTIONS")) {
      response.setStatus(HttpServletResponse.SC_OK);
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
      response.setHeader("Access-Control-Max-Age", "3600");
      response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
    } else{

    String token = tokenGenerator.getJWTFromRequest(request);

    if (StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
      var username = tokenGenerator.getUsernameFromJWT(token);
      var userDetails = customUserDetailsService.loadUserByUsername(username);
      var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
          userDetails.getAuthorities());

      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    filterChain.doFilter(request, response);}
  }
}
