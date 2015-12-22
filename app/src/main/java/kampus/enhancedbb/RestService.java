package kampus.enhancedbb;

/**
 * Created by Павел on 13.12.2015.
 */
public class RestService {
    //You need to change the IP if you testing environment is not local machine
    //or you may have different URL than we have here
    private static final String URL = "http://campusbbapi.azurewebsites.net/";
    private retrofit.RestAdapter restAdapter;
    private BBService apiService;

    public RestService()
    {
        restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint(URL)
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .build();

        apiService = restAdapter.create(BBService.class);
    }

    public BBService getService()
    {
        return apiService;
    }
}

