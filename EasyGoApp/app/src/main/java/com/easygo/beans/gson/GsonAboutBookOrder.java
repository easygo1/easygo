package com.easygo.beans.gson;

import com.easygo.beans.order.Order;
import com.easygo.beans.user.UserLinkman;

import java.util.List;

/**
 * Created by PengHong on 2016/6/8.
 */
public class GsonAboutBookOrder {
    private Order order;
    private List<UserLinkman> mUserLinkmanList;

    public GsonAboutBookOrder(Order order, List<UserLinkman> userLinkmanList) {
        this.order = order;
        mUserLinkmanList = userLinkmanList;
    }

    public List<UserLinkman> getUserLinkmanList() {
        return mUserLinkmanList;
    }

    public void setUserLinkmanList(List<UserLinkman> userLinkmanList) {
        mUserLinkmanList = userLinkmanList;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
