package mp.kafa.mycollege;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class ViewInternalMark extends Activity
{

    AppSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_internal_mark);
        getActionBar().hide();
        settings=new AppSettings(getApplicationContext());
        WebView internalmark=(WebView)findViewById(R.id.internalmark);
        internalmark.loadUrl("http://leomessi10.esy.es/displaymark.php?studid="+settings.retriveSettings("id"));
    }

    }

