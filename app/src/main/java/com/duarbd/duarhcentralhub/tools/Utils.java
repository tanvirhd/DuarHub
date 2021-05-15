package com.duarbd.duarhcentralhub.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.RingtoneManager;
import android.net.Uri;
import android.view.Window;

import com.duarbd.duarhcentralhub.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static String getAddress(Context context, double LATITUDE, double LONGITUDE) {
        String locationName = "";
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {


                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                locationName = address + "," + city+ "," +state+ "," +country+ "," +postalCode+ "," +knownName;

//                Log.e(TAG, "getAddress:  address " + address);
//                Log.e(TAG, "getAddress:  city " + city);
//                Log.e(TAG, "getAddress:  state " + state);
//                Log.e(TAG, "getAddress:  postalCode " + postalCode);
//                Log.e(TAG, "getAddress:  knownName " + knownName);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return locationName;
    }

    public static Uri getDefaultNotificationToneUri(){
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    public static String getCustentDateTime24HRFormat(){
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormatDate = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat dateFormatTime = new SimpleDateFormat("HH:MM");
        return dateFormatDate.format(calendar.getTime())+" "+dateFormatTime.format(calendar.getTime());
    }

    public static String getCustentTime24HRFormat(){
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormatTime = new SimpleDateFormat("HH:MM");
        return dateFormatTime.format(calendar.getTime());
    }

    public static String getTimeFromDeliveryRequestPlacedDate(String timeDate){
        String[] separated=timeDate.split(" ");
        return separated[1];
    }

    public static String addMinute(String time,int pickupWithin){
        String ampm="";
        String[] separatedTime=time.split(":");
        int hour=Integer.valueOf(separatedTime[0]);
        int min=Integer.valueOf(separatedTime[1]);

        min=min+pickupWithin;
        if(min>60){
            hour=hour+1;
            min=min-60;
        }

        return hour+":"+min;
    }

    public static Dialog setupLoadingDialog(Activity activity) {
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        //window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);  //this prevents dimming effect
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    public static String[] getCustentDateArray(){
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormatDate = new SimpleDateFormat("dd-MM-yyyy");
        String date=dateFormatDate.format(calendar.getTime());
        String[] seperated=date.split("-");
        return seperated;
    }
}
