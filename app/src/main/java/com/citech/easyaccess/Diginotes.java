package com.citech.easyaccess;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Diginotes extends AppCompatActivity {
    WebView webView5;
    int x = 1;
    SwipeRefreshLayout myswipeRefreshLayout;
    public ProgressBar progressBar;
    public int i = 0, j = 0;
    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;
    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private static final int FILECHOOSER_RESULTCODE = 1;
    public String url;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }

            Uri[] results = null;

            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;

        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (requestCode != FILECHOOSER_RESULTCODE || mUploadMessage == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }

            if (requestCode == FILECHOOSER_RESULTCODE) {

                if (null == this.mUploadMessage) {
                    return;

                }

                Uri result = null;

                try {
                    if (resultCode != RESULT_OK) {

                        result = null;

                    } else {

                        // retrieve from the private variable if the intent is null
                        result = data == null ? mCapturedImageURI : data.getData();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "activity :" + e,
                            Toast.LENGTH_LONG).show();
                }

                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;

            }
        }

        return;
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citech);
        webView5 = (WebView) findViewById(R.id.wv);

        myswipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        progressBar = (ProgressBar) findViewById(R.id.pb);
        Button button1 = (Button) findViewById(R.id.b1);
        Button button2 = (Button) findViewById(R.id.b2);
        Button button3 = (Button) findViewById(R.id.b3);
        Button button4 = (Button) findViewById(R.id.b4);

        url = "http://www.diginotes.in/";


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Citech.class);
                startActivity(i);

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DUMYONE.class);
                startActivity(i);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DUMYTWO.class);
                startActivity(i);
            }
        });

        webView5.clearHistory();
        //webView5.clearFormData();


        //webView5.getSettings().setSupportZoom(true);
        //webView5.getSettings().setBuiltInZoomControls(false);

        //webView5.clearHistory();
        //webView5.clearFormData();
        //webView5.clearCache(true);

        //webView5.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView5.getSettings().setDomStorageEnabled(true);
        webView5.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false;  // no override, let the webview load this url
            }
        });
        //  webView5.getSettings().setUserAgentString("Android Mozilla/5.0 AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        webView5.getSettings().setAllowFileAccess(true);
        webView5.getSettings().setAllowContentAccess(true);
        // webView5.getSettings().supportZoom();
        webView5.getSettings().setAppCacheEnabled(true);
        //  webView.getSettings().setLoadWithOverviewMode(true);
        //  webView.getSettings().setSupportMultipleWindows(true);
        webView5.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView5.getSettings().setJavaScriptEnabled(true);
        webView5.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView5.getSettings().setLoadWithOverviewMode(true);
        webView5.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView5.getSettings().setUseWideViewPort(true);
        webView5.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView5.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        webView5.getSettings().setSupportMultipleWindows(true);
        //webView5.loadUrl("https://www.instagram.com");
        webView5.setWebChromeClient(new ChromeClient());
        if (CheckNetwork.isInternetAvailable(Diginotes.this)) //returns true if internet available
        {

            //do something. loadwebview.
            j = 1;
            webView5.loadUrl(url);
        } else {
            Toast.makeText(Diginotes.this, "No Internet Connection", 1000).show();
            //webView.loadUrl("file:///android_asset/error.html");
            Diginotes.this.myswipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(ProgressBar.GONE);
        }


        myswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {


                if (CheckNetwork.isInternetAvailable(Diginotes.this)) //returns true if internet available
                {

                    //do something. loadwebview.
                    //    webView.loadUrl(ur);
                    if (j == 1) {
                        webView5.loadUrl(url);
                    } else if (j == 0) {
                        webView5.loadUrl(url);

                    }
                    if (progressBar.getVisibility() == progressBar.GONE) {
                        progressBar.setVisibility(ProgressBar.VISIBLE);

                    }


                    Diginotes.this.myswipeRefreshLayout.setRefreshing(false);


                } else {
                    Toast.makeText(Diginotes.this, "No Internet Connection", 3000).show();
                    Diginotes.this.myswipeRefreshLayout.setRefreshing(false);
                    progressBar.setVisibility(ProgressBar.GONE);

                }


            }
        });

        webView5.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        //       webView5.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView5.getSettings().setUseWideViewPort(true);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == 0) {
            switch (keyCode) {
                case R.styleable.View_theme:
                    if (webView5.canGoBack()) {
                        webView5.goBack();
                    } else {
                        webView5.clearHistory();
                        //webView5.clearFormData();
                        webView5.clearCache(true);
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    public class ChromeClient extends WebChromeClient {

        // progress bar when first called
        @Override
        public void onProgressChanged(WebView view, int progress) {
            progressBar.setProgress(progress);
            //int i=0;
            if (progress < 100 && progressBar.getVisibility() == progressBar.GONE && i == 0) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                i++;
            }
            if (progress == 100) {
                progressBar.setVisibility(ProgressBar.GONE);
                i++;
            }
        }

        // For Android 5.0
        public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {
            // Double check that we don't have any existing callbacks
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(null);
            }
            mFilePathCallback = filePath;

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    //  Log.e(Common.TAG, "Unable to create Image File", ex);
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                } else {
                    takePictureIntent = null;
                }
            }

            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("image/*");

            Intent[] intentArray;
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
            } else {
                intentArray = new Intent[0];
            }

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

            return true;

        }

        // openFileChooser for Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {

            mUploadMessage = uploadMsg;
            // Create AndroidExampleFolder at sdcard
            // Create AndroidExampleFolder at sdcard

            File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "AndroidExampleFolder");

            if (!imageStorageDir.exists()) {
                // Create AndroidExampleFolder at sdcard
                imageStorageDir.mkdirs();
            }

            // Create camera captured image file path and name
            File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");

            mCapturedImageURI = Uri.fromFile(file);

            // Camera capture image intent
            final Intent captureIntent = new Intent(
                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");

            // Create file chooser intent
            Intent chooserIntent = Intent.createChooser(i, "Image Chooser");

            // Set camera intent to file chooser
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});

            // On select image call onActivityResult method of activity
            startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
        }

        // openFileChooser for Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "");
        }

        //openFileChooser for other Android versions
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType,
                                    String capture) {

            openFileChooser(uploadMsg, acceptType);
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    // to check internet
    public static class CheckNetwork {
        private static final String TAG = CheckNetwork.class.getSimpleName();

        public static boolean isInternetAvailable(Context context) {
            NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

            if (info == null) {
                Log.d(TAG, "no internet connection");
                return false;
            } else {
                if (info.isConnected()) {
                    Log.d(TAG, " internet connection available...");
                    return true;
                } else {
                    Log.d(TAG, " internet connection");
                    return true;
                }

            }
        }
    }

}


