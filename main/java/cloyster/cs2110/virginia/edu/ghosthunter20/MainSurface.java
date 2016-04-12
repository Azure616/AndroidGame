package cloyster.cs2110.virginia.edu.ghosthunter20;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * T102-06
 * Bowen Ke(bk7bb), Tianyang He(th4xa), Haoyang Li(hl3fm)
 */

public class MainSurface extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    private Human human;
    private int pX;
    private int pY;
    private ArrayList<Heart> hearts;
    private ArrayList<Ghost> ghosts;
    private ArrayList<Bomb> bombs;
    private Bitmap background;
    private int killCount;
    private int score;
    private static final String TAG = MainSurface.class.getSimpleName();

    public MainSurface (Context context) {
        super(context);
        getHolder().addCallback(this);
        //game loop thread
        thread = new MainThread(getHolder(),this);
        human = new Human(context,50,50);
        ghosts = new ArrayList<Ghost>();
        ghosts.add(new Ghost(context,500,500));
        ghosts.add(new Ghost(context,200,600));
        hearts = new ArrayList<Heart>();
        hearts.add(new Heart(context,300,300));
        bombs = new ArrayList<Bomb>();
        bombs.add(new Bomb(context, 700, 700));
        score = 0;
        killCount = 0;
        background = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.background1),0,0,1950,1200);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.join(); //Block current thread until its receiver finishes execution and dies
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getY() > getHeight() - 50) {
                thread.setRunning(false);
                ((Activity)getContext()).finish();
            } else {
                pX = (int) event.getX();
                pY = (int) event.getY();
            }
        }
        return super.onTouchEvent(event);
    }

    @SuppressLint("WrongCall")
    protected void OnDraw(Canvas canvas) {

        canvas.drawBitmap(background,0,0,null);
        human.draw(canvas);
        for(Ghost i:ghosts) {
            i.draw(canvas);
        }
        for(Heart h:hearts) {
            h.draw(canvas);
        }
        for(Bomb b:bombs){
            if (b.getGoOff()) {
                b.Boom();
            }
            b.draw(canvas);
            if (b.getGoOff()) {
                bombs.remove(b);
            }
        }
        Paint paint  = new Paint(Color.WHITE);
        paint.setTextSize(50);
        paint.setColor(Color.WHITE);
        //canvas.drawText("Your score:"+score,1200,120, paint);
        //Log.d("OnDraw()", "called");

    }

    private class MainThread extends Thread{

        //private static final String TAG = MainThread.class.getSimpleName();
        private boolean running;
        private SurfaceHolder surfaceHolder;
        private MainSurface mainSurface;

        public MainThread (SurfaceHolder surfaceHolder, MainSurface mainSurface) {
            super();
            this.surfaceHolder = surfaceHolder;
            this.mainSurface = mainSurface;

        }

        public void setRunning (boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            Canvas canvas = null;
            boolean goOff = false;
            long tickCount = 0L;
            Log.d(TAG, "Starting game loop");
            while (running) {
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        goOff = false;
                        tickCount++;
                        //add a ghost every 200 ticks if the size of ghosts is less than a threshold value


/////////////////////////////////////////////////////////////////////////////////////////

                        if (tickCount % 200 == 0 && ghosts.size() <= 7) {
                            int random = (int) (Math.random()*500);
                            if (Math.random() > 0.5) {
                                random = -1*random;
                            }
                            //Log.d("Random", "" + random);
                            ghosts.add(new Ghost(getContext(), human.getX() + random, human.getY() + random));
                        }
/////////////////////////////////////////////////////////////////////////////////////////

                        //The player's score will go up by one every 5s
                        if (tickCount % 250 == 0) {
                            score = score + 20;
                        }
                        //add a new heart every 1000 ticks
                        if (tickCount % 1000 == 0 && hearts.size() <= 5) {
                            int random = (int) (Math.random()*500);
                            if (Math.random() > 0.5) {
                                random = -1*random;
                            }
                            //Log.d("Random", "" + random);
                            hearts.add(new Heart(getContext(), human.getX() + random, human.getY() + random));
                        }
                        //add a new bomb every 1200 ticks
                        if (tickCount % 1200 == 0 && bombs.size() <= 3) {
                            int random = (int) (Math.random() * 500);
                            if (Math.random() > 0.5) {
                                random = -1 * random;
                            }
                            //Log.d("Random", "" + random);
                            bombs.add(new Bomb(getContext(), human.getX() + random, human.getY() + random));
                            }

                        //keep human moving towards the point player touches
                        if (Math.abs(human.getX()-pX) >= 1 && Math.abs(human.getY()-pY) >= 1) {
                            human.move(pX, pY);
                        }
                        //HP restores when collides with heart
                        if (human.getHP() <= 2) {
                            for (int i = 0;i < hearts.size();i++) {
                                Heart h = hearts.get(i);
                                if (h.collideWith(human.getHitBox())) {
                                    human.setHP(human.getHP() + 1);
                                    hearts.remove(h);
                                }
                            }
                        }
/////////////////////////////////////////////////////////////////////////////////////////
                         //if human touch the bomb,bomb goes off, killing ghosts nearby
                    for(int i = 0;i < bombs.size();i++) {
                        Bomb b = bombs.get(i);
                        if (b.collideWith(human.getHitBox())) {
                            //Log.d("bomb","human use bomb");
                            for (int j = 0; j < ghosts.size(); j++) {
                                Ghost g = ghosts.get(j);
                                //Log.d(j+"th ghost","checked");
                                if (g.getDistanceSquare(b) < 40000) {
                                    ghosts.remove(g);
                                    killCount++;
                                    //a dead ghost will add 100 score
                                    score = score + 100;
                                }
                            }
                            b.setGoOff(true);
                        }
                    }
/////////////////////////////////////////////////////////////////////////////////////////

                        //all ghost goes after human
                        for (int i = 0; i < ghosts.size(); i++) {
                            Ghost cur = ghosts.get(i);
                            cur.move(human);
                            if(Math.abs(cur.getX()-human.getX())<100 && Math.abs(cur.getY()-human.getY())<100) {


                            }
                            //lose HP if touches by ghost.When HP < 0, stop the game
                            if (cur.collideWith(human.getHitBox())) {
                                int HP = human.getHP();
                                if (HP > 0) {
                                    human.setHP(HP - 1);
                                    cur.bounceOff(human);
                                } else {
                                    running = false;
                                    Intent gameOver = new Intent("cloyster.cs2110.virginia.edu.ghosthunter20.GAMEOVER");
                                    mainSurface.getContext().startActivity(gameOver);
                                }
                            }
                            //for each ghost, bounce off each other when collide
                            for (int j = i + 1; j < ghosts.size() - 1; j++) {
                                Ghost g = ghosts.get(j);
                                if (g.collideWith(cur.getHitBox())) {
                                    g.bounceOff(cur);
                                }
                            }
                        }

                        // update game state
                        // draws the canvas on the panel
                        this.mainSurface.OnDraw(canvas);
                    }
                } finally {
                    // in case of an exception the surface is not left in
                    // an inconsistent state
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                } // end finally
            }
        }

    }
}
