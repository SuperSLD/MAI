package com.raspisanie.mai.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

import com.raspisanie.mai.Adapters.NewsAdapter;
import com.raspisanie.mai.Classes.NewsManager;
import com.raspisanie.mai.R;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        init();

        setTitle(R.string.activity_title_news);

        if (NewsManager.getInstance().getNewsObjects().size() > 0) {
            findViewById(R.id.warning).setVisibility(View.GONE);
        }

        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());

        NewsAdapter adapter = new NewsAdapter(NewsManager.getInstance().getNewsObjects());
        RecyclerView recyclerView = findViewById(R.id.newsList);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Инициализация.
     * @author Соляной Леонид (solyanoy.leonid@gmail.com)
     */
    private void init() {

    }

}
