package budiluhur.ac.id.uas;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView[] image;
    private ConstraintLayout layout;
    private int t;
    private float a, b, c, d = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.layout);
        image = new ImageView[]{
                findViewById(R.id.tokopedia),
                findViewById(R.id.bukalapak),
                findViewById(R.id.shopee)
        };

        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int[] x = new int[]{
                        (layout.getWidth() / 2) - pixel(MainActivity.this,38),
                        (layout.getWidth() / 17) * 10,
                        pixel(MainActivity.this,25)
                };
                int[] y = new int[]{
                        pixel(MainActivity.this,26),
                        (layout.getHeight() / 2) - pixel(MainActivity.this,35),
                        (layout.getHeight() / 2) - pixel(MainActivity.this,35)
                };
                Path[] path = new Path[]{
                        new Path(),
                        new Path(),
                        new Path()
                };
                path[0].moveTo(x[0], y[0]);
                path[1].moveTo(x[1], y[1]);
                path[2].moveTo(x[2], y[2]);
                int test = layout.getWidth()/4;
                for(int z=1; z<=test*4; z++) {
                    if (z>=test) {
                        if (z>=test*2 && z<test*3) {
                            --t; --b; c=t;
                            path[0].lineTo(x[0]+t, y[0]+b);
                        }
                        else if (z>=test*3) {
                            t++; c++; --b;
                            path[0].lineTo(x[0]+c, y[0]+b);
                        }
                        else {
                            --t; a++;
                            b=z;
                            path[0].lineTo(x[0]+t, y[0]+a);
                        }
                    }
                    else {
                        path[0].lineTo(x[0]+z, y[0]+z);
                        path[1].lineTo(x[1]+d, y[1]+z);
                        path[2].lineTo(x[2]+z, y[2]);
                        t=z;
                        a=z;
                        --d;
                    }
                }

                ObjectAnimator objectAnimator;
                for(int i=0; i<3; i++) {
                    objectAnimator = ObjectAnimator.ofFloat(image[i], View.X, View.Y, path[i]);
                    objectAnimator.setDuration(2000).start();
                }

                Thread timer = new Thread(){
                    public void run(){
                        try {
                            sleep(3000);
                        }
                        catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        finally {
                            Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                };
                timer.start();
            }
        });
    }

    private int pixel(Context context, int dp) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int)((dp * dm.density) + 0.5);
    }
}
