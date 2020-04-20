package com.techuva.councellorapp.contus_Corporate.restclient;

import com.squareup.okhttp.OkHttpClient;
import com.techuva.councellorapp.contus_Corporate.apiinterface.LocationApiInterface;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by user on 8/13/2015.
 */
public class LocationRestClient {
    private static LocationApiInterface sPersonalInterface;//Location api interface
    private static String root = "http://maps.google.com/maps";    /** The root. */
    static {
        setupRestClient();
    }
    /**
     * Instantiates a new settings rest client.
     */
    private LocationRestClient() {
    }
    /**
     * Gets the.
     *
     * @return the settings api interface
     */
    public static LocationApiInterface getInstance() {
        return sPersonalInterface;
    }

    /**
     * Setup rest client.
     */
    private static void setupRestClient() {
        //HTTP is the way modern applications network. It’s how we exchange data & media.
        // Doing HTTP efficiently makes your stuff load faster and saves bandwidth.
        OkHttpClient ohHttp= new OkHttpClient();
        //y to set the read timeout as well
        ohHttp.setConnectTimeout(120, TimeUnit.SECONDS);
        //The REST adapter allows your store to communicate with an HTTP server by transmitting JSON via XHR.
        // Most Ember.js apps that consume a JSON API should use the REST adapter.
        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(root).setLogLevel(RestAdapter.LogLevel.FULL).setClient(new OkClient(ohHttp));
        RestAdapter restAdapter = builder.build();
        //create the rest adapter
        sPersonalInterface = restAdapter.create(LocationApiInterface.class);
    }
}
