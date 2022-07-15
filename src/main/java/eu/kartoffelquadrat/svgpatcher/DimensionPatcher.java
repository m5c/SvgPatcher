package eu.kartoffelquadrat.svgpatcher;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DimensionPatcher {

    /**
     * Changes the SVG dimensions so any browser considers it big enough to trigger css resize.
     *
     * @param svg the raw svg document object
     * @return the patched svg document object
     */
    public static Document patchSvgDimensions(Document svg) {

        printAllChildNodes(svg);

        svg.getElementsByTagName("svg");
        NamedNodeMap attributes = svg.getAttributes();
        attributes.getNamedItem("width").setNodeValue("5000");
        attributes.getNamedItem("height").setNodeValue("5000");
        return svg;
    }

    public static void printAllChildNodes(Node node) {
        System.out.println(node);

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            printAllChildNodes(currentNode);
        }
    }

}
