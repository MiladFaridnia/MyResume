package faridnia.milad.myresume.activity;

import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import faridnia.milad.myresume.R;
import fragment.ExperienceFragment;

public class MainActivity extends AppCompatActivity implements  ExperienceFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
              //  messageView.setText(TabMessage.get(tabId, false));
                switch (tabId){
                    case R.id.tab_experience:
                        initFragment(ExperienceFragment.newInstance());
                        break;

                    case R.id.tab_education:
                        initFragment(ExperienceFragment.newInstance());
                        break;

                    case R.id.tab_interests:
                        initFragment(ExperienceFragment.newInstance());
                        break;

                    case R.id.tab_contact:
                        initFragment(ExperienceFragment.newInstance());
                        break;

                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
            }
        });




    }

    private void initFragment(Fragment fragment) {
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        //ExperienceFragment fragment =  ExperienceFragment.newInstance();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit).add(R.id.contentContainer, fragment).commit();
    }


}
