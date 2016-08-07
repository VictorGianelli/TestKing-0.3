package br.com.terabyteapps.jugvale.TK.entrada;


import br.com.terabyteapps.jugvale.TK.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class SplashScreen extends Activity{
   
   private Thread mSplashThread; 
   private boolean mblnClicou = false,mblnAux = false;

   /** Evento chamado quando a activity é executada pela primeira vez */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.splashscreen1);
       Log.i("log", "entrou");
   
       //thread para mostrar uma tela de Splash
       mSplashThread = new Thread() {
           @Override
           public void run() {
            try {
                   synchronized(this){
                	   wait();
//                	   wait(1000);
//                       mblnAux = true;
                   
                   }
               }
               catch(InterruptedException ex){                    
               }
                
               if (mblnAux){
                    
                   
                //Carrega a Activity Principal
                Intent i = new Intent();
                //i.setClass(SplashScreen.this, SplashScreen2.class);
                i.setClass(SplashScreen.this, Begin.class);
                startActivity(i);
               }
           }
       };
        
       mSplashThread.start();
       
       
   }
   
   
    
   @Override
   public void onPause()
   {
       super.onPause();
        
       //garante que quando o usuário clicar no botão
       //"Voltar" o sistema deve finalizar a thread
       mSplashThread.interrupt();
   }
   
   public boolean onKeyDown(int keyCode, KeyEvent event) {
		  if (keyCode == KeyEvent.KEYCODE_BACK) {//Intent intent = new
			
		  return false; 
		  
	  	} 
	  return false;
	  
	  }
    
   @Override
   public boolean onTouchEvent(MotionEvent event) {
       if (event.getAction() == MotionEvent.ACTION_DOWN) {
           //o método abaixo está relacionado a thread de splash
        synchronized(mSplashThread){
        	mblnAux = true;
          
            //o método abaixo finaliza o comando wait
            //mesmo que ele não tenha terminado sua espera
               mSplashThread.notifyAll();
           }            
       }
       return true;
   }

}
