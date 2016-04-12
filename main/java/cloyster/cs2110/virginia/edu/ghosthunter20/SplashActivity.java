package cloyster.cs2110.virginia.edu.ghosthunter20;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * T102-06
 * Bowen Ke(bk7bb), Tianyang He(th4xa), Haoyang Li(hl3fm)
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        IntentLauncher l = new IntentLauncher();
        l.start();

    }
    private class IntentLauncher extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(new Long(5000));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("Start", "Start Splash");
            startActivity(new Intent("cloyster.cs2110.virginia.edu.ghosthunter20.START"));
        }
    }
}

