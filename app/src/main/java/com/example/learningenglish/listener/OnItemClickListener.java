package com.example.learningenglish.listener;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.learningenglish.entity.ItemWordMeanChoice;

public interface OnItemClickListener {

    void onItemClick(RecyclerView parent, View view, int position, ItemWordMeanChoice itemWordMeanChoice);


}
