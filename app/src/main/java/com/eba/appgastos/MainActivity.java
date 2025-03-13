package com.eba.appgastos;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.eba.appgastos.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_ahorros, R.id.navigation_gastos, R.id.navigation_gastos_fijos)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buttom_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            String currentDestinationId = navController.getCurrentDestination().getLabel().toString();
            switch (currentDestinationId) {
                case "Ahorros":
                    navController.navigate(R.id.navigation_form_ahorros);
                    break;
                case "Gastos":
//                    Toast.makeText(this, "Fragmento Gastos activo", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.navigation_form_gastos);
                    break;
                case "Gastos Fijos":
                    navController.navigate(R.id.navigation_form_gastos_fijos_form);
//                    Toast.makeText(this, "Fragmento Gastos Fijos activo", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}