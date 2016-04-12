package cloyster.cs2110.virginia.edu.ghosthunter20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.Editable;
import android.util.Log;

/**
 * T102-06
 * Bowen Ke(bk7bb), Tianyang He(th4xa), Haoyang Li(hl3fm)
 */
public class Human {
    private Bitmap bitmap;
    private Bitmap[] bitmaps;
    private int x;
    private int y;
    private Rect hitBox;
    private int HP;

    public Human(Context context, int x, int y) {
        this.bitmaps = loadSprite(context);
        this.bitmap = bitmaps[0];
        this.x = x;
        this.y = y;
        this.hitBox = new Rect(this.x-15,this.y-15,this.x-25,this.y-25);
        this.HP = 3;
    }

    public void move(int xD, int yD){
        if(Math.abs(xD - this.x) - Math.abs(yD - this.y) > 1) {
            if (xD - this.x > 1) {
                this.bitmap = this.bitmaps[3];
            } else {
                this.bitmap = this.bitmaps[2];
            }
        } else {
            if (yD - this.y > 1) {
                this.bitmap = this.bitmaps[1];
            } else {
                this.bitmap = this.bitmaps[4];
            }
        }

        if (xD > this.x) {
            this.x = this.x + 5;
        } else if (xD == this.x) {
            this.x = this.x;
        } else {
            this.x = this.x - 5;
        }
        if (yD > this.y) {
            this.y = this.y + 5;
        } else if (yD == this.y) {
            this.y = this.y;
        } else {
            this.y = this.y - 5;
        }

        this.hitBox = new Rect(this.x-15,this.y-25,this.x+15,this.y+25);
    }
    public static Bitmap[] loadSprite(Context context) {
        Bitmap[] sprites = new Bitmap[5];
        sprites[0] = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet),5,5,30,50);//stay
        sprites[1] = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet),90,82,30,50);//face forward
        sprites[2] = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet),45,210,30,50);//face left
        sprites[3] = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet2),56,76,30,50);//face right
        sprites[4] = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet),86,338,30,50);//face backward
        return sprites;
    }

    public void draw (Canvas canvas) {
        canvas.drawBitmap(bitmap, x-30, y-30, null);
    }

    public Bitmap getBitmap () {
        return bitmap;
    }

    public void setBitmap (Bitmap bitmap) {
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

    public int getHP () {
        return HP;
    }

    public void setHP (int HP) {
        this.HP = HP;
    }
}

