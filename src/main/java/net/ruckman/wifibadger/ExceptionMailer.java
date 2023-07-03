package net.ruckman.wifibadger;

/**
 * Created by William Ruckman on 11/21/2015.
 */
import java.io.PrintWriter;
import java.io.StringWriter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;


public class ExceptionMailer implements
        java.lang.Thread.UncaughtExceptionHandler {
    private final Activity myContext;

    public ExceptionMailer(Activity context) {
        myContext = context;
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        String LINE_SEPARATOR = "\n";
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        String errorReport = "************ CAUSE OF ERROR ************\n\n" +
                stackTrace.toString() +
                "\n************ DEVICE INFORMATION ***********\n" +
                "Brand: " +
                Build.BRAND +
                LINE_SEPARATOR +
                "Device: " +
                Build.DEVICE +
                LINE_SEPARATOR +
                "Model: " +
                Build.MODEL +
                LINE_SEPARATOR +
                "Id: " +
                Build.ID +
                LINE_SEPARATOR +
                "Product: " +
                Build.PRODUCT +
                LINE_SEPARATOR +
                "\n************ FIRMWARE ************\n" +
                "SDK: " +
                Build.VERSION.SDK +
                LINE_SEPARATOR +
                "Release: " +
                Build.VERSION.RELEASE +
                LINE_SEPARATOR +
                "Incremental: " +
                Build.VERSION.INCREMENTAL +
                LINE_SEPARATOR;
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "william@ruckman.net", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "WIFIBadger Error Report");
        emailIntent.putExtra(Intent.EXTRA_TEXT, errorReport);
        myContext.startActivity(Intent.createChooser(emailIntent, "Send email..."));

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

}