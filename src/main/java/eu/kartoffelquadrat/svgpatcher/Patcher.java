package eu.kartoffelquadrat.svgpatcher;

import java.io.IOException;
import org.w3c.dom.Document;

/**
 * Abstract base class for all patchers.
 */
public abstract class Patcher {

  /**
   * The svg this patcher works on.
   */
  protected Document svg;


  /**
   * Abstract constructor invoked by all subclasses.
   *
   * @param svg the document to modify.
   */
  public Patcher(Document svg) {
    this.svg = svg;
  }

  /**
   * Triggers single run execution of the given patcher implementation. Patchers may behave cause
   * incremental changes when executed repeatedly.
   *
   * @return the modified svg document object
   * @throws IOException if patching involves loading of external files which cannot be resolved.
   */
  public abstract Document execute() throws IOException;
}
