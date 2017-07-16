Einfach die qa.jar file ausführen wie in der Übung.
Folgende Funktionalitäten sind verfügbar:


H2 Konsole öffnen:		localhost:8080/console , ACHTUNG FALLS NICHT AUTOMATISCH UMGESTELLT: "JDBC URL:" = jdbc:h2:mem:app_db


Question hinzufügen:		localhost:8080/addQuestion , POST, erwartet ein JSON Objekt, benutzt angefügte "index.html" datei als beispiel :)


Questions von einem author:	localhost:8080/getQuestions/{author}


Datenbank initialisieren:	localhost:8080/init (wird mit VERSION 2.0 automatisch gemacht, kann trotzdem ausgeführt werden)

VERSION 2.0:
Weitere Funktionen aus der Controller-Klasse:
"localhost:8080" + Wert von "value" im jeweiligen @RequestMapping ruft die funktion auf. 
(z.B. "localhost:8080/requestAllQuestions" liefert ein JSON Objekt als Response mit allen derzeit in der Datenbank befindlichen Fragen)

"@Secured" bedeutet, dass für diese Methode/Funktion ein login erforderlich ist. Folgende User werden derzeit initialisiert:
"user", "pass" mit ("ROLE_USER")
"TestUser", "pass" mit ("ROLE_USER")
"admin", "pass" mit ("ROLE_ADMIN", "ROLE_USER")
benutzt "localhost:8080/register" + JSON um einen user zu registrieren(siehe index.html als beispiel, man muss sich vor einem ajax call auf "localhost:8080/login" anmelden,sonst gibt es ein cross-origin error-> TODO fix)


Funktionsnamen sollten selbsterklärend sein. ;)

Im Ordner QA befindet sich mein Eclipse Projekt, fühlt euch eingeladen es zu importieren und zu bearbeiten.


@RequestMapping(value="/requestAllQuestions")
public List<QuestionDto> requestAllQuestions()

@RequestMapping(value = "/register", method = RequestMethod.POST)
public ResponseEntity register(@RequestBody String userDetails)
	
@RequestMapping(value="/requestUnanswered")
public List<QuestionDto> requestUnanswered()
	
@RequestMapping(value="/requestUnsolved")
public List<QuestionDto> requestUnsolved()
	
@Secured({"ROLE_USER" , "ROLE_ADMIN"})
@RequestMapping(value="/myQuestions")
public List<QuestionDto> myQuestions(Authentication auth)
	
@RequestMapping(value="/getQuestions/{author}")
@Secured({"ROLE_USER" , "ROLE_ADMIN"})
public List<QuestionDto> getQuestionsFromAuthor(@PathVariable("author") String author)
	
@Secured({"ROLE_USER" , "ROLE_ADMIN"})
@RequestMapping(value="/addQuestion", method=RequestMethod.POST)
public void addQuestion(@RequestBody String string)

@Secured({"ROLE_USER" , "ROLE_ADMIN"})
@RequestMapping(value="/addAnswerToQuestion", method=RequestMethod.POST)
public void addAnswer(@RequestBody String string)
	
@Secured({"ROLE_USER" , "ROLE_ADMIN"})
@RequestMapping(value="/accept/{id}")
public void acceptAnswer(@PathVariable("id") long id)

@Secured({"ROLE_USER" , "ROLE_ADMIN"})
@RequestMapping(value="/deleteAnswer/{id}")
public void deleteAnswer(@PathVariable("id") long id)
	
@Secured({"ROLE_USER" , "ROLE_ADMIN"})
@RequestMapping(value="/deleteQuestion/{id}")
public void deleteQuestion(@PathVariable("id") long id)
	
@Secured({"ROLE_USER" , "ROLE_ADMIN"})
@RequestMapping(value="/toggleSolved/{id}")
public void toggleSolved(@PathVariable("id") long id)
