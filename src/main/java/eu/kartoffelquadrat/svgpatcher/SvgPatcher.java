package eu.kartoffelquadrat.svgpatcher;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Code based on the example by mkyong: https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * <p>
 * This program reads in an xml file, parses it to it's tree structure. Then it walks the tree ans prints the info of
 * selected notes.
 */
public class SvgPatcher {

    // Set path to this programs own pom.xml file.
    private static final String SOURCE_FILE_1 = "vectorBoard.svg";

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {

        // Convert XML file, to in-RAM tree structure of nodes.
        Document svg = XmlIOUtils.parseXmlToDocument(SOURCE_FILE_1);

        // Patch all IDs
        IdPatcher.patchAllOmnigraffleIds(svg);
//        System.out.println(DependencySearcher.getAllDependenciesAsString(pomTree1));

    }
}