import models.A_Star;
import models.Point;
import org.junit.Assert;
import org.junit.Test;

public class A_StarTest {

    @Test
    public void hCostCalulate() {
        int x1 = 1;
        int y1 = 1;
        Point p = new Point(5, 5);
        int heuristic_modifier = 1;

        Assert.assertEquals(5, (int) new A_Star().calculateHCost(x1, y1, p, heuristic_modifier));
    }

}
