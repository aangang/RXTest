package com.android.rxtest.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.android.rxtest.R;
import com.android.rxtest.bean.AbilityBean;
import com.android.rxtest.bean.AttentionBean;
import com.android.rxtest.fragment.base.RecyclerViewFragment;
import com.android.rxtest.requests.MovieRequest;
import com.android.rxtest.utils.RetrofitUtil;
import com.android.rxtest.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by gang.an on 2017/10/30.
 */

public class AttentionFragment extends RecyclerViewFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private CommonAdapter<AttentionBean> adapter;
    private MovieRequest movieRequest;

    private List<AttentionBean> datas = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void loadData() {
        getMovie();
    }
    private void getMovie() {
        movieRequest = RetrofitUtil.getInstance().create(MovieRequest.class);
        movieRequest.getMovies(0, 10)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AbilityBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast(getActivity(), getString(R.string.request_error));
                    }

                    @Override
                    public void onNext(AbilityBean abilityBean) {
                        swipeRefreshLayout.setRefreshing(false);
                        datas.clear();
                        ToastUtils.showToast(getActivity(), getString(R.string.request_success));
                        for (int i = 0; i < abilityBean.subjects.size(); i++) {
                            datas.add(new AttentionBean(abilityBean.subjects.get(i).images.small, abilityBean.subjects.get(i).original_title));
                        }

                        adapter = new CommonAdapter<AttentionBean>(getActivity(), R.layout.item_fragment_attention_recyclerview, datas) {
                            @Override
                            protected void convert(ViewHolder holder, final AttentionBean attentionBean, final int position) {

                                holder.setText(R.id.tx_attention_title, attentionBean.getTitle());
                                ImageView circleImageView = holder.getView(R.id.img_attention);
                                Glide.with(getActivity()).load(attentionBean.getIcon()).into(circleImageView);
                            }
                        };
                        recyclerView.setAdapter(adapter);
                    }
                });
    }
    @Override
    protected void adapterBuilder() {

    }
    @Override
    public void onRefresh() {
        super.onRefresh();
        getMovie();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attention;
    }

}

