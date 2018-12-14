package com.beertag.android.views.login;


import com.beertag.android.models.User;

public interface LoginContracts {
    interface View {

        void setPresenter(Presenter presenter);

        void showLoginCredentialsProblemMessage(String message);

        void hideLoginProblemMessage();

        void showLoading();

        void hideLoading();

        void showHomeActivityWithUser(User user);

        void showError(Throwable error);

        void showMessage(String message);

    }

    interface Presenter {
        void getUserByUserName(String s);

        void subscribe(View view);

        void unsubscribe();

        void checkErrorVisibility(int visibilityCode);

        void handleLoginFieldFocusChange(int errorVisibilityCode);
    }

    interface Navigator {

        void navigateToHomeWithUser(User user);

    }
}
