package com.yeltic.unityplugin;

public interface RequestMadeListener {
    void onRequestMade(long downloadId, String filename, String extension );
}
