package org.baumandm.monglorious.java;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import monglorious.core.MongloriousConnection;
import org.baumandm.monglorious.Monglorious;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * Monglorious client. Creates and maintains a persistent connection until close() is called.
 *
 * Created by baumandm on 12/16/16.
 */
public class MongloriousClient implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(MongloriousClient.class);

    private MongoClient mongoClient;

    private DB database;

    /**
     * Create a new Monglorious client, connected to the given URI (including database).
     *
     * @param uri
     */
    public MongloriousClient(final String uri) {
        this.connect(uri);
    }

    /**
     * Create a new Monglorious client, using the provided MongoClient and DB.
     *
     * @param client
     * @param database
     */
    public MongloriousClient(final MongoClient client, final DB database) {
        this.setMongoClient(client);
        this.setDatabase(database);
    }

    /**
     * Open a connection to a MongoDB database using a valid MongoDB URI.
     *
     * @param uri A valid MongoDB URI.
     */
    private void connect(final String uri) {
        MongloriousConnection connection = (MongloriousConnection) Monglorious.getConnection(uri);

        this.setMongoClient((MongoClient) connection.conn);
        this.setDatabase((DB) connection.db);
    }

    /**
     * Executes a MongoDB query and returns the result.
     *
     * May return various types depending on the provided query.
     *
     * @param query The MongoDB query to execute
     * @return Returns the result of the query.
     * @throws MongloriousException
     */
    public Object execute(final String query) throws MongloriousException {
        if (this.getMongoClient() == null) {
            LOG.error("This MongloriousClient instance has a null MongoClient; either a null value was passed to the constructor, or close() was called. Either way, the connection cannot be reopened and this instance cannot be used. A new MongloriousClient should be created.");
            throw new MongloriousException("Cannot execute query: Monglorious connection to MongoDB is not open");
        }

        LOG.debug("Executing Mongo query...");
        Object results = Monglorious.executeWithConnection(this.getMongoClient(), this.getDatabase(), query);

        LOG.debug("Completed Mongo query...");
        return results;
    }

    /**
     * Executes a MongoDB query and returns the result, cast to a particular Class.
     *
     * @param query The MongoDB query to execute.
     * @param type The expected return type.
     * @return Returns the result of the query.
     * @throws MongloriousException
     */
    public <T extends Object> T execute(final String query, final Class<T> type) throws MongloriousException {
        return type.cast(this.execute(query));
    }

    /**
     * Executes a MongoDB query and returns the result, cast to a List&lt;T&gt;
     *
     * @param query The MongoDB query to execute
     * @param type The expected returned List element type.
     * @return Returns the result of the query.
     * @throws MongloriousException
     */
    public <T extends Object> List<T> executeAsList(final String query, final Class<T> type) throws MongloriousException {
        return (List<T>) this.execute(query);
    }

    /**
     * Gets the MongoClient instance used to execute queries.s
     * @return A MongoClient instance.
     */
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    protected void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    /**
     * Gets the MongoDB database instance used to execute queries.
     * @return A DB instance.
     */
    public DB getDatabase() {
        return database;
    }

    protected void setDatabase(DB database) {
        this.database = database;
    }

    /**
     * Closes any open MongoDB connection.
     * Once closed, this MongloriousClient instance cannot be used again.
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        if (this.getMongoClient() != null) {
            this.getMongoClient().close();
            this.setMongoClient(null);
        }
    }
}
