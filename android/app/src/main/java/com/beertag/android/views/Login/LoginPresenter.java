package com.beertag.android.views.login;


import com.beertag.android.async.base.SchedulerProvider;
import com.beertag.android.models.User;
import com.beertag.android.services.base.UsersService;
import com.beertag.android.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;


public class LoginPresenter implements LoginContracts.Presenter {

    private final UsersService mUserService;
    private final SchedulerProvider mSchedulerProvider;
    private LoginContracts.View mView;

    @Inject
    public LoginPresenter(UsersService usersService, SchedulerProvider schedulerProvider) {
        mUserService = usersService;
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void subscribe(LoginContracts.View view) {
        mView = view;
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }


    @Override
    public void getUserByUserName(String name) {
        mView.showLoading();
        Disposable observable = Observable
                .create((ObservableOnSubscribe<User>) emitter -> {
                    User userToCheck = mUserService.getUserByUserName(name);
                    emitter.onNext(userToCheck);
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::hideLoading)
                .subscribe(userResult -> {
                    if(userResult.getUserName()!=null) {
                        mView.showMessage("Successful login");
                        mView.showHomeActivityWithUser(userResult);
                    }else{
                        mView.showMessage("This user doesn't exist");
                    }
                        },
                        error -> {
                            if (error instanceof NullPointerException) {
                                mView.showError(error);
                            } else {
                                mView.showError(error);
                            }
                        });
    }

}
