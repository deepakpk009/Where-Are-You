/*
 This file is part of WhereAreYou v0.1

 WhereAreYou is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 WhereAreYou is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with WhereAreYou.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.deepak.whereareyou;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * this class is a sms broadcast receiver. when ever a sms is received, the
 * onReceive method gets called
 * 
 * @author user
 * 
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		// check whether the broadcast is sms received broadcast message
		if (intent.getAction()
				.equals("android.provider.Telephony.SMS_RECEIVED")) {

			// get the SMS message passed in
			Bundle bundle = intent.getExtras();

			SmsMessage[] msgs = null;

			if (bundle != null) {

				try {
					Object[] pdus = (Object[]) bundle.get("pdus");
					msgs = new SmsMessage[pdus.length];

					for (int i = 0; i < msgs.length; i++) {
						msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

						String msgBody = msgs[i].getMessageBody();

						// checking for the sms content with
						// 'where.are.you' activation message
						if (msgBody.toLowerCase().contains("where.are.you")
						// check for this also if the user misunderstood the
						// sms format
								|| msgBody.toLowerCase().contains(
										"'where.are.you'")) {

							// abort further broadcasting
							// this also prevents the message from getting into
							// the inbox
							// DO CHECK THE MANIFEST FILE FOR THE PRIORITY
							// SETTING
							// the priority for our app is set to maximum as we
							// want the broadcast
							// to be processed by our app first
							abortBroadcast();

							// start the service for alerting
							Intent alertIntent = new Intent(context,
									AlertService.class);
							alertIntent.addCategory(AlertService.TAG);
							context.startService(alertIntent);

							// start the alert stopper activity
							Intent alertStopperIntent = new Intent(context,
									AlertStopperActivity.class);
							alertStopperIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.startActivity(alertStopperIntent);
						}
					}
				} catch (Exception e) {
					Log.d("Exception caught", e.getMessage());
				}
			}
		}
	}
}