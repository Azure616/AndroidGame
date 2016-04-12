package cloyster.cs2110.virginia.edu.ghosthunter20;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * T102-06
 * Bowen Ke(bk7bb), Tianyang He(th4xa), Haoyang Li(hl3fm)
 */
public class GameOver extends Activity {
    Button again;
    Button end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        again = (Button)findViewById(R.id.button_start);
        end = (Button)findViewById(R.id.button_end);
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent("cloyster.cs2110.virginia.edu.ghosthunter20.MAIN");
                startActivity(main);
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }
}
