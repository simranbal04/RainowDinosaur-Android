package com.example.tappyspaceship01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {

    // Android debug variables
    final static String TAG="DINO-RAINBOWS";

    // screen size
    int screenHeight;
    int screenWidth;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;



    // -----------------------------------
    // GAME SPECIFIC VARIABLES
    // -----------------------------------


    // ----------------------------
    // ## SPRITES
    // ----------------------------

    int playerXPosition; //here player is dinosaur
    int playerYPosition;
    Bitmap playerImage;
    Rect playerHitbox;

    Bitmap candyImage;
    Rect candyHitbox;
    Bitmap garbageImage;
    Rect garbageHitbox;
    Bitmap rainbowImage;
    Rect rainbowHitbox;

//    Dinosaurs dinosaurs;
//    Rainbows rainbows;
//    Candy candy;
//    Garbage garbage;
    // represent the TOP LEFT CORNER OF THE GRAPHIC

    // ----------------------------
    // ## GAME STATS
    // ----------------------------

    int score = 0;
    int lives = 3;


    public GameEngine(Context context, int w, int h) {
        super(context);

        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;

        this.printScreenInfo();

        // adding sprites
        this.spawnPlayer();
        this.spawnEnemyShips();
    }


//    @Override
//    public void onDraw(Canvas canvas) {
//        canvas.drawLine(0,0,20,20,paintbrush);
//        canvas.drawLine(20,0,0,20,paintbrush);
//    }

    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }

    private void spawnPlayer() {
        //@TODO: Start the player at the left side of screen
//        dinosaurs = new Dinosaurs(this.getContext(),100 ,100);
    }
    private void spawnEnemyShips() {
        Random random = new Random();

        //@TODO: Place the enemies in a random location

    }

    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }


    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------


    String directionBallIsMoving = "down";
    String personTapped="";

    public void updatePositions() {

    }

//    if(this.fingerAction == "mousedown") {
//        this.pla
//    }

    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------

            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255,255,255,255));
            paintbrush.setColor(Color.WHITE);


            //drawing the line
            this.canvas.drawLine(50,0,150,0,paintbrush);
            this.canvas.drawLine(50,50,150,50,paintbrush);
            this.canvas.drawLine(50,100,150,100,paintbrush);
            this.canvas.drawLine(50,150,150,150,paintbrush);

            // DRAW THE PLAYER HITBOX
            // ------------------------
            // 1. change the paintbrush settings so we can see the hitbox
            paintbrush.setColor(Color.BLUE);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(5);

            //----------------
            this.holder.unlockCanvasAndPost(canvas);

            //life label
            paintbrush.setTextSize(60);
            canvas.drawText("lives " + this.lives, 50, 50,paintbrush);

            //score label
            paintbrush.setTextSize(45);
            canvas.drawText("score" + this.score, 80,50,paintbrush);
        }
    }

    public void setFPS() {
        try {
            gameThread.sleep(120);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------


    String fingerAction = "";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {
            // user pushed down on screen

            // 1. Get position of tap
            float fingerXPosition = event.getX();
            float fingerYPosition = event.getY();
            Log.d(TAG, "Person's pressed: "
                    + fingerXPosition + ","
                    + fingerYPosition);


            // 2. Compare position of tap to middle of screen
            int middleOfScreen = this.screenWidth / 2;
            if (fingerXPosition <= middleOfScreen) {
                // 3. If tap is on upside of screen, player should go up
                personTapped = "up";
            }
            else if (fingerXPosition > middleOfScreen) {
                // 4. If tap is on down, player should go dowm
                personTapped = "down";
            }
        }


        else if (userAction == MotionEvent.ACTION_UP) {


        }

        return true;
    }
}
