package com.android.rivchat.util;

import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

import androidx.annotation.NonNull;

public class FileUploadUtils {

    public interface DoneCallBack {
        void done(String result, Exception e);
    }

    public static StorageReference getFilesReference() {
        return FirebaseStorage.getInstance().getReference().child("files");
    }

    public static void uploadFile(InputStream fileStream, final DoneCallBack resultCallBack) {
        final StorageReference storageReference = getFilesReference();
        final UploadTask uploadTask = storageReference.putStream(fileStream);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    resultCallBack.done(storageReference.getDownloadUrl().toString(), null);
                } else {
                    // Handle failures
                    resultCallBack.done(null, new Exception(task.getException()));
                    // ...
                }
            }
        });

    }

}
