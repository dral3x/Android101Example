package org.hackatron.hackatronapp;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Base64;


public class SerializationUtil
{
    public static String encodeBundle(Bundle in)
    {
        Parcel parcel = Parcel.obtain();

        in.writeToParcel(parcel, 0);
        String serialized = Base64.encodeToString(parcel.marshall(), 0);

        parcel.recycle();

        return serialized;
    }

    public static Bundle decodeBundle(String serialized)
    {
        if (serialized == null) {
            return null;
        }

        Parcel parcel = Parcel.obtain();

        byte[] data = Base64.decode(serialized, 0);
        parcel.unmarshall(data, 0, data.length);
        parcel.setDataPosition(0);
        Bundle bundle = parcel.readBundle();

        parcel.recycle();

        return bundle;
    }
}
