package qa.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import qa.exceptions.InvalidInputException;
import qa.exceptions.NameAlreadyInUseException;
import qa.model.Role;
import qa.model.User;
import qa.repo.RoleRepository;
import qa.repo.UserRepository;

@Service
@Transactional
public class UserService {

	private final UserRepository userRep;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRep;

	@Autowired
	public UserService(RoleRepository roleRep, UserRepository userRep, PasswordEncoder passwordEncoder) {
		this.userRep = userRep;
		this.passwordEncoder = passwordEncoder;
		this.roleRep = roleRep;
	}

	@SuppressWarnings("unchecked")
	public void register(String userDetails) throws NameAlreadyInUseException, InvalidInputException {
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			result = new ObjectMapper().readValue(userDetails, HashMap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (result.isEmpty())
			throw new InvalidInputException();

		if (userRep.findByUsername(result.get("user")) != null) {
			throw new NameAlreadyInUseException();
		}

		for (String s1 : result.values()) {
			if (s1.length() < 3 || s1 == null)
				throw new InvalidInputException();
		}

		User u = new User();
		u.setUsername(result.get("user"));
		u.setPassword(passwordEncoder.encode(result.get("password")));

		HashSet<Role> userRoles = new HashSet<Role>();
		userRoles.add(roleRep.findOne((long) 2));
		u.setRoles(userRoles);

		userRep.save(u);
	}

}
