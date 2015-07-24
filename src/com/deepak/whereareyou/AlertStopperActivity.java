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

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

/**
 * this activity is called upon the sms reception with the code provides
 * mechanism to stop playing the ring tone on finding the phone
 * 
 * @author deepak
 * 
 */
public class AlertStopperActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alert_stopper);

		// set the font to the info
		TextView tv = (TextView) findViewById(R.id.infoTextView);
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/Beta54.ttf");
		tv.setTypeface(face);
	}

	/**
	 * method called on clicking any where on the displayed screen stops the
	 * alerter service
	 * 
	 * @param v
	 */
	public void stopAlert(View v) {
		// stop the alert service
		Intent alertIntent = new Intent(this, AlertService.class);
		alertIntent.addCategory(AlertService.TAG);
		stopService(alertIntent);
		// goto home screen
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		// finish the activity
		finish();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		stopAlert(null);
	}
}
