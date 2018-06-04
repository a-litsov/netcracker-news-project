package com.netcracker.adlitsov.newsproject.authserver.repository;

import com.netcracker.adlitsov.newsproject.authserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findByAuthority(String authority);
}
