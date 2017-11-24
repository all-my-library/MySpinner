# COMPILE
<pre>
compile 'leduyhung.my.view:my-spinner:0.0.1'
</pre>
# HOW TO USE
<h3>MySpinnerView</h3>
<img src="https://github.com/all-my-library/MySpinner/blob/master/art/my-spinner.gif"></a>
<p><b>1: Declare in xml</b></p>
<pre> leduyhung.view.myspinner.MySpinnerView
        android:id="@+id/spinner"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:layout_marginLeft="60dp"
        android:layout_marginRight="60dp"
        custom:my_spinner_arrow_color="#FFFFFF"
        custom:my_spinner_background_color="#005005"
        custom:my_spinner_radius="10"
        custom:my_spinner_padding_arrow="20dp"
        custom:my_spinner_arrow_size="20dp"
        custom:my_spinner_border_arrow_size="2dp"
        custom:my_spinner_value_color="#ffffff"
        custom:my_spinner_value_size="16sp"
</pre>
<br/>
<p><b>2: Init data</b></p>
<pre>
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
</pre>
<br/>
<p><b>3: METHOD</b></p>
<pre>
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
</pre>
