package com.promineotech.mystore2.service;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.promineotech.mystore2.model.Student;
import com.promineotech.mystore2.model.Teacher;
import com.promineotech.mystore2.repository.AppUserRepository;
import com.promineotech.mystore2.repository.TeacherRepository;
@Service
public class ProfileService {

    @Autowired
    private AppUserRepository repo;
    @Autowired
    private TeacherRepository teacherRepo;

    public String getLoggedInUserFirstName(Principal principal) {
        if (principal == null) {
            return "Unknown User";
        }
        Object user = getLoggedInUser(principal);
        if (user instanceof Student) {
            return ((Student) user).getFirstName();
        } else if (user instanceof Teacher) {
            return ((Teacher) user).getFirstName();
        } else {
            return "Unknown User";
        }
    }

    public Object getLoggedInUser(Principal principal) {
        Student appUser = repo.findByEmail(principal.getName());
        if (appUser != null) {
            return appUser;
        }
        return teacherRepo.findByEmail(principal.getName());
    }

    public void uploadProfilePicture(MultipartFile file, Principal principal) {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to upload profile picture: file is empty");
        }
        if (!file.getContentType().startsWith("image/")) {
            throw new RuntimeException("Only images are allowed");
        }
        Object user = getLoggedInUser(principal);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        try {
            if (user instanceof Student) {
                ((Student) user).setProfilePicture(file.getBytes());
                repo.save((Student) user);
            } else if (user instanceof Teacher) {
                ((Teacher) user).setProfilePicture(file.getBytes());
                teacherRepo.save((Teacher) user);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload profile picture", e);
        }
    }
}
