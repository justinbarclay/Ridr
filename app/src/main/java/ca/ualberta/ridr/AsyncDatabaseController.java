package ca.ualberta.ridr;

import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by mackenzie on 09/11/16.
 * To implement database tasks, given by AsyncController
 */
public class AsyncDatabaseController extends AsyncTask<String, Void, JsonObject> {
    private static JestDroidClient client;
    private String action;
    private static String databaseLink
            = "https://search-ridr-3qapqm6n4kj3r37pbco5esgwrm.us-west-2.es.amazonaws.com/";
    private static String databaseName = "ridr";

    /**
     * Instantiates a new Async database controller.
     *
     * @param action the action we are going to do, with this database controller
     */
// Constructor for controller
    public AsyncDatabaseController(String action) {
        this.action = action;
    }

    /**
     * Queries elastic search for an object with matching UUID
     *
     * @return JsonObject
     *
     */
    @Nullable
    @Override
    protected JsonObject doInBackground(String... parameters) {
        verifySettings();

        //search string should work, is searching for the name, only returns 1 result

        // Depending on action run a different async request, returns a JSONObject of the request
        //if successful  or null if something went wrong
        try {
            if(action == "get") {
                return getRequest(parameters[0], parameters[1]).getJsonObject();
            } else if(action == "create"){
                return createRequest(parameters[0], parameters[1], parameters[2]).getJsonObject();
            } else if(action == "getAllFromIndex"){
                return getRequest(parameters[0], parameters[1]).getJsonObject();
            } else if(action == "getAllFromIndexFiltered") {
                return getRequest(parameters[0], parameters[1]).getJsonObject();
            } else if(action == "deleteUserTests"){
                deleteUserTests(parameters[0]);
            }
        } catch (Exception e) {
            Log.i(e.toString(),
                    "Something went wrong when we tried to communicate with the elasticsearch server!");
            return null;
        }

        return null;
    }

    /**
     * A simple function to check if the controller has been set up, and if not then create it
     * This could probably be moved to the constructor
     */
    private static void verifySettings() {
        // if the client hasn't been initialized then we should make it!
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(databaseLink);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }


    @Nullable
    /**
     * A generic function that performs a get request to the elastic search databases
     * @param type
     * @param searchString
     * @throws IOException
     * @return result
     * @nullable
     */
    private JestResult getRequest(String type, String  searchString) throws IOException {
        // As the name implies builds a search object and returns the result
        Search search = new Search.Builder(searchString)
                .addIndex(databaseName)
                .addType(type)
                .build();
        JestResult result = client.execute(search);
        System.out.println(search.toString());
        System.out.println(result.getJsonObject());
        if (result.isSucceeded()) {
            return result;
        }
        else {
            Log.i("Error", "The search query failed to find the Class that matched.");
            return null;
        }
    }

    /**
     * A generic function creates or updates a document in an elastic search database based on a type
     * and ID
     * @param type
     * @param ID
     * @param jsonValue
     * @throws IOException
     * @return result
     * @nullable
     */
    @Nullable
    private JestResult createRequest(String type, String ID, String jsonValue) throws IOException {
        // Takes strings of the type of object, [user, ride, request], the id of the object to create
        // and the json version of that object and posts it to the server
        // It returns a jsonObject representing the results of the operation or null if it failed
        System.out.println(jsonValue);
        Index index = new Index.Builder(jsonValue).index(databaseName).type(type).id(ID).build();
        DocumentResult result = client.execute(index);
        if (result.isSucceeded()) {
            return result;
        }
        else {
            Log.d("error", result.getJsonObject().toString());
            Log.i("Error", "The search query failed to find the Class that matched.");
            return null;
        }
    }

    //for tests, not sure where else to put
    private void deleteUserTests(String ID){
        verifySettings();
        try {
            client.execute(new Delete.Builder(ID)
                    .index(databaseName)
                    .type("user")
                    .build());
        } catch (Exception e){
            Log.i("ERROR", "Couldn't delete previous driver test objects from elastic search");
        }
    }
}

