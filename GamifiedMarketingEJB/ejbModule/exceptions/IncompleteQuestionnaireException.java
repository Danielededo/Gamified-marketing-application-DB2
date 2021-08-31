package exceptions;

public class IncompleteQuestionnaireException extends Exception {
	private static final long serialVersionUID = 1L;

	public IncompleteQuestionnaireException(String message) {
		super(message);
	}
}
