package com.frosquivel.magicalcameraapp.Activities.Menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.frosquivel.magicalcameraapp.R;
import com.mukesh.MarkdownView;

/**
 * Created by Fabian on 06/05/2017.
 */
public class MarkDownReadMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown_readme);

        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdown_view);
        markdownView.loadMarkdownFromAssets("README.md"); //Loads the markdown file from the assets folder

    }
}
