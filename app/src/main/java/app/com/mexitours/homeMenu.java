package app.com.mexitours;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import app.com.mexitours.fragmentsMenu.circuitosQR;
import app.com.mexitours.fragmentsMenu.homeFragment;
import app.com.mexitours.fragmentsMenu.perfilFragment;
import app.com.mexitours.fragmentsMenu.toursFragment;
import app.com.mexitours.fragmentsMenu.transfersFragment;

public class homeMenu extends AppCompatActivity {

    private static final String SHRD_PREF = "app.com.mexitours";
    public static final String PREF_IUS ="state.log.user.ius";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            Fragment selectedFragment = null;


            switch (item.getItemId()) {
                case R.id.tour_navigation:
                    selectedFragment = new toursFragment();
                    break;
                case R.id.navigation_qr:
                    selectedFragment = new circuitosQR();
                    break;
                case R.id.navigation_home:
                    selectedFragment = new homeFragment();
                    break;
                case R.id.navigation_transfers:
                    selectedFragment = new transfersFragment();
                    break;
                case R.id.navigation_perfil:
                    selectedFragment = new perfilFragment();
                    break;


            }
            Bundle bundle = new Bundle();
            Integer IUS =  getIUS();
            bundle.putInt(PREF_IUS, IUS);
            //System.out.println("HOME MENU"+IUS);
            selectedFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frmLayoutContainer, selectedFragment).commit();
            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);
        //BottomNavigationView navView = findViewById(R.id.nav_view);
        Fragment home = new toursFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frmLayoutContainer, home).commit();
        BottomNavigationView navView=(BottomNavigationView)findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);


    }

    public Integer getIUS(){
        SharedPreferences preferences = getSharedPreferences(SHRD_PREF, MODE_PRIVATE);
        return preferences.getInt(PREF_IUS,0);
    }
}
