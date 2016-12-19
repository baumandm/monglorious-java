package org.baumandm.monglorious;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by baumandm on 12/16/16.
 */
public class MongloriousClientTest {

    @Test
    public void testMongloriousWithUri() {
        MongloriousClient m = new MongloriousClient("mongodb://localhost:27017/testdb");
    }

    @Test
    public void testMongloriousExecute() {
        MongloriousClient m = new MongloriousClient("mongodb://localhost:27017/testdb");
        Object results = m.execute("show dbs");
    }
}
