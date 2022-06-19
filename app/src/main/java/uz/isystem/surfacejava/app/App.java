package uz.isystem.surfacejava.app;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

import uz.isystem.surfacejava.R;

public class App extends Application {
    private static MediaPlayer mediaPlayer;
    private static Context appContext;
    private static int LastMediaPoint = 0;

    public static void startMedia() {
        mediaPlayer = MediaPlayer.create(appContext, R.raw.piano);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopMedia();
            }
        });
        if (LastMediaPoint <= mediaPlayer.getDuration() - 2_000) {
            mediaPlayer.seekTo(LastMediaPoint);
        }
        mediaPlayer.setLooping(true);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public static void stopMedia() {
        if (mediaPlayer != null) {
            LastMediaPoint = mediaPlayer.getCurrentPosition();
            mediaPlayer.release();
        }

    }

    @Override
    public void onCreate() {
        appContext = this;
        super.onCreate();
//       startMedia();

    }

    @Override
    public void onTerminate() {
        startMedia();
        mediaPlayer = null;
        super.onTerminate();

    }

}
