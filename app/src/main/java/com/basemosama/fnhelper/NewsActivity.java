package com.basemosama.fnhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basemosama.fnhelper.Constants.Constant;
import com.basemosama.fnhelper.objects.NewsObjects.NewsEntries;
import com.squareup.picasso.Picasso;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class NewsActivity extends AppCompatActivity {

    NewsEntries news;
    private ImageView newsImage;
    TextView newsDescription,newsDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsImage=findViewById(R.id.news_activity_image);
        newsDescription =findViewById(R.id.news_description);
        newsDate =findViewById(R.id.news_date);

        news=getIntent().getParcelableExtra(Constant.INTENT_news_KEY);
        setTitle(news.getTitle());
        Picasso.get()
                .load(news.getImage())
                .into(newsImage);

        newsDescription.setText(news.getBody());

        Date date=new Date(Long.valueOf(news.getTime())*1000);
        Format format = new SimpleDateFormat("HH:mm a - dd/MM/yyyy", Locale.US);
        String time= format.format(date);

        newsDate.setText(time);

    }
}
