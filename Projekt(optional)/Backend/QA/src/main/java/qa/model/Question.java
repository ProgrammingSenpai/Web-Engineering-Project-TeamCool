package qa.model;



import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Question {
	
	@Id
	@GeneratedValue
	private long id;
	private String author, text, title;
	private Date date;
	private boolean answered = false;
	private boolean solved = false;

	@OneToMany
	private List<Answer> answers=new LinkedList<Answer>();
	
	public Question (){}
	public Question(String author, String title, String text){
		this.author=author;
		this.text=text;
		this.title=title;
		this.date=new Timestamp(System.currentTimeMillis());
	}
	
	public void addAnswer(Answer answer){
		answers.add(answer);
	}
	
	public void deleteAnswer(Answer answer){
		answers.remove(answer);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers != null? answers : new LinkedList<Answer>();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isAnswered() {
		return answered;
	}
	public void setAnswered(boolean answered) {
		this.answered = answered;
	}
	public boolean isSolved() {
		return solved;
	}
	public void setSolved(boolean solved) {
		this.solved = solved;
	}
	
}
