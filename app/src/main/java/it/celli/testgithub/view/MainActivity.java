package it.celli.testgithub.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import it.celli.testgithub.R;
import it.celli.testgithub.application.App;
import it.celli.testgithub.data.Stargazer;
import it.celli.testgithub.di.PicassoInstance;
import it.celli.testgithub.di.RetrofitInstance;
import it.celli.testgithub.presenter.MainPresenter;
import it.celli.testgithub.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.owner)
    EditText mOwnerView;

    @BindView(R.id.repo)
    EditText mRepoView;

    @BindView(R.id.search)
    Button mSearch;

    @BindView(R.id.stargazers_list)
    RecyclerView mRecyclerView;

    @Inject
    RetrofitInstance mRetrofitInstance;

    @Inject
    PicassoInstance mPicsssoInstance;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private MainAdapter mAdapter;

    private MainPresenter mMainPresenter;

    private int mCurrentPage;
    private boolean mIsFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((App) getApplication()).getAppComponent().inject(this);
        mMainPresenter = new MainPresenter(mRetrofitInstance);

        setTitle(R.string.activity_title);

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        RxTextView.textChanges(mOwnerView)
                .subscribe(sequence -> {
                    boolean clickable = (!sequence.toString().trim().isEmpty() && !mRepoView.getText().toString().trim().isEmpty());
                    if(clickable) {
                        mSearch.setVisibility(View.VISIBLE);
                    }
                    else {
                        mSearch.setVisibility(View.INVISIBLE);
                    }
                });

        RxTextView.textChanges(mRepoView)
                .subscribe(sequence -> {
                    boolean clickable = (!sequence.toString().trim().isEmpty() && !mOwnerView.getText().toString().trim().isEmpty());
                    if(clickable) {
                        mSearch.setVisibility(View.VISIBLE);
                    }
                    else {
                        mSearch.setVisibility(View.INVISIBLE);
                    }
                });
    }

    @OnClick(R.id.search)
    public void performSearch() {
        closeKeyboard();

        mCurrentPage = 1;
        mIsFinished = false;
        loadPage();
    }

    private void loadAdapter(List<Stargazer> list) {
        mAdapter = new MainAdapter(mPicsssoInstance, list);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnBottomReachedListener(position -> loadPage());
    }

    private void loadPage() {
        if(mIsFinished) {
            Log.e("PIPPO", "IsFinished");
            return;
        }

        Disposable disposable = mMainPresenter.loadStargazers(mOwnerView.getText().toString().trim(), mRepoView.getText().toString().trim(), mCurrentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe( list -> {
                                if(list.isEmpty()) {
                                    mIsFinished = true;
                                    if(mCurrentPage == 1) {
                                        Utils.showErrorDialog(this, getString(R.string.error_no_stargazers));
                                    }
                                }
                                else {
                                    if(mCurrentPage == 1) {
                                        loadAdapter(list);
                                    }
                                    else {
                                        for(Stargazer stargazer : list) {
                                            mAdapter.add(stargazer);
                                        }
                                    }
                                    mCurrentPage++;
                                }},
                            throwable -> Utils.showErrorDialog(this, throwable.getMessage())

                );

        mCompositeDisposable.add(disposable);
    }

    private void closeKeyboard() {
        if(getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }
}
