package occupi.occupi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.search:
            goSearch();
            return(true);
        case R.id.login:
            goLogin();
            return(true);
        case R.id.rally:
            goRally();
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }
    public void goSearch(){
        Intent intent = new Intent (this, Search.class);
        startActivity(intent);
    }

    public void goLogin(){
        Intent intent = new Intent (this, Login.class);
        startActivity(intent);
    }

    public void goRally(){
        Intent intent = new Intent (this, Rally.class);
        startActivity(intent);
    }
}
