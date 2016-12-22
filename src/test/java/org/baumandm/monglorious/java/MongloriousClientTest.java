package org.baumandm.monglorious.java;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.baumandm.monglorious.java.MongloriousClient;
import org.baumandm.monglorious.java.MongloriousException;
import org.bson.Document;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by baumandm on 12/16/16.
 */
public class MongloriousClientTest {

    @Test
    public void testMongloriousWithUri() {
        MongloriousClient m = new MongloriousClient("mongodb://localhost:27017/testdb");
    }

    @Test
    public void testMongloriousExecute() throws MongloriousException, IOException {
        try(MongloriousClient m = new MongloriousClient("mongodb://localhost:27017/testdb")) {
            Object results = m.execute("show dbs");
        }
    }

    @Test
    public void testShowDbs() throws MongloriousException {
        MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoIterable<String> expected = mongo.listDatabaseNames();

        MongloriousClient monglorious = new MongloriousClient("mongodb://localhost:27017/testdb");
        List<String> actual = (List<String>) monglorious.execute("show dbs");

        for(String db : expected) {
            Assert.assertThat(actual, CoreMatchers.hasItem(db));
        }
    }

    @Test
    public void testShowCollections() throws MongloriousException {
        MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase database = mongo.getDatabase("testdb");
        MongoIterable<String> expected = database.listCollectionNames();

        MongloriousClient monglorious = new MongloriousClient("mongodb://localhost:27017/testdb");
        List<String> actual = (List<String>) monglorious.execute("show collections");

        for(String db : expected) {
            Assert.assertThat(actual, CoreMatchers.hasItem(db));
        }
    }

    @Test
    public void testCollectionStats() throws MongloriousException {
        MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase database = mongo.getDatabase("testdb");
        Document expected = database.runCommand(new Document("collStats", "documents"));

        MongloriousClient monglorious = new MongloriousClient("mongodb://localhost:27017/testdb");
        Map<String, Object> actual = (Map<String, Object>) monglorious.execute("db.runCommand({ collStats: 'documents' })");

        for(Map.Entry<String, Object> entry : expected.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String || value instanceof Integer) {
                Assert.assertEquals(value, actual.get(entry.getKey()));
            }
        }
    }

    @Test
    public void testCount() throws MongloriousException {
        MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase database = mongo.getDatabase("testdb");
        long expected = database.getCollection("documents").count();

        MongloriousClient monglorious = new MongloriousClient("mongodb://localhost:27017/testdb");
        long actual = (Long) monglorious.execute("db.documents.count()");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testTypedCount() throws MongloriousException {
        MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase database = mongo.getDatabase("testdb");
        long expected = database.getCollection("documents").count();

        MongloriousClient monglorious = new MongloriousClient("mongodb://localhost:27017/testdb");
        long actual = monglorious.execute("db.documents.count()", Long.class);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteAsListShowDbs() throws MongloriousException {
        MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoIterable<String> expected = mongo.listDatabaseNames();

        MongloriousClient monglorious = new MongloriousClient("mongodb://localhost:27017/testdb");
        List<String> actual = monglorious.executeAsList("show dbs", String.class);

        for(String db : expected) {
            Assert.assertTrue(actual.contains(db));
        }
    }

    @Test(expected = ClassCastException.class)
    public void testExecuteTypeError() throws MongloriousException {
        MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase database = mongo.getDatabase("testdb");
        long expected = database.getCollection("documents").count();

        MongloriousClient monglorious = new MongloriousClient("mongodb://localhost:27017/testdb");
        String actual = monglorious.execute("db.documents.count()", String.class);
    }
}
