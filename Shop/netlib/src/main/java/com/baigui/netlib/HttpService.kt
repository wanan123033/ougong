package com.baigui.netlib

import com.baigui.commonlib.utils.ReflectToStringUtil
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import io.reactivex.Observable


interface HttpService {


    @GET("/repos/{owner}/{repo}/contributors")
    fun contributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Flowable<List<Contributor>>
}
