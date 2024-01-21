package eu.kartoffelquadrat.svgpatcher;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * Helper class that adds additional script referencer tags as main nodes (one level below root).
 */
public class FunctionReferencePatcher extends Patcher {

  private static final Logger logger = LogManager.getLogger(FunctionReferencePatcher.class);

  List<String> externalFunctions;

  /**
   * Constructor for Function Reference Patcher utility class.
   *
   * @param externalFunctions as a list of functions you want to be able to invoke from your svg.
   * @param svg               as the parsed svg file, the object you want to patch.
   */
  public FunctionReferencePatcher(Document svg, List<String> externalFunctions) {
    super(svg);
    this.externalFunctions = externalFunctions;
  }

  /**
   * Patches provided svg object with reference to prepared javascript function names. Note that
   * this is a reference to an external URL, not an in-svg reference.
   */
  @Override
  public Document execute() {

    // This is the element we want to add the script references to (as children)
    Node root = svg.getDocumentElement();

    for (String scriptLocation : externalFunctions) {
      if (logger.isInfoEnabled()) {
        logger.info("Adding script ref to this location: " + scriptLocation);
      }

      // Create new script element:
      // Something like: <script xlink:href="/gvg/uiactions.js" />
      Element scriptReference = svg.createElement("script");
      scriptReference.setAttribute("xlink:href", scriptLocation);

      // Actually add the element to the svg document
      root.appendChild(scriptReference);
    }
    return svg;
  }
}
