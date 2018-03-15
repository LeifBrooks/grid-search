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

    @Test
    /**
     * Use fCost to compare
     */
    public void compareToFCost() {
        Node n1 = new Node(new Point(0, 0), 10, false);
        n1.setfCost(10);
        Node n2 = new Node(new Point(1, 0), 10, false);


        //less than
        n2.setfCost(11);
        Assert.assertEquals(n1.compareTo(n2), -1);

        //equal
        n2.setfCost(9);
        Assert.assertEquals(n1.compareTo(n2), 1);
    }

    @Test
    /**
     * If fCost is Tied, use hCost
     */
    public void compareToFCostTie() {
        Node n1 = new Node(new Point(0, 0), 10, false);
        n1.setfCost(10);
        n1.sethCost(5);
        Node n2 = new Node(new Point(1, 0), 10, false);
        n2.setfCost(10);

        //less than
        n2.sethCost(6);
        Assert.assertEquals(n1.compareTo(n2), -1);

        //equal
        n2.sethCost(5);
        Assert.assertEquals(n1.compareTo(n2), 0);

        //greater than
        n2.sethCost(4);
        Assert.assertEquals(n1.compareTo(n2), 1);
    }

    @Test
    /**
     * PriorityQueue sorts from minimum fCost/hCost to maximum
     */
    public void priorityQueueCostsNotEqual() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Node n1 = new Node(new Point(0, 0), 10, false);
        Node n2 = new Node(new Point(1, 0), 10, false);

        n1.setfCost(5);
        n2.setfCost(10);

        pq.add(n1);
        pq.add(n2);
        Assert.assertEquals(n1, pq.poll());

        n1.setfCost(5);
        n2.setfCost(4);
        pq.clear();
        pq.add(n1);
        pq.add(n2);
        Assert.assertEquals(n2, pq.poll());

        n1.setfCost(4);
        n2.setfCost(4);
        n1.sethCost(4);
        n2.sethCost(10);
        pq.clear();
        pq.add(n1);
        pq.add(n2);

        Assert.assertEquals(n1, pq.poll());

        n1.setfCost(4);
        n2.setfCost(4);
        n1.sethCost(10);
        n2.sethCost(4);
        pq.clear();
        pq.add(n1);
        pq.add(n2);

        Assert.assertEquals(n2, pq.poll());

    }

    @Test
    public void priorityQueueCostsEqual() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Node n1 = new Node(new Point(0, 0), 10, false);
        n1.setfCost(5);
        n1.sethCost(5);
        Node n2 = new Node(new Point(1, 0), 10, false);
        n2.setfCost(5);
        n2.sethCost(5);
        Node n3 = new Node(new Point(1, 1), 10, false);
        n3.setfCost(10);
        n3.sethCost(10);

        pq.add(n1);
        pq.add(n2);
        pq.add(n3);
        Assert.assertEquals(5, pq.poll().getfCost(), .001);
    }

}
