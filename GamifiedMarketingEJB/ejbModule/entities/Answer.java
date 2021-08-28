package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "answer", schema = "gamified_marketing")
@NamedQuery(name = "Answer.findBySubmission", query = "SELECT a FROM Answer a WHERE a.submission.id = :submissionId")
@NamedQuery(name = "Answer.findByQuestion", query = "SELECT a FROM Answer a WHERE a.question.id = :questionId")
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "submission")
	private Submission submission;
	@ManyToOne
	@JoinColumn(name = "question")
	private Question question;
	private String text;
	
	public Answer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Answer(Question question, String text) {
		super();
		this.question = question;
		this.text = text;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Submission getSubmission() {
		return submission;
	}
	public void setSubmission(Submission submission) {
		this.submission = submission;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
