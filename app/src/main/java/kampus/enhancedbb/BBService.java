package kampus.enhancedbb;

/**
 * Created by Павел on 13.12.2015.
 */

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface BBService {

//Retrofit turns our institute WEB API into a Java interface.
    //So these are the list available in our WEB API and the methods look straight forward

    //i.e. http://localhost/api/institute/Students
    @GET("/posts/")
    public void getBB(Callback<List<IdleBB>> callback);

    //i.e. http://localhost/api/institute/Students/1
    //Get student record base on ID
    @GET("/posts/{id}")
    public void getBBById(@Path("id") Integer id,Callback<IdleBB> callback);

    //i.e. http://localhost/api/institute/Students/1
    //Delete student record base on ID
    @DELETE("/posts/{id}")
    public void deleteBBById(@Path("id") Integer id,Callback<IdleBB> callback);

    //i.e. http://localhost/api/institute/Students/1
    //PUT student record and post content in HTTP request BODY
    @PUT("/posts/{id}")
    public void updateBBById(@Path("id") Integer id,@Body IdleBB bb,Callback<IdleBB> callback);

    //i.e. http://localhost/api/institute/Students
    //Add student record and post content in HTTP request BODY
    @POST("/posts")
    public void addBB(@Body IdleBB bb,Callback<IdleBB> callback);

}