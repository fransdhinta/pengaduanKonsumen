package co.disperindag.pkl_01.Pengaduan;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.disperindag.pkl_01.Pengguna.DataPengguna;
import co.disperindag.pkl_01.Pengguna.Pendaftaran;
import co.disperindag.pkl_01.Pengguna.Profil;
import co.disperindag.pkl_01.R;
import pub.devrel.easypermissions.EasyPermissions;

import static android.text.TextUtils.isEmpty;

public class Pengadu extends AppCompatActivity {

    private DatabaseReference database;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAPTURE_IMAGE_REQUEST = 2;
    //private static final int TAKE_IMAGE_REQUEST = 2;
    private static final String TAG = "Pengaduan";

    private Button btnLanjut;
    private Button btnKembali;
    private EditText etnama;
    private EditText etumur;
    private EditText etjk;
    private EditText etalamat;
    private EditText etpos;
    private EditText ettelp;
    private EditText etfoto;
    private Button btnFoto;
    private ImageView imgLampiran;

    String pathFile;

    File mFileURI;

    private Uri fotoUri;

    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengadu);

        if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
            //pickImageFromGallery();
        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                    101, galleryPermissions);
        }

        btnLanjut = (Button) findViewById(R.id.btn_lanjut);
        btnKembali = (Button) findViewById(R.id.btn_kembali);
        btnFoto = (Button) findViewById(R.id.btn_photo);
        etnama = (EditText) findViewById(R.id.namaUsr);
        etumur = (EditText) findViewById(R.id.umurUsr);
        etjk = (EditText) findViewById(R.id.jkUsr);
        etalamat = (EditText) findViewById(R.id.alamatUsr);
        etpos = (EditText) findViewById(R.id.posUsr);
        ettelp = (EditText) findViewById(R.id.telpUsr);
        etfoto = (EditText) findViewById(R.id.phtUsr);
        imgLampiran = (ImageView) findViewById(R.id.imagePreview);

        Intent intent = getIntent();
        final String nama = intent.getStringExtra("namaNya");
        final String alamat = intent.getStringExtra("AlamatNya");
        final String jk = intent.getStringExtra("JkNya");
        final String telp = intent.getStringExtra("TelpNya");

        if (isEmpty(etnama.getText().toString()) && isEmpty(etumur.getText().toString()) && isEmpty(etjk.getText().toString()) && isEmpty(etalamat.getText().toString()) && isEmpty(etpos.getText().toString()) && isEmpty(ettelp.getText().toString()) && isEmpty(etfoto.getText().toString()))
        {
            etnama.setText(nama);
            etalamat.setText(alamat);
            etjk.setText(jk);
            ettelp.setText(telp);

        }

        btnFoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                //ToDo memunculkan dialog setelah LongKlik
                final Dialog dialog =  new Dialog(Pengadu.this);
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

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty(etnama.getText().toString()) && !isEmpty(etumur.getText().toString()) && !isEmpty(etjk.getText().toString()) && !isEmpty(etalamat.getText().toString()) && !isEmpty(etpos.getText().toString()) && !isEmpty(ettelp.getText().toString()) && !isEmpty(etfoto.getText().toString()) && fotoUri != null)
                {
                    String namaPd = etnama.getText().toString().trim();
                    String umurPd = etumur.getText().toString();
                    String jkPd = etjk.getText().toString();
                    String alamatPd = etalamat.getText().toString();
                    String posPd = etpos.getText().toString();
                    String teleponPd = ettelp.getText().toString();

                    //ToDO IKI LOH !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    //ToDo cek apakah data URI dari foto sudah ada atau belum
                    if (fotoUri != null) {
                        Intent pengadu = new Intent(Pengadu.this, Teradu.class);

                        pengadu.putExtra("nama", namaPd);
                        pengadu.putExtra("umur", umurPd);
                        pengadu.putExtra("jk", jkPd);
                        pengadu.putExtra("alamat", alamatPd);
                        pengadu.putExtra("pos", posPd);
                        pengadu.putExtra("telepon", teleponPd);
                        pengadu.putExtra("fotoPengadu", fotoUri.toString());

                        startActivity(pengadu);
                    }
                }
                else {
                    //ToDo menampilkan Snackbar (Seperti Toast tapi lebih interaktif)
                    Snackbar.make(findViewById(R.id.btn_lanjut), "Data Pengadu tidak boleh kosong", Snackbar.LENGTH_LONG).show();
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
        return "DataPd" + timeStamp + ".jpg";
    }

    private void openFileChooser() {
        //ToDo membuka file Chooser
        Intent intent = new Intent();

        //ToDo untuk memfilter file jenis apa yang akan ditampilkan pada file chooser
        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //ToDo Result untuk request pada method openCamera
        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {

            Uri uri = fotoUri;

            Bitmap imageBitmap = BitmapFactory.decodeFile(uri.getPath());

            imgLampiran.setImageBitmap(imageBitmap);
        }

        //ToDo Result untuk request pada method openFileChooser
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fotoUri = data.getData();

            Picasso.with(this).load(fotoUri).into(imgLampiran);
            imgLampiran.setImageURI(fotoUri);
        }
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, Pengadu.class);
    }
}