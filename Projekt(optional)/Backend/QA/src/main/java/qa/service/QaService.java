package qa.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import qa.dto.AnswerDto;
import qa.dto.QuestionDto;
import qa.model.Answer;
import qa.model.Question;
import qa.repo.AnswerRepository;
import qa.repo.QuestionRepository;

@Service
@Transactional
public class QaService {

	private final QuestionRepository qRepo;
	private final AnswerRepository aRepo;

	@Autowired
	public QaService(QuestionRepository qRepo, AnswerRepository aRepo) {
		this.qRepo = qRepo;
		this.aRepo = aRepo;
	}

	public void toggleAccepted(long id) {
		Answer a = aRepo.findOne(id);
		a.setAccepted(a.isAccepted() ? false : true);
		aRepo.save(a);
	}

	public void addAnswer(String author, String answer) {
		Answer answerE = toAnswerEntity(author, answer);
		aRepo.save(answerE);
		Question q = qRepo.findOne(answerE.getqId());
		q.addAnswer(answerE);
		q.setAnswered(true);
		qRepo.save(q);
	}

	public void addAnswerEntity(Answer answerE) {
		aRepo.save(answerE);
		Question q = qRepo.findOne(answerE.getqId());
		q.addAnswer(answerE);
		q.setAnswered(true);
		qRepo.save(q);
	}

	public void addQuestion(String author, String question) {
		qRepo.save(toQuestionEntity(author, question));
	}

	public void deleteAnswer(long id) {
		Answer a = aRepo.findOne(id); // get answer
		Question q = qRepo.findOne(a.getqId()); // get question to answer
		q.deleteAnswer(a); // delete answer from answers list of the question
		// entity
		q.setAnswered(q.getAnswers().isEmpty() ? false : true);
		qRepo.save(q); // update question
		// this is needed because otherwise the QUESTION_ANSWERS
		// table throws an error
		aRepo.delete(a); // delete answer
	}

	public void deleteQuestion(long id) {
		Question q = qRepo.findOne(id);
		List<Answer> answers = q.getAnswers();
		q.setAnswers(null);
		qRepo.save(q);
		for (Answer a : answers) {
			aRepo.delete(a.getId());
		}
		qRepo.delete(id);
	}

	public void toggleSolved(long id) {
		Question q = qRepo.findOne(id);
		q.setSolved(q.isSolved() ? false : true);
		qRepo.save(q);
	}

	private List<QuestionDto> getAllQuestions() {
		return qListToQDtoList(qRepo.findAll());
	}

	public List<QuestionDto> getQuestionsFromAuthor(String author) {
		return qListToQDtoList(qRepo.getQuestionFromAuthor(author));
	}

	private List<QuestionDto> getUnanswered() {
		return qListToQDtoList(qRepo.getUnanswered());
	}

	private List<QuestionDto> getUnsolved() {
		return qListToQDtoList(qRepo.getUnsolved());
	}

	private List<QuestionDto> qListToQDtoList(List<Question> q) {
		List<QuestionDto> qDto = new LinkedList<QuestionDto>();
		for (Question x : q) {
			qDto.add(questionToQuestionDto(x));
		}
		return qDto;
	}

	private QuestionDto questionToQuestionDto(Question q) {
		QuestionDto qDto = new QuestionDto();
		qDto.setId(q.getId());
		qDto.setAnswered(q.isAnswered());
		qDto.setSolved(q.isSolved());
		qDto.setTitle(q.getTitle());
		qDto.setAuthor(q.getAuthor());
		qDto.setId(q.getId());
		qDto.setText(q.getText());
		qDto.setAnswerCount(q.getAnswers().size());
		qDto.setDate(q.getDate().toString());
		return qDto;
	}

	@SuppressWarnings("unchecked")
	private Answer toAnswerEntity(String author, String string) {
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			result = new ObjectMapper().readValue(string, HashMap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Answer(author, result.get("text"), Long.parseLong(result.get("qId")));
	}

	@SuppressWarnings("unchecked")
	private Question toQuestionEntity(String author, String string) {
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			result = new ObjectMapper().readValue(string, HashMap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Question(author, result.get("title"), result.get("text"));
	}

	public List<QuestionDto> questionRequest(String type) {

		switch (type) {
		case "all":
			return getAllQuestions();
		case "unanswered":
			return getUnanswered();
		case "unsolved":
			return getUnsolved();
		}

		return null;
	}

	public List<AnswerDto> getMyAnswers(String name) {
		return answersToAnswerDto(aRepo.getAnswersByAuthor(name));
	}

	private List<AnswerDto> answersToAnswerDto(List<Answer> answers) {
		List<AnswerDto> answersDto = new LinkedList<AnswerDto>();
		for (Answer a : answers) {
			answersDto.add(answerToAnswerDto(a));
		}
		return answersDto;
	}

	private AnswerDto answerToAnswerDto(Answer answer) {
		AnswerDto answerDto = new AnswerDto();
		answerDto.setAccepted(answer.isAccepted());
		answerDto.setAuthor(answer.getAuthor());
		answerDto.setDate(answer.getDate());
		answerDto.setId(answer.getId());
		answerDto.setqId(answer.getqId());
		answerDto.setText(answer.getText());
		return answerDto;
	}

	public QuestionDto getQuestionById(long id) {

		return questionToQuestionDto(qRepo.findOne(id));
	}

	public List<QuestionDto> getQuestionsForAnswersByAuthor(String name) {
		List<Answer> answers = aRepo.getAnswersByAuthor(name);
		List<Question> questions = new LinkedList<Question>();
		for (Answer a : answers) {
			questions.add(qRepo.findOne(a.getqId()));
		}
		return qListToQDtoList(questions);
	}

	public List<AnswerDto> getAnswersToQuestion(long id) {
		return answersToAnswerDto(aRepo.getAnswersForQuestion(id));

	}

}
