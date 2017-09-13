package br.ufpe.cin.if710.managers.sensor;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import br.ufpe.cin.if710.managers.R;

public class GestureDetectorActivity extends AppCompatActivity implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener {

    private TextView tvGesture;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_detector);
        tvGesture = (TextView) findViewById(R.id.tvGesture);

        //o primeiro this é pq GestureDetectorActivity é uma Activity (óbvio!)
        //o segundo é pq a gente precisa de um listener, no caso OnGestureListener
        gestureDetector = new GestureDetector(this,this);

        //esse this é pq esta Activity implementa OnDoubleTapListener
        gestureDetector.setOnDoubleTapListener(this);

        //vamos monitorar longpress
        gestureDetector.setIsLongpressEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private Spanned formatSingleMotionEvent(String event, MotionEvent me) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h2>");sb.append(event);sb.append("</h2>");

        sb.append("<p>");
        sb.append("<b>X:</b> ");sb.append(me.getX());
        sb.append("</p>");
        sb.append("<p>");
        sb.append("<b>Y:</b> ");sb.append(me.getY());
        sb.append("</p>");
        sb.append("<p>");
        sb.append("<b>Pressure:</b> ");sb.append(me.getPressure());
        sb.append("</p>");
        //me.get...

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(sb.toString(),Html.FROM_HTML_MODE_LEGACY);
        }
        else {
            return Html.fromHtml(sb.toString());
        }
    }

    private Spanned formatComplexMotionEvent(String event, MotionEvent me1, MotionEvent me2, String attr, float attrX, float attrY) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>");sb.append(event);sb.append("</h2>");

        sb.append("<p>");
        sb.append("<b>X:</b> ");sb.append(me1.getX());
        sb.append(" | ");
        sb.append("<b>Y:</b> ");sb.append(me1.getY());
        sb.append("</p>");
        sb.append("<p>");
        sb.append("<b>Pressure:</b> ");sb.append(me1.getPressure());
        sb.append("</p>");

        sb.append("<p>");
        sb.append("<b>X:</b> ");sb.append(me2.getX());
        sb.append(" | ");
        sb.append("<b>Y:</b> ");sb.append(me2.getY());
        sb.append("</p>");
        sb.append("<p>");
        sb.append("<b>Pressure:</b> ");sb.append(me2.getPressure());
        sb.append("</p>");

        sb.append("<h2>");sb.append(attr);sb.append("</h2>");
        sb.append("<p>");
        sb.append("<b>X:</b> ");sb.append(attrX);
        sb.append(" | ");
        sb.append("<b>Y:</b> ");sb.append(attrY);
        sb.append("</p>");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(sb.toString(),Html.FROM_HTML_MODE_LEGACY);
        }
        else {
            return Html.fromHtml(sb.toString());
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        tvGesture.setText(formatSingleMotionEvent("onSingleTapConfirmed", e));
        return false;
    }



    @Override
    public boolean onDoubleTap(MotionEvent e) {
        tvGesture.setText(formatSingleMotionEvent("onDoubleTap", e));
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        tvGesture.setText(formatSingleMotionEvent("onDoubleTapEvent", e));
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        tvGesture.setText(formatSingleMotionEvent("onDown", e));
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        tvGesture.setText(formatSingleMotionEvent("onShowPress", e));
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        tvGesture.setText(formatSingleMotionEvent("onSingleTapUp", e));
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        tvGesture.setText(formatComplexMotionEvent("onScroll",e1,e2,"Distance",distanceX,distanceY));
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        tvGesture.setText(formatSingleMotionEvent("onLongPress", e));
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        tvGesture.setText(formatComplexMotionEvent("onFling",e1,e2,"Velocity",velocityX,velocityY));
        return false;
    }
}