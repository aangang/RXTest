package com.android.rxtest.requests;


import com.android.rxtest.bean.AbilityBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ${符柱成} on 2017/1/21.
 */

public interface MovieRequest {
//    //请求参数
    @GET("/v2/movie/top250")
    Observable<AbilityBean> getMovies(@Query("start") int start, @Query("count") int count);
}
