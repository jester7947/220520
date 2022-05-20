package com.example.newnews;

import com.example.newnews.Models.NewsHeadlines;

import java.util.List;

interface OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<NewsHeadlines> list, String message);
    void onError(String message);

}
