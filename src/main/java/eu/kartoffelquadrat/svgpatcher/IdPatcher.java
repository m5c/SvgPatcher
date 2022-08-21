package eu.kartoffelquadrat.svgpatcher;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static eu.kartoffelquadrat.svgpatcher.XmlNodelistIteratorTools.asList;

/**
 * Maximilian Schiedermeier 2022
 */
public class IdPatcher {

    /**
     * This method does the actual DOM transformation we are intested in. If in Omnigraffle an object is marked with
     * something like "VID-XYZ", where VID is a standard prefix for "Vector Identifier" and "XYZ" is the actual unique
     * name describing the purpose of the object, then we ant to set this entire string as "id" in the dom. In the SVG
     * generated by omnigraffle we have something like:
     *
     * <g id="Graphic_3">
     * <title>VID-SQUARE1</title>
     * <rect x="230.8" y="193.9" width="105" height="105" fill="#40ff40"/>
     * <rect x="230.8" y="193.9" width="105" height="105" stroke="black" stroke-linecap="round" stroke-linejoin="round"
     * stroke-width="1"/>
     * <text transform="translate(235.8 237.176)" fill="black">
     * <tspan font-family="Helvetica Neue" font-size="16" font-weight="400" fill="black" x="34.156" y="15">361</tspan>
     * </text>
     * </g>
     * <p>
     * What we want is:
     * <g id="VID-SQUARE1">
     * <rect x="230.8" y="193.9" width="105" height="105" fill="#40ff40"/>
     * <rect x="230.8" y="193.9" width="105" height="105" stroke="black" stroke-linecap="round" stroke-linejoin="round"
     * stroke-width="1"/>
     * <text transform="translate(235.8 237.176)" fill="black">
     * <tspan font-family="Helvetica Neue" font-size="16" font-weight="400" fill="black" x="34.156" y="15">361</tspan>
     * </text>
     * </g>
     * <p>
     * This method handles this transformation for an entire svg document.
     *
     * @param svg
     */
    public static Document patchAllOmnigraffleIds(Document svg) {

        NodeList titleElements = svg.getElementsByTagName("title");
        for (Node node : asList(titleElements)) {

            // TODO: figure out why this does not patch cirlce and Rectangle2. => Modfying while iterating, only every second elemtn?
            // If and only if the value of that node has the OmniGraffle prefix "VID-" (Vector ID)...
            // ... then add this element value as as actual ID of its parent node
            if (node.getTextContent().startsWith("VID-")) {
                ((Element) node.getParentNode()).setAttribute("id", node.getTextContent());
                // Finally get rid of this omnigraffle artefact, we don't need it ever again.
                node.getParentNode().removeChild(node);
                System.out.println("Patched one element: "+node.getTextContent());
            }
        }
        return svg;
    }
}
