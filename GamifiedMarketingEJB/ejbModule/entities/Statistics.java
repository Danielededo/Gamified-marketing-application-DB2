package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "statistics", schema = "gamified_marketing")
@NamedQuery(name = "Statistics.findBySubmission", query = "SELECT s FROM Statistics s WHERE s.submission.id = :submissionId")
public class Statistics implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "submission")
	private Submission submission;
	
	private Integer age;
	private String sex;
	private String expertise;
	
	public Statistics() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Statistics(Submission submission, int age, String sex, String expertise) {
		super();
		this.submission = submission;
		this.age = age;
		this.sex = sex;
		this.expertise = expertise;
	}
	
	public Statistics(int age, String sex2, String expertise2) {
		// TODO Auto-generated constructor stub
	}

	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	public Submission getSubmission() {
		return submission;
	}
	public void setSubmission(Submission submission) {
		this.submission = submission;
	}
	
}
