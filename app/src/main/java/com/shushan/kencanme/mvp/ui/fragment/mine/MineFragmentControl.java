package com.shushan.kencanme.mvp.ui.fragment.mine;


import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class MineFragmentControl {
    public interface MineView extends LoadDataView {
//        void getInfoSuccess(HomeFragmentResponse response);
//        void getInfoFail(String errMsg);

    }

    public interface mineFragmentPresenter extends Presenter<MineView> {
    }


}