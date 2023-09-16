package com.example.zgames.tools;

import android.content.Context;
import android.graphics.Color;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class FieldChecker {
    private static boolean emptyField(@NonNull EditText field) {
        return field.getText().toString().equals("");
    }

    public static boolean allFieldsFilledOut(@NonNull EditText[] fields) {
        for (EditText field : fields) {
            if (emptyField(field)) return false;
        }
        return true;
    }

    public static void changeBorderColors(@NonNull EditText[] fields) {
        for (EditText field : fields) {
            if (emptyField(field)) {
                field.setBackgroundColor(Color.RED);
            } else {
                field.setBackgroundColor(Color.WHITE);
            }
        }
    }

    public static void showIncompleteFieldsToast(Context ctx) {
        Toaster.showToast(ctx, "Please make sure all necessary fields are filled out");
    }
}
