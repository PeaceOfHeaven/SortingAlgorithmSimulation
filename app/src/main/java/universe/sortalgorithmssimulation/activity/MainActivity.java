package universe.sortalgorithmssimulation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import universe.sortalgorithmssimulation.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, TestActivity.class));
        finish();
    }

    public void go(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }
}
