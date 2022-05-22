package com.example.newnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.example.newnews.Models.NewsApiResponse;
import com.example.newnews.Models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements  SelectListener, View.OnClickListener{
    RecyclerView recyclerView; // 리사이클 뷰 변수
    CustomAdapter adapter; // 커스텀 어댑터(자체 제작) 변수
    ProgressDialog dialog; // 로딩 다이얼로그 변수
    Button b1,b2,b3,b4,b5,b6,b7; // 뉴스 테마를 위한 버튼 변수


    // 메인 메소드
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("L3のニュース"); // 로딩 다이얼로그 타이틀
        dialog.show(); // 다이얼로그 가시화

        // 각 버튼의 [아이디]를 읽어와서 클릭리스너를 대응해준다.
        b1 = findViewById(R.id.btn_1);
        b1.setOnClickListener(this); // 클릭 리스너는 this(터치 반응)에 대응한다.
        b2 = findViewById(R.id.btn_2);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn_3);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn_4);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn_5);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn_6);
        b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn_7);
        b7.setOnClickListener(this);

        // 리퀘스트(요구) 관리자 인스턴스 선언
        RequestManager manager = new RequestManager(this);
        // 관리자 변수가 getNewsHeadlines 메소드를 이용하여, (리스너, 카테고리, query)를 따온다.
        manager.getNewsHeadlines(listener, "general", null);
    }

    //데이터를 가져오는 final 변수 listener
    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if(list.isEmpty()){
                Toast.makeText(MainActivity.this, "資料がないです！", Toast.LENGTH_SHORT).show();
            }
            else{
                showNews(list);
                dialog.dismiss();
            }
        }// onFetchData 를 통해서 데이터를 가지고 오고(from List<NewsHeadlines> 와 message),
        //없으면 자료 없음을 표시하고, 있으면 리스트를 showNews 메소드를 이용해 표시하고, 진행 dialog 를 삭제

        @Override
        public void onError(String message) {

        }// 에러일 경우를 선언
    };

    // 뉴스를 보여주는 클래스
    private void showNews(List<NewsHeadlines> list) {
         recyclerView = findViewById(R.id.recycler_main);
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
         adapter = new CustomAdapter(this, list, this);
         recyclerView.setAdapter(adapter); // recyclerView 에 Adapter 를 이식함

    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class)
                .putExtra("data", headlines));
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        dialog.setTitle("ローディング中です");
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, category, null);
    }
}
