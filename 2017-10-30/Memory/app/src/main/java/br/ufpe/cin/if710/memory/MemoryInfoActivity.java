package br.ufpe.cin.if710.memory;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.widget.TextView;

public class MemoryInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_info);
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        TextView availableMemory = (TextView) findViewById(R.id.availableMemory);
        TextView percentAvailable = (TextView) findViewById(R.id.percentAvailable);
        TextView lowMemory = (TextView) findViewById(R.id.lowMemory);
        TextView threshold = (TextView) findViewById(R.id.threshold);
        TextView totalMemory = (TextView) findViewById(R.id.totalMemory);
        TextView etc = (TextView) findViewById(R.id.etc);

        StringBuilder sb = new StringBuilder();

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        double tmp = memoryInfo.availMem / 0x100000L;
        sb.append(tmp);
        sb.append(" MB");
        availableMemory.setText(sb.toString());

        tmp = memoryInfo.availMem / (double) memoryInfo.totalMem * 100.0;
        sb.setLength(0);
        sb.append(tmp);
        sb.append(" %");
        percentAvailable.setText(sb.toString());

        if (memoryInfo.lowMemory) {
            lowMemory.setText("Yes");
        }
        else {
            lowMemory.setText("No");
        }

        tmp = memoryInfo.threshold / 0x100000L;
        sb.setLength(0);
        sb.append(tmp);
        sb.append(" MB");
        threshold.setText(sb.toString());

        tmp = memoryInfo.totalMem / 0x100000L;
        sb.setLength(0);
        sb.append(tmp);
        sb.append(" MB");
        totalMemory.setText(sb.toString());

        ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
        activityManager.getMyMemoryState(runningAppProcessInfo);

        sb.setLength(0);
        sb.append("PID: ");
        sb.append(runningAppProcessInfo.pid);
        sb.append("\n");
        sb.append("Importance: ");
        sb.append(runningAppProcessInfo.importance);
        sb.append("\n");
        sb.append("Last Trim Level: ");
        sb.append(runningAppProcessInfo.lastTrimLevel);
        sb.append("\n");
        sb.append("Importance Reason Code: ");
        sb.append(runningAppProcessInfo.importanceReasonCode);
        sb.append("\n");
        sb.append("LRU: ");
        sb.append(runningAppProcessInfo.lru);
        sb.append("\n");

        etc.setText(sb.toString());





    }
}
