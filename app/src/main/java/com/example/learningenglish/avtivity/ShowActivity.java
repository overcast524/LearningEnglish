package com.example.learningenglish.avtivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningenglish.R;
import com.example.learningenglish.adapter.ShowWordAdapter;
import com.example.learningenglish.avtivity.review.GameActivity;
import com.example.learningenglish.avtivity.review.MatchActivity;
import com.example.learningenglish.avtivity.review.SpeedActivity;
import com.example.learningenglish.database.Interpretation;
import com.example.learningenglish.database.Word;
import com.example.learningenglish.entity.ItemMatch;
import com.example.learningenglish.entity.ItemShow;
import com.example.learningenglish.until.ActivityCollector;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private List<ItemShow> showList = new ArrayList<>();

    private List<Word> wordList = new ArrayList<>();

    public final String SHOW_TYPE = "showType";

    public final int TYPE_MATCH = 1;

    public final int TYPE_SPEED = 2;

    public final int TYPE_GAME = 3;

    private final int FINISH = 0;

    private ShowWordAdapter showWordAdapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case FINISH:
                    showWordAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private static final String TAG = "ShowActivity";
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        init();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        showWordAdapter = new ShowWordAdapter(showList);
        recyclerView.setAdapter(showWordAdapter);

        //showProgressDialog();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowActivity.this,MainActivity.class));
                finish();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                searchWord();
                bindData();
                Message message = new Message();
                message.what = FINISH;
                handler.sendMessage(message);
            }
        }).start();

    }

    private void init() {
        recyclerView = findViewById(R.id.recycler_show);
        image= (ImageView)findViewById(R.id.iv_home);
    }

    public void searchWord() {
        wordList.clear();
        int currentType = getIntent().getIntExtra(SHOW_TYPE, 0);
        Log.d(TAG, "currentType: " + currentType);
        Log.d(TAG, "searchWord: " + MatchActivity.allMatches.size());
        switch (currentType) {
            case TYPE_MATCH:
                Log.d(TAG, "searchWord: ");
                for (ItemMatch match : MatchActivity.allMatches) {
                    List<Word> words = LitePal.where("wordId = ?", match.getId() + "").select("wordId", "word").find(Word.class);
                    wordList.add(words.get(0));
                }
                break;
            case TYPE_SPEED:
                wordList = (ArrayList<Word>) SpeedActivity.wordList.clone();
                break;
            case TYPE_GAME:
                for (Integer integer : GameActivity.alreadyWords) {
                    List<Word> words = LitePal.where("wordId = ?", integer + "").find(Word.class);
                    wordList.add(words.get(0));
                }
                GameActivity.alreadyWords.clear();
        }
    }

    private void bindData() {
        showList.clear();
        for (Word word : wordList) {
            List<Interpretation> interpretations = LitePal.where("wordId = ?", word.getWordId() + "").find(Interpretation.class);
            StringBuilder stringBuilder = new StringBuilder();
            for (Interpretation interpretation : interpretations) {
                stringBuilder.append(interpretation.getWordType() + ". " + interpretation.getCHSMeaning() + " ");
            }
            if (word.getIsCollected() == 1)
                showList.add(new ItemShow(word.getWordId(), word.getWord(), stringBuilder.toString(), true));
            else
                showList.add(new ItemShow(word.getWordId(), word.getWord(), stringBuilder.toString(), false));
        }
    }

    @Override
    public void onBackPressed() {
        ActivityCollector.startOtherActivity(ShowActivity.this,MainActivity.class);
        finish();
    }
}
