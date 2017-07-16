package qa.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import qa.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);


}
