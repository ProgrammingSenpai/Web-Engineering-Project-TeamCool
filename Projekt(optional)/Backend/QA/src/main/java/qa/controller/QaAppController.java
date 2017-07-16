package qa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import qa.dto.AnswerDto;
import qa.dto.QuestionDto;
import qa.service.QaService;

@CrossOrigin
@RestController
public class QaAppController {

	private final QaService qaServ;

	@Autowired
	QaAppController(QaService qaServ) {
		this.qaServ = qaServ;
	}

	@RequestMapping(value = "/questions/{type}", method=RequestMethod.GET)
	public List<QuestionDto> getQuestions(@PathVariable("type") String type) {
		return qaServ.questionRequest(type);
	}
	
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping(value = "/questions", method=RequestMethod.GET)
	public List<QuestionDto> myQuestions(Authentication auth) {
		return qaServ.getQuestionsFromAuthor(auth.getName());
	}

	@RequestMapping(value = "/question/{id}", method=RequestMethod.GET)
	public QuestionDto questionById(@PathVariable("id") long id) {
		return qaServ.getQuestionById(id);
	}
	
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping(value = "/questions/answeredByMe", method=RequestMethod.GET)
	public List<QuestionDto> myAnsweredQuestions(Authentication auth) {
		List<QuestionDto> q = qaServ.getQuestionsForAnswersByAuthor(auth.getName());
		for(QuestionDto quest : q){
			System.out.println(quest.getId());
		}
		return qaServ.getQuestionsForAnswersByAuthor(auth.getName());
	}
	
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping(value = "/question", method = RequestMethod.POST)
	public void addQuestion(Authentication auth,@RequestBody String string) {
		qaServ.addQuestion(auth.getName(),string);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping(value = "/answer", method = RequestMethod.POST)
	public void addAnswer(Authentication auth,@RequestBody String string) {
		qaServ.addAnswer(auth.getName(), string);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping(value = "/answer/{id}", method = RequestMethod.PATCH)
	public void toggleAccepted(@PathVariable("id") long id) {
		qaServ.toggleAccepted(id);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping(value = "/answer/{id}", method = RequestMethod.DELETE)
	public void deleteAnswer(@PathVariable("id") long id) {
		qaServ.deleteAnswer(id);
	}
	
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping(value = "/answers", method = RequestMethod.GET)
	public List<AnswerDto> getMyAnswers(Authentication auth) {
		return qaServ.getMyAnswers(auth.getName());
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping(value = "/question/{id}", method = RequestMethod.DELETE)
	public void deleteQuestion(@PathVariable("id") long id) {
		qaServ.deleteQuestion(id);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping(value = "/question/{id}", method = RequestMethod.PATCH)
	public void toggleSolved(@PathVariable("id") long id) {
		qaServ.toggleSolved(id);
	}
	
	@RequestMapping(value = "/answers/question/{id}", method = RequestMethod.GET)
	public List<AnswerDto> getAnswersToQuestion(@PathVariable("id") long id) {
		return qaServ.getAnswersToQuestion(id);
	}
}
