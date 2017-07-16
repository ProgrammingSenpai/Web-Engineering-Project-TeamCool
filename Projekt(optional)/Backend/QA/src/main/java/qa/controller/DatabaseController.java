package qa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import qa.service.DBService;

@CrossOrigin
@RestController
@RequestMapping
public class DatabaseController {

	private final DBService dbServ;

	@Autowired
	public DatabaseController(DBService dbServ) {
		this.dbServ = dbServ;
	}

	@RequestMapping(value = "/init")
	public void initDB() {
		dbServ.initDB();
	}
}
