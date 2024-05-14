package com.juno.controller;

import com.juno.DTO.CategoryDTO;
import com.juno.DTO.UserDTO;
import com.juno.exception.ResourceAlreadyExitsException;
import com.juno.exception.ResourceNotFoundException;
import com.juno.model.UserModel;
import com.juno.repository.UserRepo;
import com.juno.service.impl.CloudinaryService;
import com.juno.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepo userRepo;
    private final CloudinaryService cloudinaryService;
//    private static final String GOOGLE_USERINFO_ENDPOINT = "https://www.googleapis.com/oauth2/v3/userinfo";
//
//    @GetMapping("userInfo")
//    public ResponseEntity<String> getUserInfo(@RequestParam("token") String idToken) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(idToken);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.exchange(
//                GOOGLE_USERINFO_ENDPOINT,
//                HttpMethod.GET,
//                null,
//                String.class,
//                headers
//        );
//        return response;
//    }

    @GetMapping("users")
    public ResponseEntity<?> getAllUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<UserModel> list = userService.getAll(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        try {
            UserModel userModel = userService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("user")
    public ResponseEntity<?> createUser(@ModelAttribute("user")UserDTO userDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body("ok nha cu");
        }
        catch (ResourceAlreadyExitsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
        catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("user/{id}")
    public ResponseEntity<?> updateUser(@ModelAttribute("user")UserDTO userDTO,
                                        @PathVariable("id") Long id) {
        try {
            userService.updateUser(userDTO,id);
            return ResponseEntity.status(HttpStatus.OK).body("ok nha cu");
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body("delete ok");
        }catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

}
