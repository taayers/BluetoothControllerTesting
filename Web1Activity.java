package co.triadicsoftware.bluetoothcontrollertesting;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by Todd on 8/9/17.
 */

public class Web1Activity extends Activity{

    WebView mWebview;
    Joystick mJstick = new Joystick();
    Dpad mDpad = new Dpad();
    String toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebview = (WebView)findViewById(R.id.webview);
        mWebview.setWebChromeClient(new WebChromeClient());
        mWebview.setWebViewClient(new MyWebViewClient());
        mWebview.loadUrl("http://testurl.com");

        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled = false;
        if ((event.getSource() & InputDevice.SOURCE_GAMEPAD)
                == InputDevice.SOURCE_GAMEPAD) {
            if (event.getRepeatCount() == 0 && event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (event.getKeyCode()) {
                    // Handle gamepad and D-pad button presses to
                    // navigate the ship
                    case KeyEvent.KEYCODE_BUTTON_A:
                        toast = "clickZoom();";
                        mWebview.evaluateJavascript(toast,null);
                        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_B:
                        toast = "clickPan();";
                        mWebview.evaluateJavascript(toast, null);
                        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_X:
                        toast = "clickStack();";
                        mWebview.evaluateJavascript(toast, null);
                        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_Y:
                        toast = "clickWindow();";
                        mWebview.evaluateJavascript(toast, null);
                        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_R1:
                        toast = "nextCanvas();";
                        mWebview.evaluateJavascript("nextCanvas();", null);
                        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_L1:
                        mWebview.evaluateJavascript("MoveMouseX(1);", null);
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

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        boolean handled = false;
        if(Dpad.isDpadDevice(event)) {
            int press = mDpad.getDirectionPressed(event);
            switch (press) {
                case Dpad.LEFT:
                    toast = "MoveMouseX(1);";
                    mWebview.evaluateJavascript(toast, null);
                    Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
                    handled = true;
                    break;
                case Dpad.RIGHT:
                    toast = "MoveMouseX(-1);";
                    mWebview.evaluateJavascript(toast, null);
                    Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
                    handled = true;
                    break;
                case Dpad.UP:
                    toast = "MoveMouseY(-1);";
                    mWebview.evaluateJavascript(toast, null);
                    Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
                    handled = true;
                    break;
                case Dpad.DOWN:
                    toast = "MoveMouseY(1);";
                    mWebview.evaluateJavascript(toast, null);
                    Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
                    handled = true;
                    break;
            }
        }
        if(Joystick.isJoystickDevice(event)){
            int[] press = mJstick.processJoystickInputAlt(event);
            switch (press[0]){
                case Joystick.LEFT:
                    handled = true;
                    break;
                case Joystick.RIGHT:
                    handled = true;
                    break;
                default:
                    break;
            }
            switch (press[1]){
                case Joystick.UP:
                    handled = true;
                    break;
                case Joystick.DOWN:
                    handled = true;
                    break;
                default:
                    break;
            }
        }
        if (handled){
            return true;
        }
        return super.onGenericMotionEvent(event);
    }

    private void startHomeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("www.testurl.com")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

}
