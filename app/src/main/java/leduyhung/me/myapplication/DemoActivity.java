package leduyhung.me.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import leduyhung.view.myspinner.MySpinnerView;
import leduyhung.view.myspinner.SpinnerClickListener;

public class DemoActivity extends AppCompatActivity {

    private MySpinnerView spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        spinner = findViewById(R.id.spinner);
        ArrayList a = new ArrayList();
        a.add("1");
        a.add("2");
        a.add("3");
        a.add("4");
        a.add("5");
        a.add("6");
        a.add("7");
        a.add("8");
        a.add("9");
        a.add("10");
        a.add("11");
        spinner.setData(a);
        spinner.setCurrentItem(4);

        spinner.setCurrentItem(0);
        spinner.setSpinnerClickListener(new SpinnerClickListener() {
            @Override
            public void onPreviousClick(int index) {

            }

            @Override
            public void onNextClick(int index) {

            }
        });
        spinner.getData();
        spinner.getTotalItem();
        spinner.nextData();
        spinner.previousData();
        spinner.getCurrentItem();
        spinner.run();
    }
}
