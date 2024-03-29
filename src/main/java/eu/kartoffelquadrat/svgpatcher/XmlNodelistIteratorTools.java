package eu.kartoffelquadrat.svgpatcher;

// Can't believe w3c does not support foreach on nodelist...
// https://stackoverflow.com/a/19591302

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Helper tool to provide foreach support for xml nodelists.
 */
public final class XmlNodelistIteratorTools {

  private XmlNodelistIteratorTools() {
  }

  /**
   * Conversion to java List type of w3c provided NodeList object.
   *
   * @param n as the NodeList object you want to convert into a java list
   * @return a java list, semantically equivalent to the NodeList provided as input
   */
  public static List<Node> asList(NodeList n) {
    return n.getLength() == 0
        ? Collections.<Node>emptyList() : new NodeListWrapper(n);
  }

  static final class NodeListWrapper extends AbstractList<Node>
      implements RandomAccess {
    private final NodeList list;

    NodeListWrapper(NodeList l) {
      list = l;
    }

    @Override
    public Node get(int index) {
      return list.item(index);
    }

    @Override
    public int size() {
      return list.getLength();
    }
  }
}