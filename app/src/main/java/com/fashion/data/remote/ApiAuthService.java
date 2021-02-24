package com.fashion.data.remote;

import com.fashion.core.ResponseBase;
import com.fashion.data.model.auth.LoginDTO;
import com.fashion.data.model.auth.SignUpDTO;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiAuthService {
    /*-------------- AUTH API --------------*/
    @POST("signup")
    Observable<ResponseBase<SignUpDTO.Response>> signUp(@Body SignUpDTO.EmailRequest request);

    @POST("signupfacebook")
    Observable<ResponseBase<SignUpDTO.Response>> signUpByFacebook(@Body SignUpDTO.FacebookRequest request);

    @POST("signupgoogle")
    Observable<ResponseBase<SignUpDTO.Response>> signUpByGoogle(@Body SignUpDTO.GoogleRequest request);

    @POST("signupapple")
    Observable<ResponseBase<SignUpDTO.Response>> signUpByApple(@Body SignUpDTO.AppleRequest request);

    @POST("login")
    Observable<ResponseBase<LoginDTO.Response>> login(@Body LoginDTO.EmailRequest request);

    @POST("loginfacebook")
    Observable<ResponseBase<LoginDTO.Response>> loginByFacebook(@Body LoginDTO.FacebookRequest request);

    @POST("logingoogle")
    Observable<ResponseBase<LoginDTO.Response>> loginByGoogle(@Body LoginDTO.GoogleRequest request);

    @POST("loginapple")
    Observable<ResponseBase<LoginDTO.Response>> loginByApple(@Body LoginDTO.AppleRequest request);

    @POST("logout")
    Observable<ResponseBody> logout();

    @GET("me")
    Observable<ResponseBody> getMe();

    @POST("user/{email}/forgotpasswords")
    Completable forgotPassword(@Path("email") String email);

    @POST("verifyforgotpasswords")
    Observable<ResponseBody> verifyForgotPassword(@Body LoginDTO.VerifyForgotPasswordRequest request);
}
