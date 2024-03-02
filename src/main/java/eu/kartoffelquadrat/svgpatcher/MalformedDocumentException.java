package eu.kartoffelquadrat.svgpatcher;

/**
 * Custom exception that is thrown when a structural issue with the transformers input is detected.
 */
public class MalformedDocumentException extends RuntimeException {

  /**
   * Public constructor for custom exception.
   *
   * @param message as the payload to be printed when exception is raised.
   */
  public MalformedDocumentException(String message) {
    super(message);
  }
}
