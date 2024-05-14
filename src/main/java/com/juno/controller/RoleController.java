package com.juno.controller;

import com.juno.DTO.RoleDTO;
import com.juno.entity.Role;
import com.juno.service.impl.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("roles")
    public ResponseEntity<?> getRoles() {
        try {
            List<Role> list = roleService.findAll();
            return ResponseEntity.ok(list);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("role")
    public ResponseEntity<?> addRole(@RequestBody RoleDTO role) {
        try {
            roleService.create(role);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("role/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody RoleDTO role) {
        try {
            roleService.update(role,id);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("role/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        try {
            roleService.delete(id);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
