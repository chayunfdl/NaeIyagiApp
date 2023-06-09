package com.chayun.naeiyagiapp.data.api

import com.chayun.naeiyagiapp.data.response.AddIyagiResponse
import com.chayun.naeiyagiapp.data.response.IyagiResponse
import com.chayun.naeiyagiapp.data.response.LoginResponse
import com.chayun.naeiyagiapp.data.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @FormUrlEncoded
    @POST("login")
    fun postLoginData(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun postRegisterData(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @GET("stories")
    fun getIyagiData(
        @Header("Authorization") token: String,
        @Query("location") location: Int? = null
    ): Call<IyagiResponse>

    @GET("stories")
    suspend fun getIyagiPaging(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): IyagiResponse

    @Multipart
    @POST("stories")
    fun postIyagiData(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ):Call<AddIyagiResponse>
}