package com.example.learningenglish.avtivity.review;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.learningenglish.R;
import com.example.learningenglish.adapter.MatchAdapter;
import com.example.learningenglish.avtivity.BaseActivity;
import com.example.learningenglish.database.Word;
import com.example.learningenglish.entity.ItemMatch;

import java.util.ArrayList;
import java.util.List;

public class MatchActivity extends BaseActivity {

    public static List<Word> wordList = new ArrayList<>();

    public static ArrayList<ItemMatch> matchList = new ArrayList<>();

    public static ArrayList<ItemMatch> allMatches = new ArrayList<>();

    private RecyclerView recyclerView;

    private static final String TAG = "MatchActivity";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        init();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        windowExplode();

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        MatchAdapter matchAdapter = new MatchAdapter(matchList);
        recyclerView.setAdapter(matchAdapter);
    }


    private void init() {
        recyclerView = findViewById(R.id.recycler_mt);
        imageView =  findViewById(R.id.iv_back);
    }

}