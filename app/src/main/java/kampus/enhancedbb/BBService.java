package kampus.enhancedbb;

/**
 * Created by Павел on 13.12.2015.
 */

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface BBService {

    @FormUrlEncoded
    @POST("/api/Account/")
    //@Headers({"Content-Type: application/json;charset=UTF-8"})
    public void getUser(@Field("login") String login, @Field("password") String password, Callback<Account> callback);

    @FormUrlEncoded
    @POST("/api/Bulletin/All")
    public void getAllBulletins(@Field("id") long id, Callback<List<Bulletin>> callback);

    @FormUrlEncoded
    @POST("/api/Bulletin/Actual")
    public void getActualBulletins(@Field("id") long id, Callback<List<Bulletin>> callback);

    @FormUrlEncoded
    @POST("/api/Bulletin/Profile")
    public void getBulletinsByProfile(@Field("id") long id, Callback<List<Bulletin>> callback);

    @FormUrlEncoded
    @POST("/api/Bulletin/Subdivision")
    public void getBulletinsBySubdiv(@Field("id") long id, Callback<List<Bulletin>> callback);




//Retrofit turns our institute WEB API into a Java interface.
    //So these are the list available in our WEB API and the methods look straight forward
    /*
    //i.e. http://localhost/api/institute/Students
    @GET("/posts/")
    public void getBB(Callback<List<IdleBB>> callback);

    //i.e. http://localhost/api/institute/Students/1
    //Get student record base on ID
    @GET("/posts/{id}")
    public void getBBById(@Path("id") Integer id,Callback<IdleBB> callback);

    @GET("/posts/{UserId}")
    public void getBBByUID(@Path("UserId") Integer id,Callback<IdleBB> callback);

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
    */
}