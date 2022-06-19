package uz.isystem.surfacejava;

import android.os.Bundle;
import android.view.SurfaceView;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import uz.isystem.surfacejava.app.App;
import uz.isystem.surfacejava.engine.Engine;

public class MainActivity extends AppCompatActivity {
    SurfaceView surfaceView;
    Engine engine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        surfaceView = findViewById(R.id.surface);
        engine = new Engine(surfaceView);
//        App.startMedia();
    }

    @Override
    public void onBackPressed() {
        engine.stop();
        super.onBackPressed();
    }


    @Override
    protected void onStop() {
        App.stopMedia();
        super.onStop();

    }

    @Override
    protected void onResume() {
        App.startMedia();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
//        App.stopMedia();
        super.onDestroy();
        engine.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}