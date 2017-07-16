package com.example.sehajgulati08.todofinal;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by sehaj.gulati08 on 07-07-2017.
 */

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
