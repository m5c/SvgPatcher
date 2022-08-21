import eu.kartoffelquadrat.svgpatcher.IdPatcher;
import eu.kartoffelquadrat.svgpatcher.XmlIOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class SvGPatcherTest {

    public static final File TEST_RESOURCE_FOLDER = new File("src/test/resources");
    public static final File TEST_INPUT_GRAPHIC = new File(TEST_RESOURCE_FOLDER.getAbsolutePath() + "/testgraphic.svg");
    public static final File TEST_IDPATCHED_OUTPUT_LOCATION = new File(TEST_RESOURCE_FOLDER.getAbsolutePath() + "/idpatched.svg");
    public static final File TEST_IDPATCHED_REFERENCE_LOCATION = new File(TEST_RESOURCE_FOLDER.getAbsolutePath() + "/idpatchedreference.svg");


    /**
     * Tests Loading of test resource file form disk and interpretation as SVG file
     */
    @Test
    public void testLoadFromTestResources() throws IOException, SAXException, ParserConfigurationException {
        Document svg = XmlIOUtils.parseXmlToDocument(TEST_INPUT_GRAPHIC.getAbsolutePath());
    }

    /**
     * Test if the string (not persisted to disk) when sample svg is only ID patched complies to what we expect.
     */
    @Test
    public void testIdPatch() throws IOException, SAXException, ParserConfigurationException, TransformerException, NoSuchAlgorithmException {
        Document svg = XmlIOUtils.parseXmlToDocument(TEST_INPUT_GRAPHIC.getAbsolutePath());

        // Patch all IDs
        IdPatcher.patchAllOmnigraffleIds(svg);

        // Write the outcome to disk
        XmlIOUtils.writeXmlDocumentToDisk(svg, TEST_IDPATCHED_OUTPUT_LOCATION.getAbsolutePath());

        // Verify this corresponds to the expected outcome
        String generatedContent = new Scanner(TEST_IDPATCHED_OUTPUT_LOCATION).useDelimiter("\\Z").next();
        String expectedContent = new Scanner(TEST_IDPATCHED_REFERENCE_LOCATION).useDelimiter("\\Z").next();

        Assert.assertTrue("Generated file with patched ids differs from expected output. Run this command to inspect how they differ: \nicdiff " + TEST_IDPATCHED_OUTPUT_LOCATION.getAbsolutePath() + " " + TEST_IDPATCHED_REFERENCE_LOCATION.getAbsolutePath(), generatedContent.equals(expectedContent));

    }
}
