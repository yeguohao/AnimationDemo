package com.ygh.animationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Anim anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (anim != null) {
                    anim.cancel();
                }
                anim = Anim.rangeInt(0, 300);
                anim.duration(1000)
                        .listener(new Anim.Listener() {
                            @Override
                            public void update(int val) {
                                ((ViewGroup) v.getParent()).scrollTo(0, -val);
                            }

                            @Override
                            public void end() {
                                int scrollY = ((ViewGroup) v.getParent()).getScrollY();
                                Log.d(TAG, "end: " + scrollY);
                            }
                        })
                        .start();
            }
        });
    }

}
