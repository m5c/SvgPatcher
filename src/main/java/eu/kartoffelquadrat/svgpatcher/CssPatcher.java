package eu.kartoffelquadrat.svgpatcher;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Little helper class to add an internal css definition to the SVG file that prevents manual
 * selection of any text objects by the user. See:
 * https://gist.github.com/23maverick23/64b3b587c88697558fac (no text select css definitions) And:
 * https://developer.mozilla.org/en-US/docs/Web/SVG/Element/style (svg css embed mechanism)
 *
 * @author Maximilian Schiedermeier, 2022s
 */
public class CssPatcher extends Patcher {

  private static final String CSS_NO_TEXT_SELECT_DEFINITION =
      "        svg text {\n"
          + "                -webkit-user-select: none;\n"
          + "                -moz-user-select: none;\n"
          + "                -ms-user-select: none;\n"
          + "                user-select: none;\n"
          + "                }\n"
          + "                svg text::selection {\n"
          + "                background: none;\n"
          + "                }\n";


  /**
   * CssPatcher constructor. Invokes super patcher constructor.
   *
   * @param svg as the document to operate on.
   */
  public CssPatcher(Document svg) {
    super(svg);
  }

  /**
   * Adds a new child node to the root svg node: "style".
   *
   * @return the identical svg object you passed as input, but with patched style node.
   */
  @Override
  public Document execute() {
    // Prepare a new node with the CSS definitions:
    Node cssDefNode = svg.createElement("style");
    cssDefNode.setTextContent(CSS_NO_TEXT_SELECT_DEFINITION);

    // Add it to the root.
    NodeList svgElementList = svg.getElementsByTagName("svg");
    svgElementList.item(0).appendChild(cssDefNode);

    return svg;
  }
}