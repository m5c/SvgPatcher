package eu.kartoffelquadrat.svgpatcher;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Code based on the example by mkyong:
 * https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 */
public class SvgPatcher {

  /**
   * Default constructor.
   */
  public SvgPatcher() {
  }


  /**
   * Makes to changes to a provided SVG document (generated by omnigraffle). First: boosts the svg
   * dimensions so the svg is correctly rendered and not too tiny. Second: searches for IDs that
   * Omnigraffle places as title attributes and adapts these id values as actual svg ids, so browser
   * javascript frameworks can actually find them and conveniently modify the DOM.
   *
   * @param args first argument in input file to parse, second argument is where to store the target
   *             file. Next argument is link to a file with custom css definitions (pass empty file
   *             if not needed). All further arguments are references to javascript functions that
   *             must be included by the svg. (Place those javascript files in your
   *             .../resources/static/ folder, then reference to them using your applications base
   *             url, e.g.: /baseurl/uiactions.js)
   * @throws IOException                  in case provided file can not be read
   * @throws SAXException                 in case provided file can not be interpreted as svg / does
   *                                      not comply to DTD
   * @throws ParserConfigurationException in case the parser arguments cannot be applied
   * @throws TransformerException         in case the modified svg cannot be persisted to disk
   */
  public static void main(String[] args)
      throws IOException, SAXException, ParserConfigurationException, TransformerException {

    // Convert XML file, to in-RAM tree structure of nodes.
    Document svg = XmlInputOutputUtils.parseXmlToDocument(args[0]);

    // Patch all IDs
    new IdPatcher(svg).execute();

    // Patch meta width and height, so css renderer is not confused by overly small svg graphics.
    new DimensionPatcher(svg).execute();

    // Patch (add) an internal CSS definition that prevents manual text selection on click
    String customCssFileReference = args[2];
    new CssPatcher(svg, customCssFileReference).execute();

    // TODO: Assess if this is obsolete. It seems JS issued click listeners are more effective.
    // Add reference to a relative javascript file that defines functions for onclick actions.
    List<String> externalFunctions = new LinkedList<>(Arrays.asList(args));
    // remove first two elements. The first two command line args are always file input and
    // output location, we are only interested in the remaining arguments which are assumed
    // custom JS functions.
    externalFunctions.remove(0);
    externalFunctions.remove(0);
    // The third argument is the CSS patcher reference
    externalFunctions.remove(0);
    // Remaining elements are actual JS function references.
    new FunctionReferencePatcher(svg, externalFunctions).execute();

    // Delete file if already exists (so it is actually replaced)
    File file = new File(args[1]);
    if (file.exists()) {
      file.delete();
    }

    // Write patched svg to disk
    XmlInputOutputUtils.writeXmlDocumentToDisk(svg, args[1]);
  }
}