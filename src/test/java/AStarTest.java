import models.AStarNode;
import models.algorithms.AStar;
import models.Node;
import models.Point;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarTest {

    private AStarNode buildAStarNode(int x, int y, int tileSize, boolean open) {
        return new AStarNode(new Node(new Point(x,y),tileSize,open));
    }

    @Test
    public void hCostCalculate() {
        AStarNode n = buildAStarNode(5, 5, 0, true);
        AStar a_star = new AStar();
        a_star.setHeuristicMultiplier(1);
        Assert.assertEquals(5.65, a_star.calculateHCost(1, 1, n), .01);
    }

    @Test
    /**
     * Use fCost to compare
     */
    public void compareToFCost() {
        AStarNode n1 = buildAStarNode(0, 0, 10, false);
        n1.setfCost(10);
        AStarNode n2 = buildAStarNode(1, 0, 10, false);


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
        AStarNode n1 = buildAStarNode(0, 0, 10, false);
        n1.setfCost(10);
        n1.sethCost(5);
        AStarNode n2 = buildAStarNode(1, 0, 10, false);
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
        PriorityQueue<AStarNode> pq = new PriorityQueue<>();
        AStarNode n1 = buildAStarNode(0, 0, 10, false);
        AStarNode n2 = buildAStarNode(1, 0, 10, false);

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
        PriorityQueue<AStarNode> pq = new PriorityQueue<>();
        AStarNode n1 = buildAStarNode(0, 0, 10, false);
        n1.setfCost(5);
        n1.sethCost(5);
        AStarNode n2 = buildAStarNode(1, 0, 10, false);
        n2.setfCost(5);
        n2.sethCost(5);
        AStarNode n3 = buildAStarNode(1, 1, 10, false);
        n3.setfCost(10);
        n3.sethCost(10);

        pq.add(n1);
        pq.add(n2);
        pq.add(n3);
        Assert.assertEquals(5, pq.poll().getfCost(), .001);
    }

    @Test
    /**
     *  Since the AStarNode is just a wrapper for the Node class,
     *  any two AStarNodes with same reference to a particular Node
     *  should hash to the the same location. 
     */
    public void sameNodeSameHash() {
        Set<AStarNode> closedSet = new HashSet<>();
        Node node = new Node(new Point(0,0),0,true);
        AStarNode a = new AStarNode(node);
        AStarNode b = new AStarNode(node);

        closedSet.add(a);
        Assert.assertTrue(closedSet.contains(b));
    }

}
