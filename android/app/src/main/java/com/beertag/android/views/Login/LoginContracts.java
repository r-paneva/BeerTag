package com.beertag.android.views.Login;


import com.beertag.android.models.User;

public interface LoginContracts {
    interface View {

    }

    interface Presenter {
        User getUserByName(String s);
    }

    interface Navigator {

    }
}
