package co.disperindag.pkl_01;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import co.disperindag.pkl_01.Pengguna.DataPengguna;
import co.disperindag.pkl_01.Pengguna.Pendaftaran;
//Todo main activity untuk login
public class MainActivity extends AppCompatActivity {

    //ToDo memasukkan data dari DataPengguna kedalam dataPengguna
    private DatabaseReference databaseReference;
    private ArrayList<DataPengguna> dataPengguna;

    String tNama, tPwd;

    //ToDo deklarasi variable
    Button btnRegis;
    Button btnLogin;
    EditText etuname;
    EditText etpwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ToDo komponen layout
        etuname = (EditText) findViewById(R.id.unamePg);
        etpwd = (EditText) findViewById(R.id.pwdPg);

        btnLogin = (Button) findViewById(R.id.btn_Login);
        btnRegis = (Button) findViewById(R.id.btn_Regis);

        //ToDO aksess database (getInstance = untuk memulai akses ke Firebase Database)
        //ToDo & (getReferences = untuk akses mana yang akan digunakan)
        //ToDo & (getChild = lebih detail kedalam dari getReference)
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //ToDo action button login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pwd = etpwd.getText().toString();

                Toast.makeText(getApplicationContext(), "Memproses", Toast.LENGTH_SHORT).show();

                //ToDo cek apakah nik cocok dengan form etuname
                Query query = databaseReference.child("DataPengguna").orderByChild("nik").equalTo(etuname.getText().toString().trim());

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataPengguna = new ArrayList<>();
                        //ToDo jika data yang diinputkan cocok / ada
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot pegawai : dataSnapshot.getChildren()) {
                                DataPengguna dataPengguna1 = pegawai.getValue(DataPengguna.class);

                                //ToDo mengambil data yang nanti akan dikirimkan ke Class dataPengguna untuk menampilkan detil
                                String uNama = dataPengguna1.getName();
                                String uNik = dataPengguna1.getNik();
                                String uTtl = dataPengguna1.getTtl();
                                String uAgama = dataPengguna1.getAgama();
                                String uJk = dataPengguna1.getJk();
                                String uAlamat = dataPengguna1.getAlamat();
                                String uTelp = dataPengguna1.getTelp();
                                String uPkj = dataPengguna1.getPkj();
                                String uPwd = dataPengguna1.getPwd();
                                String uFto = dataPengguna1.getFto();
                                String uUrl = dataPengguna1.getUrlfto();

                                //Todo melakukan pencocokan lagi tapi pada form password
                                if (dataPengguna1.getPwd().equals(etpwd.getText().toString().trim())) {
                                    //ToDo dan jika cocok/ada
                                    //Todo langsung mengirim data lewat Intent ke Class dashboard
                                    Intent lihat = new Intent(MainActivity.this, dashboard.class);

                                    //Todo penjelasan untuk <putExtra("namaVariableData", variableIsiData-> (dari ToDo diatas)>)
                                    lihat.putExtra("nama", uNama);
                                    lihat.putExtra("nik", uNik);
                                    lihat.putExtra("ttl", uTtl);
                                    lihat.putExtra("agama", uAgama);
                                    lihat.putExtra("jk", uJk);
                                    lihat.putExtra("alamat", uAlamat);
                                    lihat.putExtra("telp", uTelp);
                                    lihat.putExtra("pkj", uPkj);
                                    lihat.putExtra("pwd", uPwd);
                                    lihat.putExtra("fto", uFto);
                                    lihat.putExtra("url", uUrl);

                                    startActivity(lihat);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Password anda salah", Toast.LENGTH_SHORT).show();
                                }
                                dataPengguna.add(dataPengguna1);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent daftar = new Intent(MainActivity.this, Pendaftaran.class);
                startActivity(daftar);

            }
        });
    }
}
