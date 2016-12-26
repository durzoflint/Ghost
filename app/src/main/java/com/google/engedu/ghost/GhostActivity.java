package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class GhostActivity extends ActionBarActivity
{
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private FastDictionary fastdictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    String s="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        onStart(null);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            fastdictionary = new FastDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        char key = (char) event.getUnicodeChar();
        if((key>=65&&key<=90)||(key>=97&&key<=122))
        {
            TextView t = (TextView) findViewById(R.id.ghostText);
            s = t.getText().toString() + key;
            t.setText(s);
            TextView t1 = (TextView) findViewById(R.id.gameStatus);
            t1.setText("Computer Turn");
            computerTurn();
        }
        return false;
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void computerTurn()
    {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        TextView text = (TextView) findViewById(R.id.ghostText);
        String s=text.getText().toString();
        if(fastdictionary.isWord(s)) {
            label.setText("Computer Wins!!!!");
        }
        else {
            String x = fastdictionary.getGoodWordStartingWith(s);
            if (x == null)
                label.setText("No Such word. Computer Wins!!!!Null");
            else {
                s = s + x.charAt(s.length());
                text.setText(s);
                userTurn = true;
                label.setText(USER_TURN);
            }
        }
    }

    public void challenged(View v)
    {
        TextView t = (TextView) findViewById(R.id.ghostText);
        String x = t.getText().toString();
        TextView label = (TextView) findViewById(R.id.gameStatus);
        String st=fastdictionary.getGoodWordStartingWith(x);
        if(fastdictionary.isWord(x))
            label.setText("User Wins!!!!");
        else if(st==null)
            label.setText("No Such Word. User Wins!!!!");
        else if(st!=null)
            label.setText("Word Exists. "+st+". Computer Wins!!!!");
    }
    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        label.setText(USER_TURN);
        return true;
    }
}
