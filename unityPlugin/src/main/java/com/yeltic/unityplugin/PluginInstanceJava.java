package com.yeltic.unityplugin;

import android.app.Activity;
import android.widget.Toast;
import com.yeltic.unityplugin.AndroidDownloader;

public class PluginInstanceJava {
    private static Activity unityActivity;
    private static DownloadCompleteListener downloadCompleteListener;
    private static RequestMadeListener requestMadeListener;

    public static void receiveUnityActivity(Activity tActivity) {
        unityActivity = tActivity;
    }
    public PluginInstanceJava(DownloadCompleteListener downloadListener, RequestMadeListener requestListener) {
        downloadCompleteListener = downloadListener;
        requestMadeListener = requestListener;
    }


    public void madeToast(String message) {
        if (unityActivity != null) {
            Toast.makeText(unityActivity, message, Toast.LENGTH_SHORT).show();
        }
    }
    public void callDownloadArchive(String url, String savePath ,String filename, String extension){
        if (unityActivity != null) {
            AndroidDownloader downloader = new AndroidDownloader(unityActivity, downloadCompleteListener, requestMadeListener);
            downloader.downloadFile(url, savePath, filename, extension);
        }
    }


}
