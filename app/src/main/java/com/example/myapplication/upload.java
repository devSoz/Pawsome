package com.example.myapplication;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.API.dogeAPI;
import com.example.myapplication.API.dogeService;
import com.example.myapplication.Model.AnalysisDoge;
import com.example.myapplication.Model.Result;
import com.example.myapplication.Model.uploadDoge;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class upload extends AppCompatActivity {

    public Uri uri;
    private final static String apiKey = "0bee7108-b9af-414c-8a97-2f803b14ca45";
    private String TAG = "Okie", image_id = "Pof0ARXII";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);
        imageListener();
    }

    private void imageListener() {

        TextView txtSelect = findViewById(R.id.txtUpload);
        Button btnUpload = findViewById(R.id.btnUpload);

        txtSelect.setOnClickListener((View view) -> {
            mGetContent.launch("image/*");
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream is = null;
                try {
                    is = getContentResolver().openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    uploadFile(getBytes(is));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public byte[] getBytes(InputStream is) throws IOException
    {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new
                    ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    Log.d("test", "Test");
                    uri = result;
                    ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                    imageView.setImageURI(result);

                }
    });

    private void uploadFile(byte[] imageBytes)
    {
        String path = uri.getPath();
        File file = new File(path);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        dogeService dogeservice = dogeAPI.getClient().create(dogeService.class);
        Call<uploadDoge> call = dogeservice.upload(body, apiKey);
        call.enqueue(new Callback<uploadDoge>() {
            @Override
            public void onResponse(Call<uploadDoge> call,
                                   Response<uploadDoge> response) {
                image_id = response.body().getId();
                analysis();
                Log.v("dogeUpload", "success");
            }

            @Override
            public void onFailure(Call<uploadDoge> call, Throwable t) {
                Log.e("dogeUpload", t.getMessage());
            }
        });
    }

    public void analysis()
    {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_analysis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dogeService dogeservice = dogeAPI.getClient().create(dogeService.class);
        Call<List<AnalysisDoge>> call = dogeservice.getAnalysisDoge(image_id, apiKey);

        call.enqueue(new Callback<List<AnalysisDoge>>() {
            @Override
            public void onResponse(Call<List<AnalysisDoge>> call, Response<List<AnalysisDoge>> response) {
                List<AnalysisDoge> analysis = response.body();
                recyclerView.setAdapter(new analysis_adapter(analysis, R.layout.analysis_adapter, getApplicationContext()));
                //Log.d(TAG, String.valueOf(dogeList.size()));
            }

            @Override
            public void onFailure(Call<List<AnalysisDoge>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                Log.d(TAG, String.valueOf(t));
            }
        });
    }
}