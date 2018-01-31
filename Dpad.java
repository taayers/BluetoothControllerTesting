package co.triadicsoftware.bluetoothcontrollertesting;

import android.util.Log;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by Todd on 7/31/17.
 */

public class Dpad {
    final static int UP       = 0;
    final static int LEFT     = 1;
    final static int RIGHT    = 2;
    final static int DOWN     = 3;
    final static int CENTER   = 4;

    int directionPressed = -1;

    public int getDirectionPressed(InputEvent event) {
        if (!isDpadDevice(event)) {
            return -1;
        }

        if (event instanceof MotionEvent) {
            MotionEvent motionEvent = (MotionEvent) event;
            float xaxis = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_X);
            float yaxis = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_Y);

            if (Float.compare(xaxis, -1.0f) == 0) {
                directionPressed =  Dpad.LEFT;
            } else if (Float.compare(xaxis, 1.0f) == 0) {
                directionPressed =  Dpad.RIGHT;
            }

            else if (Float.compare(yaxis, -1.0f) == 0) {
                directionPressed =  Dpad.UP;
            } else if (Float.compare(yaxis, 1.0f) == 0) {
                directionPressed =  Dpad.DOWN;
            }
        }

        else if (event instanceof KeyEvent) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                directionPressed = Dpad.LEFT;
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                directionPressed = Dpad.RIGHT;
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                directionPressed = Dpad.UP;
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                directionPressed = Dpad.DOWN;
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                directionPressed = Dpad.CENTER;
            }
        }
        return directionPressed;
    }

    public static boolean isDpadDevice(InputEvent event) {
        if ((event.getSource() & InputDevice.SOURCE_DPAD)
                != InputDevice.SOURCE_DPAD) {
            return true;
        } else {
            return false;
        }
    }
}
