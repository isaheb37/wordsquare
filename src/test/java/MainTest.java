import org.example.Generate;
import org.example.Main;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.List;

public class MainTest {

    @InjectMocks
    public Generate testObj;

    @Before
    public void setup() {
        testObj = new Generate();
        System.out.println("Tests are starting");
    }

    @Test
    public void testGenerateSuccessGrid1() throws Exception {
        List<String> actual = testObj.generate("aaccdeeeemmnnnoo", 4, true);
        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.size() > 0);
    }

    @Test
    public void testGenerateSuccessGrid2() throws Exception {
        List<String> actual = testObj.generate("eeeeddoonnnsssrv", 4, true);
        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.size() > 0);
    }

}
