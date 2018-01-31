/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package co.triadicsoftware.bluetoothcontrollertesting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    TextView mBtn_text;
    TextView mDir_text;
    TextView mJoyx_text;
    TextView mJoyy_text;
    Dpad mDpad = new Dpad();
    Joystick mJstick = new Joystick();
    WebView mWebview;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn_text = (TextView) findViewById(R.id.button_text_view);
        mBtn_text.setText("");

        mDir_text = (TextView) findViewById(R.id.direction_text_view);
        mDir_text.setText("");

        mJoyx_text = (TextView) findViewById(R.id.joystick_x_text_view);
        mJoyx_text.setText("");

        mJoyy_text = (TextView) findViewById(R.id.joystick_y_text_view);
        mJoyy_text.setText("");

        mWebview = (WebView) findViewById(R.id.webview);
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
                        mBtn_text.setText("A");
                        //handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_B:
                        mBtn_text.setText("B");
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_X:
                        mBtn_text.setText("X");
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_Y:
                        mBtn_text.setText("Y");
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_R1:
                        mBtn_text.setText("R1");
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_L1:
                        mBtn_text.setText("L1");
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_THUMBL:
                        mBtn_text.setText("Left Thumb");
                        handled = true;
                        break;
                    case KeyEvent.KEYCODE_BUTTON_THUMBR:
                        mBtn_text.setText("Right Thumb");
                        handled = true;
                        break;
                    default:
                        mBtn_text.setText("Other Button");
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
                    mDir_text.setText("Left");
                    handled = true;
                    break;
                case Dpad.RIGHT:
                    mDir_text.setText("Right");
                    handled = true;
                    break;
                case Dpad.UP:
                    mDir_text.setText("Up");
                    handled = true;
                    break;
                case Dpad.DOWN:
                    mDir_text.setText("Down");
                    //handled = true;
                    break;
            }
        }
        if(Joystick.isJoystickDevice(event)){
            int[] press = mJstick.processJoystickInputAlt(event);
            switch (press[0]){
                case Joystick.LEFT:
                    mJoyx_text.setText("Left");
                    handled = true;
                    break;
                case Joystick.RIGHT:
                    mJoyx_text.setText("Right");
                    handled = true;
                    break;
                default:
                    mJoyx_text.setText("Centered");
                    break;
            }
            switch (press[1]){
                case Joystick.UP:
                    mJoyy_text.setText("Up");
                    handled = true;
                    break;
                case Joystick.DOWN:
                    mJoyy_text.setText("Down");
                    //handled = true;
                    break;
                default:
                    mJoyy_text.setText("Centered");
                    break;
            }
        }
        if (handled){
            return true;
        }
        return super.onGenericMotionEvent(event);
    }

    public void startWeb1Activity(View view){
        Intent intent = new Intent(this, Web1Activity.class);
        startActivity(intent);
    }

    public void startWeb2Activity(View view){
        Intent intent = new Intent(this, Web2Activity.class);
        startActivity(intent);
    }

}
