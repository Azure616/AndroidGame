package cloyster.cs2110.virginia.edu.ghosthunter20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * T102-06
 * Bowen Ke(bk7bb), Tianyang He(th4xa), Haoyang Li(hl3fm)
 */
public class Heart {
    private Bitmap bitmap;
    private int x;
    private int y;
    private Rect hitBox;

    public Heart(Context context, int x, int y){
        this.bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.heart),40,40,false);
        this.x = x;
        this.y = y;
        this.hitBox = new Rect(this.x-20,this.y-20,this.x+20,this.y+20);
    }

    public boolean collideWith(Rect r) {
        return hitBox.intersect(r);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x-20, y-20, null);
    }
}
