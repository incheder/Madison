package com.wezen.madison.history;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wezen.madison.R;
import com.wezen.madison.model.HistoryService;
import com.wezen.madison.model.HomeServiceStatus;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar)findViewById(R.id.historyToolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        RecyclerView rvHistory = (RecyclerView) findViewById(R.id.rvHistory);
        rvHistory.setHasFixedSize(true);
        HistoryAdapter adapter = new HistoryAdapter(getDummyList());
        rvHistory.setAdapter(adapter);

    }

    private List<HistoryService> getDummyList() {
        List<HistoryService> list = new ArrayList<>();
        HistoryService hs;
        for (int i = 0; i < 10 ; i++) {
           hs = new HistoryService();
           hs.setDate("15/09/2015");
           hs.setDescription("Such a great service, keep it going guys!!");
           hs.setImage(null);
           hs.setName("Doctor Solucion");
           hs.setReview(3);
           hs.setStatus(HomeServiceStatus.COMPLETO);
           list.add(hs);
        }
        return list;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
