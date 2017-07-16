package qa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import qa.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{

	@Query(value = "SELECT * FROM QUESTION where author=:author", nativeQuery=true)
	List<Question> getQuestionFromAuthor(@Param("author") String author);

	@Query(value="SELECT * FROM QUESTION where ANSWERED = false", nativeQuery=true)
	List<Question> getUnanswered();
	
	@Query(value="SELECT * FROM QUESTION where SOLVED = false", nativeQuery=true)
	List<Question> getUnsolved();
}
