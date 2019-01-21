package co.disperindag.pkl_01.Pengaduan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;

import java.io.File;

import co.disperindag.pkl_01.R;
import pub.devrel.easypermissions.EasyPermissions;

import static android.text.TextUtils.isEmpty;

public class Teradu extends AppCompatActivity {

    private static final String TAG = "Pengaduan";

    private Button btnLanjut;
    private Button btnKembali;
    private EditText etnama;
    private EditText etper;
    private EditText etfax;
    private EditText etalamat;
    private EditText etpos;
    private EditText ettelp;

    String pathFile;

    File mFileURI;

    private Uri fotoUri;

    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teradu);

        if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
            //pickImageFromGallery();
        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                    101, galleryPermissions);
        }

        btnLanjut = (Button) findViewById(R.id.btn_lanjut);
        btnKembali = (Button) findViewById(R.id.btn_kembali);
        etnama = (EditText) findViewById(R.id.namaUsr);
        etper = (EditText) findViewById(R.id.perUsr);
        etfax = (EditText) findViewById(R.id.faxUsr);
        etalamat = (EditText) findViewById(R.id.alamatUsr);
        etpos = (EditText) findViewById(R.id.posUsr);
        ettelp = (EditText) findViewById(R.id.telpUsr);

        Intent intent = getIntent();

        final String namaPengadu = intent.getStringExtra("nama");
        final String umurPengadu = intent.getStringExtra("umur");
        final String jkPengadu = intent.getStringExtra("jk");
        final String alamatPengadu = intent.getStringExtra("alamat");
        final String posPengadu = intent.getStringExtra("pos");
        final String teleponPengadu = intent.getStringExtra("telepon");
        final String buktiPengadu = intent.getStringExtra("fotoPengadu");

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty(etnama.getText().toString()) && !isEmpty(etper.getText().toString()) && !isEmpty(etalamat.getText().toString()) && !isEmpty(etpos.getText().toString()) && !isEmpty(ettelp.getText().toString()) && !isEmpty(etfax.getText().toString()))
                {
                    //ToDO Data Pengadu
                    String namaPd = namaPengadu;
                    String umurPd = umurPengadu;
                    String jkPd = jkPengadu;
                    String alamatPd = alamatPengadu;
                    String posPd = posPengadu;
                    String teleponPd = teleponPengadu;
                    String buktiPd = buktiPengadu;

                    //ToDo Data Teradu

                    String namaTd = etnama.getText().toString();
                    String perTd = etper.getText().toString();
                    String alamatTd = etalamat.getText().toString();
                    String posTd = etpos.getText().toString();
                    String telpTd = ettelp.getText().toString();
                    String faxTd = etfax.getText().toString();

                    Intent pengadu = new Intent(Teradu.this, Aduan.class);

                        pengadu.putExtra("nama", namaPd);
                        pengadu.putExtra("umur", umurPd);
                        pengadu.putExtra("jk", jkPd);
                        pengadu.putExtra("alamat", alamatPd);
                        pengadu.putExtra("pos", posPd);
                        pengadu.putExtra("telepon", teleponPd);
                        pengadu.putExtra("fotoPengadu", buktiPd);

                        pengadu.putExtra("nama1", namaTd);
                        pengadu.putExtra("per1", perTd);
                        pengadu.putExtra("alamat1", alamatTd);
                        pengadu.putExtra("pos1", posTd);
                        pengadu.putExtra("telp1", telpTd);
                        pengadu.putExtra("fax1", faxTd);

                        startActivity(pengadu);

                }
                else {
                    //ToDo menampilkan Snackbar (Seperti Toast tapi lebih interaktif)
                    Snackbar.make(findViewById(R.id.btn_submit), "Data Pengadu tidak boleh kosong", Snackbar.LENGTH_LONG).show();
                }
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        etnama.getWindowToken(), 0);
            }
        });
    }
}