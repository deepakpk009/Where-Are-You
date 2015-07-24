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

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

/**
 * this service is for alerting the user on sms reception with the code
 * 
 * @author deepak
 * 
 */

public class AlertService extends Service {

	// service tag
	public static final String TAG = "AlertServiceTag";
	// media player object for playing ring tone
	private MediaPlayer mediaPlayer = null;
	// timer for scheduling the timed stopping of the repeated ring tone playing
	private Timer timer = null;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onCreate();
		// change profile to normal
		AudioManager audioManager = (AudioManager) getApplicationContext()
				.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

		// set max volume
		int streamMaxVolume = audioManager
				.getStreamMaxVolume(AudioManager.STREAM_RING);
		audioManager.setStreamVolume(AudioManager.STREAM_RING, streamMaxVolume,
				AudioManager.FLAG_ALLOW_RINGER_MODES
						| AudioManager.FLAG_PLAY_SOUND);

		// play the ring tone
		// use the media player to play the ring tone repeatedly
		mediaPlayer = MediaPlayer
				.create(getApplicationContext(),  R.raw.tone);
		// play the tone repeatedly
		mediaPlayer.setLooping(true);
		mediaPlayer.start();

		// define a timer task
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// the tak is to stop the alert
				stopAlert();
			}
		};

		// create a timer object
		timer = new Timer();
		// play the alert for 5 min
		// so schedule the task to be called after a delay of 5 min (1000 milisec * 60 sec * 5 min)
		timer.schedule(task, 1000 * 60 * 5);

		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// on stopping the service stop playing the ring tone
		stopAlert();
	}

	/**
	 * method to stop the alert
	 */
	private void stopAlert() {
		// if the media player is not null and ring tone is playing then
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			// stop playing
			mediaPlayer.stop();
			// replease resource
			mediaPlayer.release();
			// reference to null
			mediaPlayer = null;
		}
		// if the timer is not null
		if (timer != null) {
			// then cancel the scheduled task
			timer.cancel();
			// point to null
			timer = null;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
