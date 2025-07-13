package com.promineotech.mystore2.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.promineotech.mystore2.model.Student;
import com.promineotech.mystore2.model.Teacher;
import com.promineotech.mystore2.service.ProfileService;

@Controller
public class ProfileController {

	// Autowire the ProfileService to interact with the profile service layer
    @Autowired
    private ProfileService profileService;

    /**
     * This method uploads a profile picture for the logged-in user.
     * 
     * @param file The MultipartFile object representing the profile picture
     * @param principal The Principal object representing the logged-in user
     * @return A redirect to the profile page
     */
    @PostMapping("/profile/upload-picture")
    public String uploadProfilePicture(@RequestParam("file") MultipartFile file, Principal principal) {
        profileService.uploadProfilePicture(file, principal);
        return "redirect:/profile";
    }

    /**
     * This method retrieves the profile picture of the logged-in user.
     * 
     * @param principal The Principal object representing the logged-in user
     * @return A ResponseEntity object containing the profile picture
     */
    @GetMapping("/profile/picture")
    public ResponseEntity<byte[]> getProfilePicture(Principal principal) {
        Object user = profileService.getLoggedInUser(principal);
        if (user instanceof Student) {
            Student appUser = (Student) user;
            if (appUser.getProfilePicture() != null) {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(appUser.getProfilePicture());
            }
        } else if (user instanceof Teacher) {
            Teacher teacher = (Teacher) user;
            if (teacher.getProfilePicture() != null) {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(teacher.getProfilePicture());
            }
        }
        return ResponseEntity.notFound().build();
    }

}
