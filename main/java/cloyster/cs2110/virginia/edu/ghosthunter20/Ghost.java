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
public class Ghost {
    private Bitmap[] bitmaps;
    private Bitmap bitmap;
    private int x;
    private int y;
    private Rect hitBox;

    public Ghost(Context context, int x, int y){
        this.bitmaps = loadGhosts(context);
        this.bitmap = bitmaps[0];
        double ran = Math.random();
        this.x = x;
        this.y = y;
        this.hitBox = new Rect(this.x-15,this.y-25,this.x+15,this.y+25);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void move(Human human) {

        if(Math.abs(human.getX() - this.x) - Math.abs(human.getY() - this.y) > 1) {
            if (human.getX() - this.x > 1) {
                this.bitmap = this.bitmaps[3];
            } else {
                this.bitmap = this.bitmaps[2];
            }
        } else {
            if (human.getY() - this.y > 1) {
                this.bitmap = this.bitmaps[1];
            } else {
                this.bitmap = this.bitmaps[4];
            }
        }

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

        this.hitBox = new Rect(this.x-15,this.y-25,this.x+15,this.y+25);
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
        this.hitBox = new Rect(this.x-15,this.y-25,this.x+15,this.y+25);
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
        this.hitBox = new Rect(this.x-15,this.y-25,this.x+15,this.y+25);
    }
    public Bitmap[] loadGhosts(Context context) {
        Bitmap[] women = new Bitmap[5];
        women[0] = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet3),20,83,30,50);//stay
        women[1] = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet3),60,83,30,50);//face forward
        women[2] = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet3),65,150,30,50);//face left
        women[3] = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet4),65,150,30,50);//face right
        women[4] = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet3),65,270,30,50);//face backward
        return women;
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
