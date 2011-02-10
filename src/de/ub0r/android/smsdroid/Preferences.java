/*
 * Copyright (C) 2010-2011 Felix Bechstein
 * 
 * This file is part of SMSdroid.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/>.
 */
package de.ub0r.android.smsdroid;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.SimpleAdapter;
import de.ub0r.android.lib.Log;
import de.ub0r.android.lib.Utils;

/**
 * Preferences.
 * 
 * @author flx
 */
public class Preferences extends PreferenceActivity {
	/** Preference's name: vibrate on receive. */
	static final String PREFS_VIBRATE = "receive_vibrate";
	/** Preference's name: sound on receive. */
	static final String PREFS_SOUND = "receive_sound";
	/** Preference's name: led color. */
	private static final String PREFS_LED_COLOR = "receive_led_color";
	/** Preference's name: led flash. */
	private static final String PREFS_LED_FLASH = "receive_led_flash";
	/** Preference's name: vibrator pattern. */
	private static final String PREFS_VIBRATOR_PATTERN = "receive_vibrate_mode";
	/** Preference's name: enable notifications. */
	static final String PREFS_NOTIFICATION_ENABLE = "notification_enable";
	/** Preference's name: hide sender/text in notifications. */
	static final String PREFS_NOTIFICATION_PRIVACY = "receive_privacy";
	/** Prefernece's name: show contact's photo. */
	static final String PREFS_CONTACT_PHOTO = "show_contact_photo";
	/** Prefernece's name: show emoticons in messagelist. */
	static final String PREFS_EMOTICONS = "show_emoticons";
	/** Prefernece's name: show bubbles in messagelist. */
	static final String PREFS_BUBBLES = "show_bubbles";
	/** Preference's name: bubbles for incoming messages. */
	private static final String PREFS_BUBBLES_IN = "bubbles_in";
	/** Preference's name: bubbles for outgoing messages. */
	private static final String PREFS_BUBBLES_OUT = "bubbles_out";
	/** Prefernece's name: show full date and time. */
	static final String PREFS_FULL_DATE = "show_full_date";
	/** Prefernece's name: hide send button. */
	static final String PREFS_HIDE_SEND = "hide_send";
	/** Prefernece's name: hide paste button. */
	static final String PREFS_HIDE_PASTE = "hide_paste";
	/** Prefernece's name: hide widget's label. */
	static final String PREFS_HIDE_WIDGET_LABEL = "hide_widget_label";
	/** Preference's name: theme. */
	private static final String PREFS_THEME = "theme";
	/** Theme: black. */
	private static final String THEME_BLACK = "black";
	/** Theme: light. */
	private static final String THEME_LIGHT = "light";
	/** Preference's name: textsize. */
	private static final String PREFS_TEXTSIZE = "textsizen";
	/** Preference's name: show titlebar. */
	public static final String PREFS_SHOWTITLEBAR = "show_titlebar";
	/** Preference's name: enable autosend. */
	public static final String PREFS_ENABLE_AUTOSEND = "enable_autosend";
	/** Preference's name: show textfield. */
	public static final String PREFS_SHOWTEXTFIELD = "show_textfield";
	/** Preference's name: show target app. */
	public static final String PREFS_SHOWTARGETAPP = "show_target_app";

	/** Drawable resources for bubbles. */
	private static final int[] BUBBLES_IMG = new int[] { 0, // .
			R.drawable.gray_dark, // .
			R.drawable.gray_light, // .
			R.drawable.bubble_old_green_left, // .
			R.drawable.bubble_old_green_right, // .
			R.drawable.bubble_old_turquoise_left, // .
			R.drawable.bubble_old_turquoise_right, // .
			R.drawable.bubble_blue_left, // .
			R.drawable.bubble_blue_right, // .
			R.drawable.bubble_brown_left, // .
			R.drawable.bubble_brown_right, // .
			R.drawable.bubble_gray_left, // .
			R.drawable.bubble_gray_right, // .
			R.drawable.bubble_green_left, // .
			R.drawable.bubble_green_right, // .
			R.drawable.bubble_orange_left, // .
			R.drawable.bubble_orange_right, // .
			R.drawable.bubble_pink_left, // .
			R.drawable.bubble_pink_right, // .
			R.drawable.bubble_purple_left, // .
			R.drawable.bubble_purple_right, // .
			R.drawable.bubble_white_left, // .
			R.drawable.bubble_white_right, // .
			R.drawable.bubble_yellow_left, // .
			R.drawable.bubble_yellow_right, // .
	};
	/** String resources for bubbles. */
	private static final int[] BUBBLES_STR = new int[] {
			R.string.bubbles_nothing, // .
			R.string.bubbles_plain_dark_gray, // .
			R.string.bubbles_plain_light_gray, // .
			R.string.bubbles_old_green_left, // .
			R.string.bubbles_old_green_right, // .
			R.string.bubbles_old_turquoise_left, // .
			R.string.bubbles_old_turquoise_right, // .
			R.string.bubbles_blue_left, // .
			R.string.bubbles_blue_right, // .
			R.string.bubbles_brown_left, // .
			R.string.bubbles_brown_right, // .
			R.string.bubbles_gray_left, // .
			R.string.bubbles_gray_right, // .
			R.string.bubbles_green_left, // .
			R.string.bubbles_green_right, // .
			R.string.bubbles_orange_left, // .
			R.string.bubbles_orange_right, // .
			R.string.bubbles_pink_left, // .
			R.string.bubbles_pink_right, // .
			R.string.bubbles_purple_left, // .
			R.string.bubbles_purple_right, // .
			R.string.bubbles_white_left, // .
			R.string.bubbles_white_right, // .
			R.string.bubbles_yellow_left, // .
			R.string.bubbles_yellow_right, // .
	};

