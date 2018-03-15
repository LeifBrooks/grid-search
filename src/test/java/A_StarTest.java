import models.A_Star;
import models.Node;
import models.Point;
import org.junit.Assert;
import org.junit.Test;

public class A_StarTest {

    @Test
    public void hCostCalculate() {
        Node n = new Node(new Point(5, 5), 0, true);
        A_Star a_star = new A_Star();
        a_star.setHeuristicMultiplier(1);
        Assert.assertEquals(5.65, a_star.calculateHCost(1, 1, n), .01);
    }

}
