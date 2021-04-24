package com.duarbd.duarhcentralhub.tools;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.media.RingtoneManager;
import android.net.Uri;

import java.io.IOException;
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
}
