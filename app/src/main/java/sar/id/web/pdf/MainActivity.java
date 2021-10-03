package sar.id.web.pdf;

import android.app.Activity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import android.widget.LinearLayout;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.widget.Button;
import android.view.View;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DialogFragment;
import android.print.PrintJob;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.print.PrintAttributes;

public class MainActivity extends Activity {
	
	private  PrintJob printJob;
	private String webName = "";
	private String weblink = "";
	private  PrintManager printManager;
	private String removedHead = "";
	
	private LinearLayout linear1;
	private WebView webView;
	private Button button1;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		button1 = (Button) findViewById(R.id.button1);
		
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				
				super.onPageFinished(_param1, _param2);
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					_PrintTheWebPage(webView);
				} else {
					android.util.Log.e("MainActivity","Error in printing webpage, possibly due to lower android version");
				}
			}
		});
	}
	
	private void initializeLogic() {
		webView.loadUrl("https://www.google.com");
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
		}
		else {
			webView.clearCache(true);
			webView.clearHistory();
			finishAffinity();
		}
	}
	
	public void _PrintTheWebPage (final WebView _webView) {
		
		/*for getting the link of current website*/
		weblink = _webView.getUrl();
		
		/*for removing the https/http tags*/
		if (weblink.contains("https://")) {
			removedHead = weblink.replace("https://", " ");
		}
		else {
			if (weblink.contains("http://")) {
				removedHead = weblink.replace("http://", " ");
			}
		}
		
		/*for setting the name of the file*/
		webName = "web2pdf".concat("-".concat(removedHead));
		
		/*the actual code for doing the magic*/
		printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
		
		PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(webName);
		
		assert printManager != null;
		
		printJob = printManager.print(webName,printAdapter, new PrintAttributes.Builder().build());
		
	}
	
}