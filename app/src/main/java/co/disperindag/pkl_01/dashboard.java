package co.disperindag.pkl_01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import co.disperindag.pkl_01.Pengaduan.Pengadu;
import co.disperindag.pkl_01.Pengguna.Profil;
//ToDo tampilan menu setelah proses login
public class dashboard extends AppCompatActivity {

    //ToDo context untuk class ini
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        LinearLayout Menu1 = (LinearLayout) findViewById(R.id.Menu1);
        LinearLayout Menu2 = (LinearLayout) findViewById(R.id.Menu2);
        LinearLayout Menu3 = (LinearLayout) findViewById(R.id.Menu3);
        LinearLayout Menu4 = (LinearLayout) findViewById(R.id.Menu4);

        //ToDo mengambil data dari Intent yang dikirimkan dari Halaman Login tadi
        Intent intent = getIntent();
        final String userName = intent.getStringExtra("nama");
        final String userNik = intent.getStringExtra("nik");
        final String userTtl = intent.getStringExtra("ttl");
        final String userAgama = intent.getStringExtra("agama");
        final String userJk = intent.getStringExtra("jk");
        final String userAlamat = intent.getStringExtra("alamat");
        final String userTelp = intent.getStringExtra("telp");
        final String userPkj = intent.getStringExtra("pkj");
        final String userPwd = intent.getStringExtra("pwd");
        final String userFto = intent.getStringExtra("fto");
        final String userUrl = intent.getStringExtra("url");

        ctx = getApplicationContext();


        Menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profil = new Intent(dashboard.this, Profil.class);
                profil.putExtra("namaNya", userName);
                profil.putExtra("nikNya", userNik);
                profil.putExtra("TtlNya", userTtl);
                profil.putExtra("AgamaNya", userAgama);
                profil.putExtra("JkNya", userJk);
                profil.putExtra("AlamatNya", userAlamat);
                profil.putExtra("TelpNya", userTelp);
                profil.putExtra("PkjNya", userPkj);
                profil.putExtra("PwdNya", userPwd);
                profil.putExtra("FtoNya", userFto);
                profil.putExtra("UrlNya", userUrl);

                //ToDo put extra yang lainnya

                startActivity(profil);
            }
        });

        Menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pengaduan = new Intent(dashboard.this, Pengadu.class);

                pengaduan.putExtra("namaNya", userName);
                pengaduan.putExtra("nikNya", userNik);
                pengaduan.putExtra("TtlNya", userTtl);
                pengaduan.putExtra("AgamaNya", userAgama);
                pengaduan.putExtra("JkNya", userJk);
                pengaduan.putExtra("AlamatNya", userAlamat);
                pengaduan.putExtra("TelpNya", userTelp);
                pengaduan.putExtra("PkjNya", userPkj);
                pengaduan.putExtra("PwdNya", userPwd);
                pengaduan.putExtra("FtoNya", userFto);
                pengaduan.putExtra("UrlNya", userUrl);

                startActivity(pengaduan);
            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, dashboard.class);
    }
}
