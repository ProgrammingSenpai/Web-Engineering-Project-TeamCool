package qa.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import qa.exceptions.InvalidInputException;
import qa.exceptions.NameAlreadyInUseException;
import qa.service.UserService;

@CrossOrigin
@RestController
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value="/user", method = RequestMethod.GET)
	public HashMap<String, String> getCurrentUserName(Authentication auth){
		if(auth==null){
			return null;
		}
		HashMap<String,String> hash = new HashMap<String,String>();
		hash.put("name", auth.getName());
		return hash;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody String userDetails) {
		try {
			userService.register(userDetails);
			return ResponseEntity.status(HttpStatus.OK).body("OK");
		} catch (NameAlreadyInUseException e) {
		       return ResponseEntity
		               .status(HttpStatus.FORBIDDEN)
		               .body("Name already in use");
		} catch (InvalidInputException e) {
		       return ResponseEntity
		               .status(HttpStatus.FORBIDDEN)
		               .body("Invalid Input");
		}

	}
}
