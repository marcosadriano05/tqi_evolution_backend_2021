package br.com.tqi.evolution.repositories;

import br.com.tqi.evolution.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName (String name);
}
