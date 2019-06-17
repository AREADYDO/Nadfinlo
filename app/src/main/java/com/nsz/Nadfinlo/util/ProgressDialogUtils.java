package com.nsz.Nadfinlo.util;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtils {
	private static ProgressDialog mProgressDialog;

	/**
	 * ProgressDialog
	 * @param context
	 * @param message
	 */
	public static void showProgressDialog(Context context, CharSequence message){
		if(mProgressDialog == null){
			mProgressDialog = ProgressDialog.show(context, "", message);
		}else{
			mProgressDialog.show();
		}
	}
	
	/**
	 *
	 */
	public static void dismissProgressDialog(){
		if(mProgressDialog != null){
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}
}
