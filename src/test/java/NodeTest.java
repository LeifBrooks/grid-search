import models.Node;
import models.Point;
import org.junit.Assert;
import org.junit.Test;

import java.util.PriorityQueue;

public class NodeTest {


    @Test
    /**
     * Nodes are equal if they share the same x y coordinates
     */
    public void nodesEqual() {
        Node n1 = new Node(new Point(0, 0), 10, false);
        Node n2 = new Node(new Point(0, 0), 10, false);

        Assert.assertEquals(n1, n2);
    }

    @Test
    /**
     * Nodes are not equal if the have different x y coordinates
     */
    public void nodesNotEqual() {
        Node n1 = new Node(new Point(0, 0), 10, false);
        Node n2 = new Node(new Point(1, 0), 10, false);

        Assert.assertNotEquals(n1, n2);
    }

}
