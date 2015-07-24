/*
 WhereAreYou v0.1
 -------------------------------------
 a SMS based mobile finding app for android
 -------------------------------------
 Developed By : deepak pk
 Email : deepakpk009@yahoo.in
 -------------------------------------
 This Project is Licensed under LGPL
 -------------------------------------
 
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.deepak.whereareyou;

import android.os.Bundle;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;

/**
 * this activity just activates the broadcast listner for the sms reception and
 * displays the welcome note
 * 
 * @author deepak
 * 
 */
public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// set the font for the info text
		EditText et = (EditText) findViewById(R.id.infoEditText);
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/Beta54.ttf");
		et.setTypeface(face);

		// stop the alert service - if its running
		// Occurs when the AlertStopperActivity did not make it to
		// stop the alert activity
		// here we are not checking whether the alert service is
		// running or not, as the stopService doesn't do anything
		// if the alert service is already stopped.
		Intent alertIntent = new Intent(this, AlertService.class);
		alertIntent.addCategory(AlertService.TAG);
		stopService(alertIntent);
	}

}
