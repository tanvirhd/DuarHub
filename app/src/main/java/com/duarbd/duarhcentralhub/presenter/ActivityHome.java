package com.duarbd.duarhcentralhub.presenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.adapter.AdapterDeliveryRequest;
import com.duarbd.duarhcentralhub.adapter.AdapterViewPager;
import com.duarbd.duarhcentralhub.databinding.ActivityHomeBinding;
import com.duarbd.duarhcentralhub.interfaces.AdapterDeliveryRequestCallbacks;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;
import com.duarbd.duarhcentralhub.model.ModelResponse;
import com.duarbd.duarhcentralhub.model.ModelToken;
import com.duarbd.duarhcentralhub.network.viewmodel.ViewModelHub;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class ActivityHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "ActivityHome";
    private ActivityHomeBinding binding;
    private ViewModelHub viewModelHub;
    public final int ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS_REQUEST_CODE = 2324;
    private PowerManager powerManager;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    private AdapterViewPager adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        initNavDrawer();
        storeFCMToken();
    }

    void init(){
        setSupportActionBar(binding.toolbar);
        viewModelHub=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelHub.class);
        adapterViewPager=new AdapterViewPager(ActivityHome.this);
        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

        binding.viewpager.setAdapter(adapterViewPager);
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(
                binding.tabLayout,  binding.viewpager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position){
                            case 0:
                                tab.setText("Delivery Request");
                                break;
                            case 1:
                                tab.setText("Duar Order");
                        }
                    }
                });
        tabLayoutMediator.attach();
        wrapTabIndicatorToTitle(binding.tabLayout,50,50);

        if(!isIgnoringBatteryOptimizationsGranted()) requestIgnoreBatteryOptimizationPermission();
    }

    void initNavDrawer(){
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                binding.drawerLayout, binding.toolbar, R.string.open, R.string.close);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black0, null));
        else actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black0));
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        binding.homeNavigationBar.setNavigationItemSelectedListener(this);
    }

    private void storeFCMToken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                viewModelHub.updateFCMToken(new ModelToken(s))
                        .observe(ActivityHome.this, new Observer<ModelResponse>() {
                            @Override
                            public void onChanged(ModelResponse modelResponse) {
                                if(modelResponse.getResponse()==1)
                                    Toast.makeText(ActivityHome.this, "token updated", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(ActivityHome.this, "token update failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navRegisterClient:
                startActivity(new Intent(ActivityHome.this,ActivityRegisterNewClient.class));
                break;
            case R.id.navRegisterRider:
                startActivity(new Intent(ActivityHome.this,ActivityRegisterNewRider.class));
                break;
            case R.id.navClientBillClearance:
                startActivity(new Intent(ActivityHome.this,ActivityClientClearance.class));
                break;
            case R.id.navRiderBillClearance:
                startActivity(new Intent(ActivityHome.this,ActivityRiderClearance.class));
                break;
            case R.id.navDeliveryHistory:
                startActivity(new Intent(ActivityHome.this,ActivityDeliveryHistory.class));
                break;
            case R.id.navSalaryCalculation:
                startActivity(new Intent(ActivityHome.this,ActivitySalaryCalculation.class));
                break;
            case R.id.navRiderStatus:
                startActivity(new Intent(ActivityHome.this,ActivityRiderStatus.class));
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: called");
        super.onResume();
        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                tabView.setMinimumWidth(0);
                // set padding to 0 for wrapping indicator as title
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                // setting custom margin between tabs
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        settingMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        settingMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        settingMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }
            tabLayout.requestLayout();
        }
    }
    private void settingMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
    }

    void requestIgnoreBatteryOptimizationPermission() {
        Log.d(TAG, "requestIgnoreBatteryOptimizationPermission: called");
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Uri.parse("package:" + getApplicationContext().getPackageName()));
        }
        startActivityForResult(intent, ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS_REQUEST_CODE){
            switch (resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "Permission Granted!!", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "Permission Denied!!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private boolean isIgnoringBatteryOptimizationsGranted() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            return true;
        } else if (powerManager.isIgnoringBatteryOptimizations(getApplicationContext().getPackageName())) {
            return true;
        } else return false;
    }

}