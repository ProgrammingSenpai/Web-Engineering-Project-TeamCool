package qa.init;

import qa.model.Role;
import qa.model.User;
import qa.repo.RoleRepository;
import qa.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserInit implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserInit(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void run(String... strings) throws Exception {
        Role adminRole = new Role();
        adminRole.setRole("ADMIN");

        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRole("USER");

        roleRepository.save(userRole);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("pass"));

        HashSet<Role> adminRoles = new HashSet<Role>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);

        admin.setRoles(adminRoles);

        userRepository.save(admin);

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("pass"));

        HashSet<Role> userRoles = new HashSet<Role>();
        userRoles.add(userRole);

        user.setRoles(userRoles);

        userRepository.save(user);
        
        User user2 = new User();
        user2.setUsername("Hans");
        user2.setPassword(passwordEncoder.encode("testpass"));
        
        user2.setRoles(adminRoles);
        
        userRepository.save(user2);
        
        User user3 = new User();
        user3.setUsername("a");
        user3.setPassword(passwordEncoder.encode("a"));
        
        user3.setRoles(adminRoles);
        
        userRepository.save(user3);
    }
}
