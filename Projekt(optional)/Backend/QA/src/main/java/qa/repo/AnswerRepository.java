package qa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import qa.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	@Query(value="SELECT * FROM ANSWER where author = :author", nativeQuery=true)
	List<Answer> getAnswersByAuthor(@Param("author") String author);
	
	@Query(value="SELECT * FROM ANSWER where Q_ID = :qId", nativeQuery=true)
	List<Answer> getAnswersForQuestion(@Param("qId") long qId);
}
