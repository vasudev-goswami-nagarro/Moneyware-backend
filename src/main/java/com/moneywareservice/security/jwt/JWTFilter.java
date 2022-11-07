package com.moneywareservice.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {
  public static final String AUTHORIZATION_HEADER = "Authorization";

  @Autowired
  private TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain) throws ServletException, IOException {
    log.info("=======================Filtering request============================");
    log.info("======================Request URI: " + httpServletRequest.getRequestURI());
    String jwt = resolveToken(httpServletRequest);
    if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
      Authentication authentication = this.tokenProvider.getAuthentication(jwt);
      log.info("======================User is validated: Principal: "
          + authentication.getPrincipal());
      SecurityContextHolder
          .getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
