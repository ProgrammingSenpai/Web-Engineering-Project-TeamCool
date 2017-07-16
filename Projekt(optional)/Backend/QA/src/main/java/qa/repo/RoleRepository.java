package qa.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import qa.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
