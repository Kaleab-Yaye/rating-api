package com.rating.api.config.security;

import com.rating.api.repository.PharmacistRepo;
import com.rating.api.service.security.FindUserDetails;
import com.rating.api.service.security.JwtService;
import com.rating.api.service.users.alluser.GetEmail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final FindUserDetails findUserDetails;
  private final GetEmail getEmail;

  // must be fixed for now just to check

  JwtAuthenticationFilter(
      JwtService jwtService,
      FindUserDetails findUserDetails,
      PharmacistRepo pharmacistRepo,
      GetEmail getEmail) {
    this.jwtService = jwtService;
    this.findUserDetails = findUserDetails;
    this.getEmail = getEmail;
  }

  @Override
  protected void doFilterInternal( // every filter on the chain has to do this,
      @NonNull HttpServletRequest request, // this is the reqeust body that the cerverlet sends
      @NonNull
          HttpServletResponse
              response, //  this is a response body that the cerveralte has to pupulate
      @NonNull FilterChain filterChain // this is the filter chain,
      ) throws ServletException, IOException {

    String authHeader =
        request.getHeader("Authorization"); // this is a just a minor optmiztion, that is optional
    if (authHeader == null || !authHeader.startsWith("Bearer ")) { // make sure autherization
      filterChain.doFilter(
          request,
          response); // if the outrization doesn't have to do any thign to do with tokens then this
      // will happ// n
      return;
    }

    String jwt = authHeader.substring(7); // we wan to remove the bearer heading
    String username = jwtService.extractUsername(jwt);
    UUID uuid = UUID.fromString(username);
    if (SecurityContextHolder.getContext().getAuthentication()
        == null) { // we can only work if the user email is presetn and the Secruity context is
      // empte
      // then now we load the user from the data base

      String email = getEmail.getEmail(uuid);

      UserDetails userDetails = findUserDetails.loadUserByUsername(email);
      // validate the token it migh get expired or tempered with
      if (jwtService.isTokenValid(jwt, userDetails)) { // if valide >>>

        SecurityContextHolder.getContext()
            .setAuthentication(
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities())); // now the authToken is set
      }
    }

    filterChain.doFilter(
        request, response); // the chain must go on! ( othere aoutrization mybe needed)
  }
}
