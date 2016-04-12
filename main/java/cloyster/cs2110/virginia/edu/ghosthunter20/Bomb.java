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
public class Bomb {
    private int x;
    private int y;
    private boolean goOff;
    private Bitmap bitmap;
    private Bitmap boom;
    private Rect hitBox;

    public Bomb(Context context, int x, int y) {
        this.bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb1), 100, 100, false);
        this.boom = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion), 200, 200, false);
        this.x = x;
        this.y = y;
        this.goOff = false;
        this.hitBox = new Rect(this.x-50, this.y-50, this.x+50, this.y+50);

    }
    public boolean collideWith(Rect r) {
        return hitBox.intersect(r);
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap,x-50,y-50,null);
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean getGoOff(){
        return this.goOff;
    }
    public void setGoOff (boolean goOff) {
        this.goOff = goOff;
    }
    public void Boom(){
        this.bitmap = this.boom;
    }
}
