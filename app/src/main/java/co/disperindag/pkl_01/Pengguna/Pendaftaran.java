package co.disperindag.pkl_01.Pengguna;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.disperindag.pkl_01.R;
import pub.devrel.easypermissions.EasyPermissions;

public class Pendaftaran extends AppCompatActivity {

    //ToDO Deklarasi Variable
    private DatabaseReference database;

    //ToDo variable biasa untuk memuat integer atau String yang digunakan untuk request code
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAPTURE_IMAGE_REQUEST = 2;
    private static final String TAG = "Pendaftaran";

    //variable komponen dalam layout
    private Button btnSubmit;
    private EditText etnama;
    private EditText etnik;
    private EditText etttl;
    private EditText etagama;
    private EditText etjk;
    private EditText etalamat;
    private EditText ettelp;
    private EditText etpekerjaan;
    private EditText etpwd;
    private Button btnFoto;
    private EditText etfoto;
    private ImageView imgPrev;

    String pathFile;

    File mFileURI;

    //ToDo variabel fotoUri untuk menyimpan
    private Uri fotoUri;

    private StorageReference storage;

    //ToDo karena Permission dari File manifest tidak bisa, Terus pakek ini :D
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftaran);

        //ToDo permission tadi terus dimuat dengan Library EasyPermission
        if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
            //pickImageFromGallery();
        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                    101, galleryPermissions);
        }
        //ToDo komponen layout
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        etnama = (EditText) findViewById(R.id.namaUsr);
        etnik = (EditText) findViewById(R.id.nikUsr);
        etttl = (EditText) findViewById(R.id.ttlUsr);
        etagama = (EditText) findViewById(R.id.agamaUsr);
        etjk = (EditText) findViewById(R.id.jkUsr);
        etalamat = (EditText) findViewById(R.id.alamatUsr);
        ettelp = (EditText) findViewById(R.id.telpUsr);
        etpekerjaan= (EditText) findViewById(R.id.pkjUsr);
        etpwd = (EditText) findViewById(R.id.pwdUsr);
        btnFoto = (Button) findViewById(R.id.btn_photo);
        etfoto = (EditText) findViewById(R.id.phtUsr);
        imgPrev = (ImageView) findViewById(R.id.imagePreview);

        //ToDo lokasi database yang digunakan
        storage = FirebaseStorage.getInstance().getReference("DataPengguna");
        //action Button Pilih Foto

        //ToDo action Button
        btnFoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                //ToDo memunculkan dialog setelah LongKlik
                final Dialog dialog =  new Dialog(Pendaftaran.this);
                dialog.setContentView(R.layout.dialog_capture_select);
                dialog.setTitle("Pilih Aksi");
                dialog.show();

                Button btn_edt = (Button) dialog.findViewById(R.id.btn_ambil);
                Button btn_dlt = (Button) dialog.findViewById(R.id.btn_pilih);

                btn_edt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        //ToDo menjalankan method openCamera()
                        openCamera();
                    }
                });

                btn_dlt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        //ToDo menjalankan method openFileChooser()
                        openFileChooser();
                    }
                });
                return true;
            }

        });
        //ToDo memulai akses FirebaseDatabase
        database = FirebaseDatabase.getInstance().getReference();

        final DataPengguna dataPengguna = (DataPengguna) getIntent().getSerializableExtra("data");

        //ToDo action untuk button Submit
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToDo cek jika form" yang ada sudah terisi atau belum
                if (!isEmpty(etnama.getText().toString()) && !isEmpty(etnik.getText().toString()) && !isEmpty(etalamat.getText().toString()) && !isEmpty(etagama.getText().toString()) && !isEmpty(etjk.getText().toString()) && !isEmpty(etttl.getText().toString()) && !isEmpty(ettelp.getText().toString()) && !isEmpty(etpwd.getText().toString()) && !isEmpty(etpekerjaan.getText().toString()) && fotoUri != null)
                {
                    //ToDO IKI LOH !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    //ToDo cek apakah data URI dari foto sudah ada atau belum
                    if (fotoUri != null) {
                        //ToDo memberi nama file pada FirebaseStorage
                        final StorageReference fileReference = storage.child(etfoto.getText().toString().trim() + "." + getFileExtension(fotoUri));

                        //ToDo untuk Upload
                        fileReference.putFile(fotoUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                                if(!task.isSuccessful())
                                {
                                    throw task.getException();
                                }
                                return fileReference.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                //ToDo jika upload complete
                                if(task.isSuccessful())
                                {
                                    //ToDo mengambil downloadURL dari file yang telah diupload
                                    Uri downloadUri = task.getResult();
                                    Log.e(TAG, "then : " + downloadUri.toString());

                                    Toast.makeText(Pendaftaran.this, "Upload Sukses", Toast.LENGTH_LONG).show();

                                    //ToDo setelah downloadURL diambil baru entry semua data dari form ke dalam FirebaseDatabase
                                    submitDataModel(new DataPengguna(etnama.getText().toString(), etnik.getText().toString(), etttl.getText().toString(), etagama.getText().toString(), etjk.getText().toString(), etalamat.getText().toString() , ettelp.getText().toString(), etpekerjaan.getText().toString(), etpwd.getText().toString(), etfoto.getText().toString(), downloadUri.toString()));
                                }
                            }
                        });
                    }
                }
                else {
                    //ToDo menampilkan Snackbar (Seperti Toast tapi lebih interaktif)
                    Snackbar.make(findViewById(R.id.btn_submit), "Data barang tidak boleh kosong", Snackbar.LENGTH_LONG).show();
                }
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        etnama.getWindowToken(), 0);
            }
        });
    }

    private void openCamera() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //ToDo request untuk membuka kamera
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camera.resolveActivity(getPackageManager()) != null) {

            //ToDo tempat penyimpanan file
            File imgDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            //ToDo nama file
            String imgName = getPictureName();

            //ToDo membuat file baru dengan data pada parameter nama dan lokasi file
            File imgFile = new File(imgDir, imgName);

            //ToDo menyimpan URI file kevariable fotoUri
            fotoUri = Uri.fromFile(imgFile);
            camera.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
            camera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivityForResult(camera, CAPTURE_IMAGE_REQUEST);
        }
    }

    private String getPictureName() {
        //ToDo membuat dateformat
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

        //ToDo menimpan String yang berisi date format
        String timeStamp = sdf.format(new Date());

        //ToDo return untuk nama file
        return "DataPg" + timeStamp + ".jpg";
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {
        //ToDo membuka file Chooser
        Intent intent = new Intent();

        //ToDo untuk memfilter file jenis apa yang akan ditampilkan pada file chooser
        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    //ToDo method yang dijalankan setelah aplikasi melakukan request pada system
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //ToDo Result untuk request pada method openCamera
        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {

            Uri uri = fotoUri;

            Bitmap imageBitmap = BitmapFactory.decodeFile(uri.getPath());

            imgPrev.setImageBitmap(imageBitmap);
        }

        //ToDo Result untuk request pada method openFileChooser
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fotoUri = data.getData();

            Picasso.with(this).load(fotoUri).into(imgPrev);
            imgPrev.setImageURI(fotoUri);
        }
    }

    private boolean isEmpty(String s)
    {
        return TextUtils.isEmpty(s);
    }

    public void submitDataModel(DataPengguna dataModel) {
        //ToDo melakukan penambahan data kedalam FirebaseDatabase
        database.child("DataPengguna").push().setValue(dataModel).addOnSuccessListener(this, new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid) {
                etnama.setText("");
                etnik.setText("");
                etttl.setText("");
                etjk.setText("");
                etagama.setText("");
                etalamat.setText("");
                ettelp.setText("");
                etpwd.setText("");
                etfoto.setText("");
                etpekerjaan.setText("");
                Snackbar.make(findViewById(R.id.btn_submit), "Data Berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, Pendaftaran.class);
    }
}


