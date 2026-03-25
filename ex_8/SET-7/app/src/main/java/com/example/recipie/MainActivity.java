package com.example.recipie;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String[][] recipes = {
        {"Italian", "Pasta Carbonara", "Margherita Pizza", "Risotto", "https://www.youtube.com/embed/D_2DBLAt57c"},
        {"Chinese", "Fried Rice", "Kung Pao Chicken", "Dumplings", "https://www.youtube.com/embed/eY1FF6SEggk"},
        {"Indian", "Butter Chicken", "Biryani", "Paneer Tikka", "https://www.youtube.com/embed/a03U45jFxOI"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinnerCuisine);
        LinearLayout container = findViewById(R.id.recipeContainer);
        WebView webView = findViewById(R.id.webVideo);

        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true); // Required for some YouTube videos
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        String[] cuisines = {"-- Select Cuisine --", "Italian", "Chinese", "Indian"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cuisines));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                container.removeAllViews();
                webView.setVisibility(View.GONE);
                if (pos == 0) return;

                String[] r = recipes[pos - 1];
                for (int i = 1; i <= 3; i++) {
                    TextView tv = new TextView(MainActivity.this);
                    tv.setText("• " + r[i]);
                    tv.setTextSize(18);
                    tv.setTextColor(0xFF333333); // Dark Gray
                    tv.setPadding(30, 20, 30, 20);
                    tv.setBackgroundColor(0xFFFFFFFF); // White background for items
                    
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, 0, 0, 16);
                    tv.setLayoutParams(lp);
                    container.addView(tv);
                }

                webView.setVisibility(View.VISIBLE);
                webView.loadUrl(r[4]); // directly load URL
            }
            public void onNothingSelected(AdapterView<?> p) {}
        });
    }
}