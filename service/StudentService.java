package com.promineotech.mystore2.service;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.promineotech.mystore2.model.Student;
import com.promineotech.mystore2.model.Teacher;
import com.promineotech.mystore2.repository.AppUserRepository;
import com.promineotech.mystore2.repository.TeacherRepository;

@Service
public class StudentService implements UserDetailsService{

	// Autowire the AppUserRepository to interact with the Student database table
    @Autowired
    private AppUserRepository repo;

    // Autowire the TeacherRepository to interact with the Teacher database table
    @Autowired
    private TeacherRepository teacherRepo;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student appUser = repo.findByEmail(email);
        if (appUser != null) {
            return buildUserDetails(appUser.getEmail(), appUser.getPassword(), appUser.getRole());
        }
        Teacher teacher = teacherRepo.findByEmail(email);
        if (teacher != null) {
            return buildUserDetails(teacher.getEmail(), teacher.getPassword(), teacher.getRole());
        }
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    private UserDetails buildUserDetails(String email, String password, String role) {
        return User.withUsername(email)
                .password(password)
                .roles(role)
                .build();
    }

    /**
     * This method returns the first name of the logged-in user.
     * 
     * @param principal The Principal object representing the logged-in user
     * @return The first name of the logged-in user
     */
    public String getLoggedInUserFirstName(Principal principal) {
        // Check if the principal object is null
        if (principal == null) {
            // If principal is null, return "Unknown User"
            return "Unknown User";
        }

        // Get the logged-in user object
        Object user = getLoggedInUser(principal);

        // Check if the user object is an instance of Student or Teacher
        if (user instanceof Student) {
            // If user is an Student, return their first name
            return ((Student) user).getFirstName();
        } else if (user instanceof Teacher) {
            // If user is a Teacher, return their first name
            return ((Teacher) user).getFirstName();
        } else {
            // If user is not found, return "Unknown User"
            return "Unknown User";
        }
    }

    /**
     * This method returns the logged-in user object.
     * 
     * @param principal The Principal object representing the logged-in user
     * @return The logged-in user object
     */
    public Object getLoggedInUser(Principal principal) {
        // Check if the user is an Student
        Student appUser = repo.findByEmail(principal.getName());
        if (appUser != null) {
            // If user is an Student, return the Student object
            return appUser;
        }

        // If user is not an Student, check if they are a Teacher
        return teacherRepo.findByEmail(principal.getName());
    }

    /**
     * This method uploads a profile picture for the logged-in user.
     * 
     * @param file The MultipartFile object representing the profile picture
     * @param principal The Principal object representing the logged-in user
     */
    public void uploadProfilePicture(MultipartFile file, Principal principal) {
        // Check if the file is empty
        if (file.isEmpty()) {
            // If file is empty, throw a RuntimeException
            throw new RuntimeException("Failed to upload profile picture: file is empty");
        }

        // Check if the file is an image
        if (!file.getContentType().startsWith("image/")) {
            // If file is not an image, throw a RuntimeException
            throw new RuntimeException("Only images are allowed");
        }

        // Get the logged-in user object
        Object user = getLoggedInUser(principal);

        // Check if the user object is null
        if (user == null) {
            // If user is null, throw a RuntimeException
            throw new RuntimeException("User not found");
        }

        try {
            // Set the profile picture for the user
            if (user instanceof Student) {
                ((Student) user).setProfilePicture(file.getBytes());
                repo.save((Student) user);
            } else if (user instanceof Teacher) {
                ((Teacher) user).setProfilePicture(file.getBytes());
                teacherRepo.save((Teacher) user);
            }
        } catch (IOException e) {
            // If an IOException occurs, throw a RuntimeException
            throw new RuntimeException("Failed to upload profile picture", e);
        }
    }
}