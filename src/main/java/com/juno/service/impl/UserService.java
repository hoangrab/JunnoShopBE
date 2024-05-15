package com.juno.service.impl;

import com.juno.DTO.UserDTO;
import com.juno.entity.Role;
import com.juno.entity.User;
import com.juno.entity.Wishlist;
import com.juno.exception.ResourceAlreadyExitsException;
import com.juno.exception.ResourceNotFoundException;
import com.juno.model.CategoryModel;
import com.juno.model.CustomUserDetail;
import com.juno.model.UserModel;
import com.juno.repository.RoleRepo;
import com.juno.repository.UserRepo;
import com.juno.repository.WishlistRepo;
import com.juno.request.GoogleRequest;
import com.juno.request.LoginRequest;
import com.juno.request.RegisterRequest;
import io.jsonwebtoken.Jwt;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CloudinaryService cloudinaryService;
    private final AuthenticationManager authenticationManager;
    private final WishlistRepo wishlistRepo;

    public Map<String, Object> login(LoginRequest loginRequest) {
        Map<String,Object> map = new HashMap<>();
        User u = userRepo.findByGmail(loginRequest.getUserName()).
                orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken((CustomUserDetail) userDetails);
        map.put("accessToken",jwt);
        map.put("user",convertEntityToModel(u));
        return map;
    }

    public void register(RegisterRequest registerRequest) {
        if(userRepo.findByGmail(registerRequest.getUserName()).isPresent()){
            throw new ResourceAlreadyExitsException("UserName is already in use");
        }
        User user = new User();
        user.setFullName(registerRequest.getFullName());
        user.setGmail(registerRequest.getUserName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Role role = roleRepo.findByName("ROLE_USER").get();
        user.setRole(role);
        user.setEnabled(true);
        Wishlist wishlist = new Wishlist();
        wishlistRepo.save(wishlist);
        user.setWishlist(wishlist);
        userRepo.save(user);
        wishlist.setUser(user);
        wishlistRepo.save(wishlist);
    }

    @Transactional
    public Map<String,Object> loginAuth2(GoogleRequest googleRequest) {
        Map<String,Object> map = new HashMap<>();
        if(userRepo.findByGmail(googleRequest.getEmail()).isEmpty()) {
            User user = new User();
            user.setGmail(googleRequest.getEmail());
            user.setPassword(passwordEncoder.encode("passDefault"));
            user.setAvatar(googleRequest.getPicture());
            user.setFullName(googleRequest.getName());
            Role role = roleRepo.findByName("ROLE_USER").get();
            user.setRole(role);
            user.setEnabled(true);
            Wishlist wishlist = new Wishlist();
            wishlistRepo.save(wishlist);
            user.setWishlist(wishlist);
            userRepo.save(user);
            wishlist.setUser(user);
            wishlistRepo.save(wishlist);
        }
        User u = userRepo.findByGmail(googleRequest.getEmail()).get();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(googleRequest.getEmail(),"passDefault"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken((CustomUserDetail) userDetails);
        map.put("accessToken",jwt);
        map.put("user",convertEntityToModel(u));
        return map;
    }

    public List<UserModel> getAll(Pageable pageable) {
        return userRepo.findAll(pageable).stream().map(this::convertEntityToModel).collect(Collectors.toList());
    }

    public UserModel getById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return convertEntityToModel(user);
    }

    public void createUser(UserDTO userDTO) throws IOException {
        if(userRepo.findByGmail(userDTO.getGmail()).isPresent()) {
            throw new ResourceAlreadyExitsException("UserName is already in use");
        }
        User user = new User();
        convertDtoToEntity(userDTO, user);
        Wishlist wishlist = new Wishlist();
        wishlistRepo.save(wishlist);
        user.setWishlist(wishlist);
        userRepo.save(user);
    }

    @Transactional
    public void updateUser(UserDTO userDTO,Long id) throws IOException {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if(user.getAvatar()!=null){
            cloudinaryService.delete(user.getUrl_id());
        }
        convertDtoToEntity(userDTO,user);
        userRepo.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        Optional<User> user = Optional.ofNullable(userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        userRepo.deleteById(id);
    }

    public void convertDtoToEntity(UserDTO userDTO, User user) throws IOException {
        user.setFullName(userDTO.getFullName());
        user.setGmail(userDTO.getGmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(userDTO.isEnabled());
        user.setRole(roleRepo.findByName("ROLE_USER").get());
        user.setCity(userDTO.getCity());
        user.setDistrict(userDTO.getDistrict());
        user.setAddressDetail(userDTO.getAddressDetail());
        user.setWard(userDTO.getWard());
        if(userDTO.getAvatar() != null) {
            Map data = cloudinaryService.upload(userDTO.getAvatar());
            user.setAvatar(data.get("url").toString());
            user.setUrl_id(data.get("public_id").toString());
        }
    }

    public UserModel convertEntityToModel(User user) {
        return new UserModel(user.getId(),user.getAvatar(),user.getFullName(),user.getGmail(),user.getPhone(),user.getCity(),user.getDistrict(),
                user.getWard(),user.getAddressDetail(),user.isEnabled(),user.getRole());
    }

}
