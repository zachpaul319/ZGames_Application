package com.example.zgames.tools;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
    public static void showToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }

    public static void showGeneralErrorToast(Context ctx) {
        Toaster.showToast(ctx, "An error occurred");
    }
}
