package com.yeltic.unityplugin;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class AndroidDownloader extends BroadcastReceiver implements Downloader {
    private Context context;
    private DownloadManager downloadManager;
    private DownloadCompleteListener completeListener;
    private RequestMadeListener requestListener;

    public AndroidDownloader(Context context, DownloadCompleteListener completeListener, RequestMadeListener requestListener) {
        this.context = context;
        this.downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        this.completeListener = completeListener;
        this.requestListener = requestListener;
        context.registerReceiver(this, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        if (completeListener != null) {
            completeListener.onDownloadComplete(downloadId);
        }
    }

    @Override
    public long downloadFile(String url, String path, String filename, String extension) {
        Log.w("Downloads", "Trying to download file");
        try {
            String filePath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + path + "/" + filename + "." + extension;
            Log.w("Downloads", "Download path: " + filePath);

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                    .setTitle(filename)
                    .setDestinationUri(Uri.parse("file://" + filePath));

            long donwloadId = downloadManager.enqueue(request);
            requestListener.onRequestMade(donwloadId, filename, extension);

            return donwloadId;
        } catch (Exception e) {
            Log.e("Downloads", "Error downloading file: " + e.getMessage());
            return -1;
        }
    }

}


