package qa.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qa.model.Answer;
import qa.model.Question;
import qa.repo.QuestionRepository;

@Service
@Transactional
public class DBService {
	
	
	private final QuestionRepository qRep;
	private final QaService qaServ;
	
	@Autowired
	public DBService(QuestionRepository qRep, QaService qaServ){
		this.qRep=qRep;
		this.qaServ=qaServ;
	}
	
	public void initDB(){
		Question q1 = new Question("Hans", "Title 1", "Test Question 1");
		qRep.save(q1);
		Question q2 = new Question("Max", "Title 2", "Test Question 2");
		qRep.save(q2);
		Question q3 = new Question("Hans", "Title 3", "Test Question 3");
		qRep.save(q3);
		Question q4 = new Question("Max", "Title 4", "Test Question 4");
		qRep.save(q4);
		Answer a1 = new Answer("Hans", "TestAnswer Text 1", (long) 1);
		qaServ.addAnswerEntity(a1);
		Answer a2 = new Answer("Max", "TestAnswer Text 2", (long) 1);
		qaServ.addAnswerEntity(a2);
		Answer a3 = new Answer("Hans", "TestAnswer Text 3", (long) 4);		
		qaServ.addAnswerEntity(a3);

		
//		Question q1 = new Question("Hans", "Title 1", "Test Question 1");
//		qRep.save(q1);
//		Question q2 = new Question("Max", "Title 2", "Test Question 2");
//		qRep.save(q2);
//		Question q3 = new Question("Hans", "Title 3", "Test Question 3");
//		qRep.save(q3);
//		Question q4 = new Question("Max", "Title 4", "Test Question 4");
//		qRep.save(q4);
//		Answer a1 = new Answer("AnswerAuthor1", "TestAnswer Text 1", (long) 1);
//		qaServ.addAnswerEntity(a1);
//		Answer a2 = new Answer("AnswerAuthor2", "TestAnswer Text 2", (long) 1);
//		qaServ.addAnswerEntity(a2);
//		Answer a3 = new Answer("AnswerAuthor3", "TestAnswer Text 3", (long) 4);
//		qaServ.addAnswerEntity(a3);
	}

}
