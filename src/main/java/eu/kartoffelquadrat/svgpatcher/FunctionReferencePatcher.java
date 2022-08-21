package eu.kartoffelquadrat.svgpatcher;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

/**
 * Helper class that adds additional script referencer tags as main nodes (one level below root)
 */
public class FunctionReferencePatcher {

    /**
     * Patches provided svg object with reference to prepared javascript function names.
     * @param svg as the parsed svg file, the object you want to patch.
     * @param externalFunctions as a list of functions you want to be able to invoke from your svg.
     */
    public static void referenceFunctions(Document svg, List<String> externalFunctions) {

        // This is the element we want to add the script references to (as children)
        Node root = svg.getDocumentElement();

        for (String scriptLocation : externalFunctions) {
            System.out.println("Adding script ref to this location: " + scriptLocation);

            // Create new script element:
            // Something like: <script xlink:href="/gvg/uiactions.js" />
            Element scriptReference = svg.createElement("script");
            scriptReference.setAttribute("xlink:href", scriptLocation);

            // Remove default attributes we do not need: (For some reason these lines have no effect)
//            scriptReference.removeAttribute("xlink:actuate");
//            scriptReference.removeAttribute("xlink:show");
//            scriptReference.removeAttribute("xlink:type");

            // Actually add the element to the svg document
            root.appendChild(scriptReference);
        }
    }
}
