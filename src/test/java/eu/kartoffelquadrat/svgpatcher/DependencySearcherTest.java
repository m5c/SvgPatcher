package eu.kartoffelquadrat.svgpatcher;


import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Unit test for xml in ram analysis.
 *
 * @author Maximilian Schiedermeier
 */
public class DependencySearcherTest {

    /**
     * Test to verify the amount of generated lines matches the expected amount.
     */
    @Test
    public void testAmountDependencyLines() throws IOException, SAXException, ParserConfigurationException {

        // Read in xml document
        Document xmlTree = XmlIOUtils.parseXmlToDocument("pom.xml");

        // Generate the dependency String
        String dependencyString = DependencySearcher.getAllDependenciesAsString(xmlTree);

        // Check amount of lines
        String[] lines = dependencyString.split("\\n");
        assert (lines.length == 9);
    }

}
