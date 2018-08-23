package com.flyone.mymarket.fragment;

import com.flyone.mymarket.bean.Good;

import java.util.List;

/**
 * Created by wl624 on 2018/4/20.
 */

public interface MVP_View {
    public void onLatestSuccess(List<Good> list);
    public void onMoreSuccess(List<Good> list);
    public void onFailure();

}
