package br.ufpe.cin.if710.managers.jobscheduler;

import android.Manifest;
import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import br.ufpe.cin.if710.managers.R;

public class JobSchedulerActivity extends AppCompatActivity {
    private static final int JOB_ID = 710;
    static final String KEY_DOWNLOAD="isDownload";

    private static final long[] PERIODS = {
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            AlarmManager.INTERVAL_HALF_HOUR,
            AlarmManager.INTERVAL_HOUR
    };

    private static final String[] STORAGE_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST = 710;

    public boolean podeEscrever() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    Button agendar, cancelar;
    Spinner period;
    JobScheduler jobScheduler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_scheduler);



        period = (Spinner) findViewById(R.id.period);
        ArrayAdapter<String> periods =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.periods));
        periods.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        period.setAdapter(periods);

        agendar = (Button) findViewById(R.id.botaoAgendar);
        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agendarJob();
                toggleWidgets(false);
            }
        });
        cancelar = (Button) findViewById(R.id.botaoCancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelarJobs();
                toggleWidgets(true);
            }
        });

        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        if (!podeEscrever()) {
            requestPermissions(STORAGE_PERMISSIONS, WRITE_EXTERNAL_STORAGE_REQUEST);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case WRITE_EXTERNAL_STORAGE_REQUEST:
                if (!podeEscrever()) {
                    Toast.makeText(this, "Sem permissão para escrita", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter f = new IntentFilter(DownloadService.DOWNLOAD_COMPLETE);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(onDownloadCompleteEvent, f);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(onDownloadCompleteEvent);
    }

    private BroadcastReceiver onDownloadCompleteEvent=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent i) {
            toggleWidgets(true);
            Toast.makeText(ctxt, "Download finalizado!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(ctxt,DownloadViewActivity.class));
        }
    };

    private void toggleWidgets(boolean enable) {
        agendar.setEnabled(enable);
        period.setEnabled(enable);
    }

    private void agendarJob() {
        JobInfo.Builder b = new JobInfo.Builder(JOB_ID, new ComponentName(this, DownloadJobService.class));
        PersistableBundle pb=new PersistableBundle();
        pb.putBoolean(KEY_DOWNLOAD, true);
        b.setExtras(pb);

        //criterio de rede
        b.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        //b.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);

        //define intervalo de periodicidade
        //b.setPeriodic(getPeriod());

        //exige (ou nao) que esteja conectado ao carregador
        b.setRequiresCharging(false);

        //persiste (ou nao) job entre reboots
        //se colocar true, tem que solicitar permissao action_boot_completed
        b.setPersisted(false);

        //exige (ou nao) que dispositivo esteja idle
        b.setRequiresDeviceIdle(false);

        //backoff criteria (linear ou exponencial)
        //b.setBackoffCriteria(1500, JobInfo.BACKOFF_POLICY_EXPONENTIAL);

        //periodo de tempo minimo pra rodar
        //so pode ser chamado se nao definir setPeriodic...
        b.setMinimumLatency(3000);

        //mesmo que criterios nao sejam atingidos, define um limite de tempo
        //so pode ser chamado se nao definir setPeriodic...
        b.setOverrideDeadline(6000);

        jobScheduler.schedule(b.build());
    }

    private void cancelarJobs() {
        jobScheduler.cancel(JOB_ID);
        // cancela todos os jobs da aplicacao
        // jobScheduler.cancelAll();
    }

    private long getPeriod() {
        return(PERIODS[period.getSelectedItemPosition()]);
    }

}
