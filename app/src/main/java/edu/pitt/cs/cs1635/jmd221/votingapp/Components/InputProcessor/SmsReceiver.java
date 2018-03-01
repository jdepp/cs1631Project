package edu.pitt.cs.cs1635.jmd221.votingapp.Components.InputProcessor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware.VoteModel;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware.VoterTable;

/**
 * Created by jeremydeppen on 2/22/18.
 */

public class SmsReceiver extends BroadcastReceiver {
    VoterTable voterTable = new VoterTable();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();

        if (intentExtras != null) {
            /* Get Messages */
            Object[] sms = (Object[]) intentExtras.get("pdus");

            if(sms != null) {
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

                    if(messageAsInt > 0) {
                        VoteModel newVoter = new VoteModel(phone, messageAsInt);
                        voterTable.addVote(newVoter);
                    }

                    Log.d("NUMBER", phone);
                    Log.d("MSG", phone);

                    Toast.makeText(context, phone + ": " + message, Toast.LENGTH_LONG).show();

                    String voteAsk = "Vote received for " + message;
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone, null, voteAsk, null, null);
                }
            }
        }
    }

    public VoterTable getVoterTable() {
        return voterTable;
    }
}