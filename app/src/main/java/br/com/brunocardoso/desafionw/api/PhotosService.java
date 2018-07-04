package br.com.brunocardoso.desafionw.api;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PhotosService {

    @GET("photos/{id}")
    Call<ResponseBody> getPhoto(@Path("id") int id);

    @POST("photos")
    @Multipart
    @Headers("Content-Type: multipart/form-data")
    Call<ResponseBody> postImage(@Part("file") MultipartBody.Part image);

}
