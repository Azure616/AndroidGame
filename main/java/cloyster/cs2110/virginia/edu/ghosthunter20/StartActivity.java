package cloyster.cs2110.virginia.edu.ghosthunter20;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * T102-06
 * Bowen Ke(bk7bb), Tianyang He(th4xa), Haoyang Li(hl3fm)
 */
public class StartActivity extends Activity {
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent("cloyster.cs2110.virginia.edu.ghosthunter20.MAIN");
                startActivity(main);
            }
        });
    }
}
