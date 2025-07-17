package com.promineotech.mystore2.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.promineotech.mystore2.service.StudentService;
import com.promineotech.mystore2.service.TeacherService;

public class CustomUserDetailsService implements UserDetailsService {

	private final StudentService studentService;
    private final TeacherService teacherService;

    @Autowired
    public CustomUserDetailsService(StudentService appUserService, TeacherService teacherService) {
        this.studentService = appUserService;
        this.teacherService = teacherService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        try {
            userDetails = studentService.loadUserByUsername(username);
            authorities.add(new SimpleGrantedAuthority("STUDENT"));
        } catch (UsernameNotFoundException e) {
            try {
                userDetails = teacherService.loadUserByUsername(username);
                authorities.add(new SimpleGrantedAuthority("TEACHER"));
            } catch (UsernameNotFoundException ex) {
                throw new UsernameNotFoundException("User not found");
            }
        }
        
        return new org.springframework.security.core.userdetails.User(userDetails.getUsername(), userDetails.getPassword(), authorities);
    }
}
