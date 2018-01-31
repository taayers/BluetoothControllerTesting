package co.triadicsoftware.bluetoothcontrollertesting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Todd on 8/9/17.
 */

public class Web2Activity extends Activity {

    WebView mWebview;
    Joystick mJstick;
    Dpad mDpad;
    String toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);

        mWebview = (WebView)findViewById(R.id.webview);
        mWebview.setWebViewClient(new MyWebViewClient());
        mWebview.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.79 Safari/537.36");
        setDesktopMode(mWebview, true);
        mWebview.loadUrl("https://testurl.com");

    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled = false;
        if ((event.getSource() & InputDevice.SOURCE_GAMEPAD)
                == InputDevice.SOURCE_GAMEPAD) {
            if (event.getRepeatCount() == 0) {
                switch (event.getKeyCode()) {
                    // Handle gamepad and D-pad button presses to
                    // navigate the ship
                    case KeyEvent.KEYCODE_BUTTON_A:
                        mWebview.evaluateJavascript(toast, null);
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_B:
                        startHomeActivity();
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_X:

                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_Y:
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_R1:
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_L1:
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_THUMBL:
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_THUMBR:
                        handled = true;
                        break;
                    default:
                        handled = true;
                        break;
                }
            }
        }
        if (handled) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    public void setDesktopMode(WebView webView,boolean enabled) {
        String newUserAgent = webView.getSettings().getUserAgentString();
        if (enabled) {
            try {
                String ua = webView.getSettings().getUserAgentString();
                String androidOSString = webView.getSettings().getUserAgentString().substring(ua.indexOf("("), ua.indexOf(")") + 1);
                newUserAgent = webView.getSettings().getUserAgentString().replace(androidOSString, "(X11; Linux x86_64)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        webView.getSettings().setUserAgentString(newUserAgent);
        webView.reload();
    }

    private void startHomeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;
        }
    }
}
