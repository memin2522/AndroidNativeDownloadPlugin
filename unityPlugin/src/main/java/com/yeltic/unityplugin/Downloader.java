package com.yeltic.unityplugin;

public interface Downloader {
    public long downloadFile(String url, String path, String filename, String extension);
}

