package com.muevetuweb.randomdecide;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    private ListView list;
    private Button btnAdd,btnRandomize;
    private EditText addText;
    private TextView txvResp;

    private ImageView ivAnimacion;
    private AnimationDrawable savingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivAnimacion = (ImageView)findViewById(R.id.iv_animacion);
        ivAnimacion.setBackgroundResource(R.drawable.animacion);
        savingAnimation = (AnimationDrawable)ivAnimacion.getBackground();
        txvResp = (TextView)findViewById(R.id.txvRespuesta);

        ivAnimacion.setVisibility(View.INVISIBLE);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);

        list = (ListView) findViewById(R.id.listView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRandomize = (Button) findViewById(R.id.btnRandomize);
        addText = (EditText) findViewById(R.id.editText);
        list.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItems(view);
            }
        });
        btnRandomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txvResp.setText("");
                txvResp.setVisibility(View.INVISIBLE);

                ivAnimacion.setVisibility(View.VISIBLE);
                savingAnimation.start();

                int resp = decidiendo(listItems);

                ivAnimacion.setVisibility(View.INVISIBLE);
                savingAnimation.stop();

                String dato = listItems.get(resp);
                //Toast.makeText(getApplicationContext(),"el elegido fue " + dato,Toast.LENGTH_LONG).show();
                txvResp.setText(dato);
                txvResp.setVisibility(View.VISIBLE);
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long arg3) {
                listItems.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    public void addItems(View v) {
        String nuevo = addText.getText().toString();
        if(!nuevo.isEmpty()){
            listItems.add(nuevo);
            adapter.notifyDataSetChanged();

            addText.setText("");
            addText.requestFocus();
        }
        adapter.notifyDataSetChanged();
    }

    public int decidiendo(ArrayList<String> lista) {
        try {
            int total   =   lista.size();
            Random r = new Random(System.currentTimeMillis());
            int resp = r.nextInt(total);
            Log.e("TAG","->"+resp);
            Thread.sleep(4000);
            return resp;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            return 0;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
