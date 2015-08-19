package com.example.tazeen.classnkk;
import android.content.SharedPreferences;
import android.view.Window;
import android.os.Bundle;

import android.content.Intent;
import android.app.Activity;

/*LDPI: Portrait: 200x320px Landscape: 320x200px MDPI: Portrait: 320x480px Landscape: 480x320px HDPI: Portrait: 480x800px Landscape: 800x480px XHDPI: Portrait: 720px1280px Landscape: 1280x720px

*/
public class Splash_Screen  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash_page);

        Thread loading = new Thread() {
            public void run() {
                try {


                    SharedPreferences settings = getSharedPreferences(Login_Screen.PREFS_NAME, 0);
                    //Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
                    boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

                    if(hasLoggedIn)
                    {
                        sleep(5000);
                        Intent main = new Intent(Splash_Screen.this, AllPosts_Page.class);
                        startActivity(main);
                    }
                    else
                    {
                        sleep(5000);
                        Intent main = new Intent(Splash_Screen.this, Login_Screen.class);
                        startActivity(main);

                    }
                }

                catch (Exception e) {
                    e.printStackTrace();
                }

                finally {
                    finish();
                }
            }
        };

        loading.start();
    }

}



//http://stackoverflow.com/questions/12852845/how-to-skip-the-first-activity-under-a-condition