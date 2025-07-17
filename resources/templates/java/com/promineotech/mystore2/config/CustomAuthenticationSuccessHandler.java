package com.promineotech.mystore2.config;

import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.promineotech.mystore2.role.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst()
				.orElse("");

		if (role.equalsIgnoreCase("ROLE_" + Role.TEACHER.name())) {

			response.sendRedirect("/dashboard");
		} else if (role.equalsIgnoreCase("ROLE_" + Role.STUDENT.name())) {

			response.sendRedirect("/index");
		} else {

			response.sendRedirect("/");
		}
	}
}