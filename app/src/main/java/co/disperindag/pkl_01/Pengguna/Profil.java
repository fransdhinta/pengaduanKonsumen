package co.disperindag.pkl_01.Pengguna;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import co.disperindag.pkl_01.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class Profil extends AppCompatActivity {

    private FirebaseDatabase database;

    private EditText etnama;
    private EditText etnip;
    private EditText etalamat;
    private EditText etagama;
    private EditText etjk;
    private EditText ettelp;
    private EditText etpkj;
    private EditText etttl;
    private CircleImageView fotoProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Intent intent = getIntent();

        final String userName = intent.getStringExtra("namaNya");
        final String userNik = intent.getStringExtra("nikNya");
        final String userTtl = intent.getStringExtra("TtlNya");
        final String userAgama = intent.getStringExtra("Agama");
        final String userJk = intent.getStringExtra("JkNya");
        final String userAlamat = intent.getStringExtra("AlamatNya");
        final String userTelp = intent.getStringExtra("TelpNya");
        final String userPkj = intent.getStringExtra("PkjNya");
        final String userPwd = intent.getStringExtra("PwdNya");
        final String userFto = intent.getStringExtra("FtoNya");
        final String userUrl = intent.getStringExtra("UrlNya");

        etnama = (EditText) findViewById(R.id.namaPg);
        etnip = (EditText) findViewById(R.id.nipPg);
        etalamat = (EditText) findViewById(R.id.alamatPg);
        etagama = (EditText) findViewById(R.id.agamaPg);
        etjk= (EditText) findViewById(R.id.jkPg);
        ettelp = (EditText) findViewById(R.id.telpPg);
        etpkj = (EditText) findViewById(R.id.pkjPg);
        etttl = (EditText) findViewById(R.id.tv_ttlPg);
        fotoProfile = (CircleImageView) findViewById(R.id.profile_image);

        etnama.setEnabled(false);
        etnip.setEnabled(false);
        etalamat.setEnabled(false);
        etagama.setEnabled(false);
        etjk.setEnabled(false);
        ettelp.setEnabled(false);
        etpkj.setEnabled(false);
        etttl.setEnabled(false);

        //ToDo memulai akses database
        database = FirebaseDatabase.getInstance();

        //ToDo Storage yang akan digunakan ENGKOK ae YAAA
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("DataPengguna");

        //ToDo Database yang akan digunakan ENGKOK pisan YAAA
        DatabaseReference userReference = database.getReference("DataPengguna").child("nik");

        etnama.setText(userName);
        etjk.setText(userJk);
        etagama.setText(userAgama);
        etalamat.setText(userAlamat);
        etnip.setText(userNik);
        ettelp.setText(userTelp);
        etpkj.setText(userPkj);
        etttl.setText(userTtl);
        Picasso.with(this).load(userUrl).into(fotoProfile);
        //ToDo Gawe gambar sek yaaa
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, Profil.class);
    }
}
