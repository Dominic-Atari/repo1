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
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
	        System.out.println("Authentication successful---------------");
	        System.out.println("Authorities: =======================" + authentication.getAuthorities());
	        String role = authentication.getAuthorities().stream()
	                .map(GrantedAuthority::getAuthority)
	                .findFirst()
	                .orElse("");
	        System.out.println("Role:------------------- " + role);
	        System.out.println("Role TEACHER: -----------------" + Role.TEACHER.name());
	        System.out.println("Role STUDENT:-------------------- " + Role.STUDENT.name());
	        System.out.println("Role equals TEACHER: ----------------" + role.equals(Role.TEACHER.name()));
	        System.out.println("Role equalsIgnoreCase TEACHER: -------------------" + role.equalsIgnoreCase(Role.TEACHER.name()));
	        if (role.equalsIgnoreCase("ROLE_" + Role.TEACHER.name())) {
	            System.out.println("Redirecting to teacher dashboard--------------------------");
	            response.sendRedirect("/dashboard");
	        } else if (role.equalsIgnoreCase("ROLE_" + Role.STUDENT.name())) {
	            System.out.println("Redirecting to student index-------------------");
	            response.sendRedirect("/index");
	        } else {
	            System.out.println("Redirecting to default page-----------------");
	            response.sendRedirect("/");
	        }
	    }
	}