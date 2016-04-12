package cloyster.cs2110.virginia.edu.ghosthunter20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

/**
 * T102-06
 * Bowen Ke(bk7bb), Tianyang He(th4xa), Haoyang Li(hl3fm)
 */
public class Women {
    private Bitmap[] bitmaps;
    private Bitmap bitmap;
    private int x;
    private int y;
    private Rect hitBox;

    public Women(Context context, int x, int y){
        this.bitmaps = loadGhosts(context);
        double ran = Math.random();
        //Log.d("Ran",""+ran);
        if(ran < 0.5) {
            this.bitmap = bitmaps[0];
        }else{
            this.bitmap = bitmaps[1];
        }
        this.x = x;
        this.y = y;
        this.hitBox = new Rect(this.x-40,this.y-40,-this.x+40,this.y+40);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void move(Human human) {
        if (human.getX() > this.x) {
            this.x = this.x + 1;
        } else if (human.getX() == this.x) {
            this.x = this.x;
        } else {
            this.x = this.x - 1;
        }
        if (human.getY() > this.y) {
            this.y = this.y + 1;
        } else if (human.getY() == this.y) {
            this.y = this.y;
        } else {
            this.y = this.y - 1;
        }
        this.hitBox = new Rect(this.x-40,this.y-40,this.x+40,this.y+40);
    }
    public boolean collideWith (Rect g) {
        return this.hitBox.intersect(g);
    }

    public int getDistanceSquare(Bomb b) {
        double deltaX = x - b.getX();
        double deltaY = y - b.getY();
        return (int)(Math.pow(deltaX,2)+Math.pow(deltaY,2));
    }
    public void bounceOff(Ghost g) {
        int deltaY = this.y - g.getY();
        int deltaX = this.x - g.getX();
        if(deltaY!=0){
            this.y = this.y + deltaY;
        }
        if(deltaX!=0){
            this.x = this.x + deltaX;
        }
        this.hitBox = new Rect(this.x-40,this.y-40,this.x+40,this.y+40);
    }
    public void bounceOff(Human h) {
        int deltaY = this.y - h.getY();
        int deltaX = this.x - h.getX();
        if(deltaY!=0){
            this.y = this.y + deltaY;
        }
        if(deltaX!=0){
            this.x = this.x + deltaX;
        }
        this.hitBox = new Rect(this.x-40,this.y-40,this.x+40,this.y+40);
    }
    public Bitmap[] loadGhosts(Context context) {
        Bitmap[] ghost = new Bitmap[2];
        ghost[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ghastly1),80,80,false);
        ghost[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ghastly2),80,80,false);
        return ghost;
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x-30, y-30, null);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX(){
        return x;
    }

    public void setX (int x) {
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY (int y) {
        this.y = y;
    }

    public Rect getHitBox () {
        return this.hitBox;
    }
}
