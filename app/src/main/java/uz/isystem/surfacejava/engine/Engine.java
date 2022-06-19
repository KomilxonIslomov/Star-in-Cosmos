package uz.isystem.surfacejava.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class Engine {
    private final Model model;
    private final Render render;
    private final SurfaceHolder.Callback callback;
    long time = System.nanoTime();
    private SurfaceHolder surfaceHolder;
    private volatile boolean sopped;
    Runnable threadRunnable = new Runnable() {
        @Override
        public void run() {
            while (!sopped) {
                Canvas canvas;
                if (surfaceHolder == null || (canvas = surfaceHolder.lockCanvas()) == null) {
                    synchronized (Engine.this) {
                        try {
                            Engine.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    long timeElapsed = System.nanoTime() - time;
                    time = System.nanoTime();
                    model.update(timeElapsed);
                    render.draw(canvas, model);
                    surfaceHolder.unlockCanvasAndPost(canvas);

                }
            }
        }
    };

    public Engine(SurfaceView surfaceView) {
        model = new Model();
        render = new Render();

        Thread engineThread = new Thread(threadRunnable, "EngineThread");
        engineThread.start();
        callback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                Engine.this.surfaceHolder = surfaceHolder;

                synchronized (Engine.this) {
                    Engine.this.notifyAll();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Engine.this.surfaceHolder = surfaceHolder;
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                Engine.this.surfaceHolder = null;
            }
        };
        surfaceView.getHolder().addCallback(callback);

    }

    public void stop() {
        this.sopped = true;
    }
}
