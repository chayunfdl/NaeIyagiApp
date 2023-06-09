package com.chayun.naeiyagiapp.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("error")
    var error: Boolean,

    @field:SerializedName("loginResult")
    var loginResult: LoginResult
)

data class LoginResult(
    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("token")
    var token: String
)
