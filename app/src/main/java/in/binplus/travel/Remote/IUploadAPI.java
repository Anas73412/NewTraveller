package in.binplus.travel.Remote;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 28,January,2020
 */
public interface IUploadAPI {
    @Multipart
    @POST("upload_image")
    Call<String> uploadFile(@Part MultipartBody.Part file);

}
