package org.diiage.amassey.broadcastintentsmassey;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Intent.ACTION_BATTERY_CHANGED;

public class BatteryBroadcastReceiver extends BroadcastReceiver {

    private final static String BATTERY_LEVEL = "level";
    TextView mBatteryLevelText;
    ProgressBar mBatteryLevelProgress;

    public BatteryBroadcastReceiver() {
    }

    public BatteryBroadcastReceiver(TextView mBatteryLevelText, ProgressBar mBatteryLevelProgress) {
        this.mBatteryLevelText = mBatteryLevelText;
        this.mBatteryLevelProgress = mBatteryLevelProgress;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
        {
            Bundle bundle = intent.getExtras();
            if (bundle != null)
            {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++)  {  messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);  }  if (messages.length > -1)
                {
                    final String messageBody = messages[0].getMessageBody();
                    final String phoneNumber = messages[0].getDisplayOriginatingAddress();

                    Toast toast = Toast.makeText(context, "Message : " + messageBody, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
        if (intent.getAction().equals(ACTION_BATTERY_CHANGED))
        {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

            mBatteryLevelText.setText("Level battery : " + level);
            mBatteryLevelProgress.setProgress(level);

            if(level < 20){
                CharSequence text = "Alert battery < 20% !";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}