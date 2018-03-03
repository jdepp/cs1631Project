package edu.pitt.cs.cs1635.jmd221.votingapp.Components.InputProcessor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import edu.pitt.cs.cs1635.jmd221.votingapp.PollingActivity;


/* Class that handles receiving text votes and sending back a confirmation */
public class SmsReceiver extends BroadcastReceiver {
    private Listener listener = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(PollingActivity.isInForeground) {
            Bundle intentExtras = intent.getExtras();

            if (intentExtras != null) {
                /* Get Messages */
                Object[] sms = (Object[]) intentExtras.get("pdus");

                if (sms != null) {
                    for (int i = 0; i < sms.length; ++i) {
                    /* Parse Each Message */
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                        String phone = smsMessage.getOriginatingAddress();
                        String message = smsMessage.getMessageBody().toString();

                        int messageAsInt;
                        try {
                            messageAsInt = Integer.parseInt(message);
                        } catch (NumberFormatException e) {
                            messageAsInt = 0;
                        }

                        SmsManager smsManager = SmsManager.getDefault();
                        if (messageAsInt > 0) {
                            if (listener != null) {
                                listener.onTextReceived(phone, message);
                            }
                        } else {
                            String voteAsk = "Could not vote for " + message + " - Not a valid option.";
                            smsManager.sendTextMessage(phone, null, voteAsk, null, null);
                        }

                        Toast.makeText(context, phone + ": vote received for " + message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }


    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onTextReceived(String phone, String vote);
    }
}