package com.muevetuweb.randomdecide;

import android.graphics.drawable.AnimationDrawable;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {
    public static ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> adapter;

    public static ListView list;
    public static Button btnAdd,btnRandomize;
    public static EditText addText;
    public static TextView txvResp,txvRespFinal;

    public static ImageView ivAnimacion;
    public static AnimationDrawable savingAnimation;
    public static TextToSpeech ttobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivAnimacion = (ImageView)findViewById(R.id.iv_animacion);
        ivAnimacion.setBackgroundResource(R.drawable.animacion);
        savingAnimation = (AnimationDrawable)ivAnimacion.getBackground();
        txvResp = (TextView)findViewById(R.id.txvRespuesta);
        txvRespFinal = (TextView)findViewById(R.id.txvRespFinal);

        ivAnimacion.setVisibility(View.INVISIBLE);

        adapter=new ArrayAdapter<>(this,
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
                AsyncDecision respuesta = new AsyncDecision(getApplicationContext(),"MainActivity");
                respuesta.execute(listItems.size());
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

        ttobj=new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR){
                            Locale loc = new Locale ("spa", "ESP");
                            ttobj.setLanguage(loc);
                        }
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
