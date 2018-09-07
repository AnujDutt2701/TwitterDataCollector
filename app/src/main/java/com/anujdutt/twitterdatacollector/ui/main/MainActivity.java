package com.anujdutt.twitterdatacollector.ui.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.anujdutt.twitterdatacollector.R;
import com.anujdutt.twitterdatacollector.adapter.SearchResultAdapter;
import com.anujdutt.twitterdatacollector.binding.FragmentBindingComponent;
import com.anujdutt.twitterdatacollector.databinding.ActivityMainBinding;
import com.anujdutt.twitterdatacollector.ui.main.topic.TopicFragment;
import com.anujdutt.twitterdatacollector.ui.main.searchtweet.SearchTweetFragment;
import com.anujdutt.twitterdatacollector.viewmodel.MainActivityViewModel;
import com.anujdutt.twitterdatacollector.viewmodel.TwitterDataCollectorViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {


    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    TwitterDataCollectorViewModelFactory twitterDataCollectorViewModelFactory;

    ActivityMainBinding activityMainBinding;

    FragmentBindingComponent fragmentBindingComponent;

    MainActivityViewModel mainActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentBindingComponent = new FragmentBindingComponent();
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main, fragmentBindingComponent);
        setupViewModel();
        initialiseUI();
        setBottomNavigation();
    }

    private void initialiseUI() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    private void setBottomNavigation() {
        activityMainBinding.bvnNavigator.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_search_tweets) {
                    navigateToSearchTweetFragment();
                    return true;
                } else if (menuItem.getItemId() == R.id.action_adjust_params) {
                    navigateToAddTopicFragment();
                    return true;
                }
                return false;
            }
        });
        activityMainBinding.bvnNavigator.setSelectedItemId(R.id.action_adjust_params);
    }

    public void navigateToSearchTweetFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new SearchTweetFragment(), "SearchTweetFragment");
        fragmentTransaction.commit();
    }

    public void navigateToAddTopicFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new TopicFragment(), "TopicFragment");
        fragmentTransaction.commit();
    }

    private void setupViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this, twitterDataCollectorViewModelFactory).get(MainActivityViewModel.class);
        //mainActivityViewModel.initialiseAllParams(cryptoCurrencyId);
//        mainActivityViewModel.getParamsLiveData().observe(this, new Observer<CryptoDetailsActivityViewModel.CryptoCurrencyDetailsActivityParams>() {
//
//            @Override
//            public void onChanged(CryptoDetailsActivityViewModel.CryptoCurrencyDetailsActivityParams cryptoCurrencyDetailsActivityParams) {
//                if (cryptoCurrencyDetailsActivityParams.cryptoCurrency != null) {
//                    activityCryptoDetailsBinding.setCryptoCurrency(cryptoCurrencyDetailsActivityParams.cryptoCurrency);
//                    startPostponedEnterTransition();
//                }
//            }
//        });
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