	/**
	 * Listen to clicks on "bubble" preferences.
	 * 
	 * @author flx
	 */
	private static class OnBubblesClickListener implements
			Preference.OnPreferenceClickListener {
		/** {@link Context}. */
		private final Context ctx;

		/**
		 * Default constructor.
		 * 
		 * @param context
		 *            {@link Context}
		 */
		public OnBubblesClickListener(final Context context) {
			this.ctx = context;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean onPreferenceClick(final Preference preference) {
			final Builder b = new Builder(this.ctx);
			final int l = BUBBLES_STR.length;
			final String[] cols = new String[] { "icon", "text" };
			final ArrayList<HashMap<String, Object>> rows // .
			= new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < l; i++) {
				final HashMap<String, Object> m = // .
				new HashMap<String, Object>(2);
				m.put(cols[0], BUBBLES_IMG[i]);
				m.put(cols[1], this.ctx.getString(BUBBLES_STR[i]));
				rows.add(m);
			}
			b.setAdapter(new SimpleAdapter(this.ctx, rows,
					R.layout.bubbles_item, cols, new int[] { android.R.id.icon,
							android.R.id.text1 }),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog,
								final int which) {
							preference.getEditor().putInt(preference.getKey(),
									which).commit();
						}
					});
			b.setNegativeButton(android.R.string.cancel, null);
			b.show();
			return true;
		}
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// final int theme = Preferences.getTheme(this);
		// this.setTheme(theme);
		this.addPreferencesFromResource(R.xml.prefs);

		final OnBubblesClickListener obcl = new OnBubblesClickListener(this);
		Preference p = this.findPreference(PREFS_BUBBLES_IN);
		if (p != null) {
			p.setOnPreferenceClickListener(obcl);
		}
		p = this.findPreference(PREFS_BUBBLES_OUT);
		if (p != null) {
			p.setOnPreferenceClickListener(obcl);
		}
		p = this.findPreference("send_logs");
		if (p != null) {
			p.setOnPreferenceClickListener(// .
					new Preference.OnPreferenceClickListener() {
						public boolean onPreferenceClick(
								final Preference preference) {
							Log.collectAndSendLog(Preferences.this);
							return true;
						}
					});
		}
	}

	/**
	 * Get Theme from Preferences.
	 * 
	 * @param context
	 *            {@link Context}
	 * @return theme
	 */
	static final int getTheme(final Context context) {
		final SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(context);
		final String s = p.getString(PREFS_THEME, THEME_BLACK);
		if (s != null && THEME_LIGHT.equals(s)) {
			return android.R.style.Theme_Light;
		}
		return android.R.style.Theme_Black;
	}

	/**
	 * Get text's size from Preferences.
	 * 
	 * @param context
	 *            {@link Context}
	 * @return theme
	 */
	static final int getTextsize(final Context context) {
		final SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(context);
		final String s = p.getString(PREFS_TEXTSIZE, null);
		return Utils.parseInt(s, 0);
	}

	/**
	 * Get LED color pattern from Preferences.
	 * 
	 * @param context
	 *            {@link Context}
	 * @return pattern
	 */
	static final int getLEDcolor(final Context context) {
		final SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(context);
		final String s = p.getString(PREFS_LED_COLOR, "65280");
		return Integer.parseInt(s);
	}

	/**
	 * Get LED flash pattern from Preferences.
	 * 
	 * @param context
	 *            {@link Context}
	 * @return pattern
	 */
	static final int[] getLEDflash(final Context context) {
		final SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(context);
		final String s = p.getString(PREFS_LED_FLASH, "500_2000");
		final String[] ss = s.split("_");
		final int[] ret = new int[2];
		ret[0] = Integer.parseInt(ss[0]);
		ret[1] = Integer.parseInt(ss[1]);
		return ret;
	}

	/**
	 * Get vibrator pattern from Preferences.
	 * 
	 * @param context
	 *            {@link Context}
	 * @return pattern
	 */
	static final long[] getVibratorPattern(final Context context) {
		final SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(context);
		final String s = p.getString(PREFS_VIBRATOR_PATTERN, "0");
		final String[] ss = s.split("_");
		final int l = ss.length;
		final long[] ret = new long[l];
		for (int i = 0; i < l; i++) {
			ret[i] = Long.parseLong(ss[i]);
		}
		return ret;
	}

	/**
	 * Get drawable resource for bubble for incoming messages.
	 * 
	 * @param context
	 *            {@link Context}
	 * @return resource id
	 */
	static final int getBubblesIn(final Context context) {
		final SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(context);
		final int i = p.getInt(PREFS_BUBBLES_IN,
				R.drawable.bubble_old_turquoise_left);
		if (i >= 0 && i < BUBBLES_IMG.length) {
			return BUBBLES_IMG[i];
		}
		return R.drawable.bubble_old_turquoise_left;
	}

	/**
	 * Get drawable resource for bubble for outgoing messages.
	 * 
	 * @param context
	 *            {@link Context}
	 * @return resource id
	 */
	static final int getBubblesOut(final Context context) {
		final SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(context);
		final int i = p.getInt(PREFS_BUBBLES_OUT,
				R.drawable.bubble_old_green_right);
		if (i >= 0 && i < BUBBLES_IMG.length) {
			return BUBBLES_IMG[i];
		}
		return R.drawable.bubble_old_green_right;
	}
}
