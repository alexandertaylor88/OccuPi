package occupi.occupi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;


public class Browse extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    public void goSearch(View view) {
        Intent intent = new Intent (this, Search.class);
        startActivity(intent);
    }

}
