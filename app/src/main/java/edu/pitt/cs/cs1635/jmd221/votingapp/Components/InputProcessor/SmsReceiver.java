package edu.pitt.cs.cs1635.jmd221.votingapp.Components.InputProcessor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import edu.pitt.cs.cs1635.jmd221.votingapp.PollingActivity;


/* Class that handles receiving text votes and triggers the onTextReceived() method in Polling Activity */
public class SmsReceiver extends BroadcastReceiver {
    private Listener listener = null;

    // Triggered when an SMS is received
    @Override
    public void onReceive(Context context, Intent intent) {
        if(PollingActivity.isInForeground) {
            Bundle intentExtras = intent.getExtras();

            if (intentExtras != null) {
                // Get Messages
                Object[] sms = (Object[]) intentExtras.get("pdus");

                if (sms != null) {
                    for (int i = 0; i < sms.length; ++i) {
                        // Parse Each Message
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
                        boolean validMessage;
                        if (messageAsInt > 0)
                            validMessage = true;
                        else
                            validMessage = false;

                        // Trigger onTextReceived() method in Polling Activity sending phone number, message, and if it's a valid msg
                        if (listener != null) {
                            listener.onTextReceived(phone, message, validMessage);
                        }
                    }
                }
            }
        }
    }

    // Activates the SmsListener - this method is called in Polling Activity
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    // Interface that forces Polling Activity to implement method onTextReceived()
    public interface Listener {
        void onTextReceived(String phone, String vote, boolean validMessage);
    }
}