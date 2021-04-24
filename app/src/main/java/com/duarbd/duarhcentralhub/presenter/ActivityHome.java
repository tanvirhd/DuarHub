package com.duarbd.duarhcentralhub.presenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.databinding.ActivityHomeBinding;
import com.duarbd.duarhcentralhub.model.ModelResponse;
import com.duarbd.duarhcentralhub.model.ModelToken;
import com.duarbd.duarhcentralhub.network.viewmodel.ViewModelHub;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class ActivityHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "ActivityHome";
    private ActivityHomeBinding binding;
    private ViewModelHub viewModelHub;
    private ActionBarDrawerToggle actionBarDrawerToggle;

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
            case R.id.navDeliveryHistory:
                Toast.makeText(this, "Delivery History", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ActivityHome.this,MapsActivity.class));
                break;
            case R.id.logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.drawerLayout.closeDrawer(GravityCompat.START);

    }
}