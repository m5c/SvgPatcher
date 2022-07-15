package eu.kartoffelquadrat.svgpatcher;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 * Little helper class to boost svg dimensions to 5000x5000, regardless of original size.
 *
 * @author Maximilian Schiedermeier, 2022s
 */
public class DimensionPatcher {

    /**
     * Changes the SVG dimensions so any browser considers it big enough to trigger css resize.
     *
     * @param svg the raw svg document object
     * @return the patched svg document object
     */
    public static Document patchSvgDimensions(Document svg) {

        NodeList svgElementList = svg.getElementsByTagName("svg");
        NamedNodeMap attributes = svgElementList.item(0).getAttributes();
        attributes.getNamedItem("width").setNodeValue("5000");
        attributes.getNamedItem("height").setNodeValue("5000");
        return svg;
    }
}
