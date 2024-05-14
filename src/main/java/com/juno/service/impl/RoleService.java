package com.juno.service.impl;

import com.juno.DTO.RoleDTO;
import com.juno.entity.Role;
import com.juno.exception.ResourceAlreadyExitsException;
import com.juno.exception.ResourceNotFoundException;
import com.juno.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepo roleRepo;

    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    public Role findById(Long id) {
        return roleRepo.findById(id).get();
    }

    @Transactional
    public void create(RoleDTO roleDTO) {
        if(roleRepo.findByName(roleDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExitsException("Conflict name role ");
        }
        Role role = new Role();
        role.setName(roleDTO.getName());
        roleRepo.save(role);
    }

    @Transactional
    public void update(RoleDTO roleDTO, Long id) {
        Role role = roleRepo.findById(id).get();
        if(!role.getName().equals(roleDTO.getName()) && roleRepo.findByName(roleDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExitsException("Conflict name role ");
        }
        role.setName(roleDTO.getName());
        roleRepo.save(role);
    }

    @Transactional
    public void delete(Long id) {
        roleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }
}
