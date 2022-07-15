package eu.kartoffelquadrat.svgpatcher;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Util class to demonstrate access on a previously imported XML file.
 * @author Maximilian Schiedermeier
 */
public class DependencySearcher {

    /**
     * Prints all dependencies of a given pom tree. Demonstrates access on the in-ram tree structure of a previously
     * imported pom.xml file.
     *
     * @param pomTree as the xml document to treat.
     */
    public static String getAllDependenciesAsString(Document pomTree) {

        // Individual dependency blocks are concatenated in this builder.
        StringBuilder dependencyBuilder = new StringBuilder("All dependencies: \n");

        // Extract list of all "dependency"-nodes in xml file (the below command performs a deep search)
        NodeList dependencyNodes = pomTree.getElementsByTagName("dependency");

        // Iterate over dependency blocks and print info:
        for (int temp = 0; temp < dependencyNodes.getLength(); temp++) {

            Node dependencyBlock = dependencyNodes.item(temp);
            dependencyBuilder.append(buildDependencyBlockString(dependencyBlock));
        }

        // return the overall dependency list
        return dependencyBuilder.toString();
    }

    /**
     * Helper method to convert the dependency details of a single dependency node into text, so it can be printed.
     * Access teh groupId, artifactId, version child nodes.
     *
     * @param dependencyNode as a node in the pom tree.
     * @return String compiling all information of a maven dependency statement.
     */
    private static String buildDependencyBlockString(Node dependencyNode) {
        if (dependencyNode.getNodeType() == Node.ELEMENT_NODE) {

            Element element = (Element) dependencyNode;

            StringBuilder sb = new StringBuilder();
            sb.append("------\n");
            sb.append("GroupId: ").append(element.getElementsByTagName("groupId").item(0).getTextContent()).append("\n");
            sb.append("ArtifactId: ").append(element.getElementsByTagName("artifactId").item(0).getTextContent()).append("\n");
            sb.append("Version: ").append(element.getElementsByTagName("version").item(0).getTextContent()).append("\n");

            return sb.toString();
        } else
            throw new RuntimeException("Provided element is not a node.");
    }
}
