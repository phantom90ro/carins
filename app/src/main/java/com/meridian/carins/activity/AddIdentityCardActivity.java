package com.meridian.carins.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.meridian.carins.R;
import com.meridian.carins.app.AppConfig;
import com.meridian.carins.app.AppController;
import com.meridian.carins.helper.SQLiteHandler;
import com.meridian.carins.helper.SQLiteHandlerUpload;
import com.meridian.carins.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddIdentityCardActivity extends Activity {

    private static final String TAG = AddIdentityCardActivity.class.getSimpleName();

    private static final int IMG_REQUEST = 1;

    private ImageView imagePreview;
    Button btnChoose;
    Button btnUpload;
    private Bitmap bitmap;
    private String file_name;


    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private SQLiteHandlerUpload dbUpload;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_identity_card);
        init();

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        dbUpload = new SQLiteHandlerUpload(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        final String uid = user.get("uid");
        file_name = "CI_" + uid + ".png";


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imagePreview.getDrawable() != null) {
                    uploadImage(imageToString(bitmap), file_name, uid);
                } else {
                    Toast.makeText(getApplicationContext(), "Selecteaza imagine",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imagePreview.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void uploadImage(final String encoded_string,
                             final String name_image,
                             final String user_id) {
        String tag_string_req = "req_img_upload";

        pDialog.setMessage("Uploading...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPLOAD, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Upload Response: " + response);
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            if (!error) {
                                JSONObject image = jObj.getJSONObject("image");
                                String name = image.getString("name");
                                String path = image.getString("path");
                                String user_id = image.getString("user_id");

                                dbUpload.addImage(name, path, user_id);

                                Toast.makeText(getApplicationContext(),
                                        "Image successfully uploaded.",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(), errorMsg,
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Upload Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(),
                        Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("encoded_string", encoded_string);
                params.put("image_name", name_image);
                params.put("user_id", user_id);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void selectImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, IMG_REQUEST);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imgBytes = stream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void init() {
        btnChoose = findViewById(R.id.choose_button);
        btnUpload = findViewById(R.id.upload_button);
        imagePreview = findViewById(R.id.imagePreview);
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(AddIdentityCardActivity.this,
                LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goBack() {
        Intent intent = new Intent(AddIdentityCardActivity.this,
                StatusActivity.class);
        startActivity(intent);
        finish();
    }
}
