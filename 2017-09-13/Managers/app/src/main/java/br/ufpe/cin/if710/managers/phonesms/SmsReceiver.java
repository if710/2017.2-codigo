package br.ufpe.cin.if710.managers.phonesms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.telephony.TelephonyManager.PHONE_TYPE_CDMA;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] rawMsgs=(Object[])intent.getExtras().get("pdus");

        for (Object raw : rawMsgs) {
            //int activePhone = TelephonyManager.getDefault().getCurrentPhoneType();
            //String format = (PHONE_TYPE_CDMA == activePhone) ? SmsConstants.FORMAT_3GPP2 : SmsConstants.FORMAT_3GPP;
            SmsMessage msg = SmsMessage.createFromPdu((byte[])raw);

            Log.w("SMS:"+msg.getOriginatingAddress(), msg.getMessageBody());
            if (msg.getMessageBody().toUpperCase().contains("IF710")) {//iF1001 if1001 If1001
                Intent i = new Intent(context,SmsReceivedActivity.class);
                i.putExtra("msgFrom",msg.getOriginatingAddress());
                i.putExtra("msgBody",msg.getMessageBody());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

                abortBroadcast();
            }
        }
    }
}
