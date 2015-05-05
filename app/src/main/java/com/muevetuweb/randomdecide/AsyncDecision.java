package com.muevetuweb.randomdecide;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.view.View;

import java.util.Random;

public class AsyncDecision  extends AsyncTask<Integer, Integer, Integer> {
    private Context mContext;
    private String mClass = "MainActivity";
    public int result = 0;

    public AsyncDecision (Context context,String className){
        mContext = context;
        mClass = className;
    }

    @Override
    protected void onPreExecute() {
        if(mClass.equals("MainActivity")){
            MainActivity.txvResp.setText("");
            MainActivity.txvRespFinal.setText("");
            MainActivity.txvResp.setVisibility(View.INVISIBLE);
            MainActivity.txvRespFinal.setVisibility(View.INVISIBLE);

            MainActivity.ivAnimacion.setVisibility(View.VISIBLE);
            MainActivity.savingAnimation.start();
        }
    }

    @Override
    protected final Integer doInBackground(Integer... params) {
        try {
            Thread.sleep(3000);
            int total   =   params[0];

            Random r = new Random(System.currentTimeMillis());
            result  = r.nextInt(total);
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {    }

    @Override
    protected void onPostExecute(Integer result) {
        String answer;
        if(mClass.equals("MainActivity")){
            MainActivity.ivAnimacion.setVisibility(View.INVISIBLE);
            MainActivity.savingAnimation.stop();

            if(MainActivity.listItems.size()>0) {
                answer   =   MainActivity.listItems.get(result);
                MainActivity.txvResp.setText(mContext.getResources().getString(R.string.decide_ok));
                MainActivity.txvRespFinal.setText(answer);

            }else{
                answer = mContext.getResources().getString(R.string.decide_wrong);
                MainActivity.txvResp.setText("");
                MainActivity.txvRespFinal.setText(answer);

            }
            if (MainActivity.ttobj != null) {
                MainActivity.ttobj.speak(answer, TextToSpeech.QUEUE_FLUSH, null);
            }
            MainActivity.txvResp.setVisibility(View.VISIBLE);
            MainActivity.txvRespFinal.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCancelled() {

    }
}