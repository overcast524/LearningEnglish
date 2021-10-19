package com.example.learningenglish.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.learningenglish.R;
import com.example.learningenglish.adapter.WordFolderAdapter;
import com.example.learningenglish.database.FolderLinkWord;
import com.example.learningenglish.database.WordFolder;
import com.example.learningenglish.entity.ItemWordFolder;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class WordFolderActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private List<ItemWordFolder> wordFolderList = new ArrayList<>();

    private ImageView imgAdd,image;

    private WordFolderAdapter wordFolderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_folder);

        windowExplode();

        init();

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        wordFolderAdapter = new WordFolderAdapter(wordFolderList);
        recyclerView.setAdapter(wordFolderAdapter);

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordFolderActivity.this, AddFolderActivity.class);
                startActivity(intent);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void init() {
        recyclerView = findViewById(R.id.recycler_wf);
        imgAdd = findViewById(R.id.img_wf_add);
        image =  findViewById(R.id.iv_back);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<WordFolder> wordFolders = LitePal.findAll(WordFolder.class);
        if (!wordFolders.isEmpty()) {
            wordFolderList.clear();
            for (WordFolder w : wordFolders) {
                List<FolderLinkWord> folderLinkWords = LitePal.where("folderId = ?", w.getId() + "").find(FolderLinkWord.class);
                wordFolderList.add(new ItemWordFolder(w.getId(), folderLinkWords.size(), w.getName(), w.getRemark()));
            }
            wordFolderAdapter.notifyDataSetChanged();
        }
    }
}
