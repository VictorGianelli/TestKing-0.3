package br.com.terabyteapps.jugvale.TK.principal;

import java.util.ArrayList;
import java.util.List;

import br.com.terabyteapps.jugvale.TK.R;
import br.com.terabyteapps.jugvale.TK.dao.AvaliacaoDAO;
import br.com.terabyteapps.jugvale.TK.entrada.Begin;
import br.com.terabyteapps.jugvale.TK.modelo.Avaliacao;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends Activity {

	TextView test,tentativas;
//	String resp;
	CheckBox radioA,radioB,radioC,radioD,radioE,radioF,radioG,radioH;
	Button answer,next;
	int Id=0;
	int resp,valor,J,Rand,Par; 
	int tr=0, proximo=0,mais=0;
	int tam,l;
	String i,j;
	private static List<Avaliacao> teste = new ArrayList<Avaliacao>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        try {
        	final int j = (int) intent
    			.getIntExtra("j", 0);
        	J = j;
        	
        	final int resultado = (int) intent
        			.getIntExtra("resultado", 0);
        	Id = resultado;
        	
        	final int rand = (int) intent
        			.getIntExtra("valor", 0);
        	Rand = rand;
        	
        	final int parada = (int) intent
        			.getIntExtra("parada", 0);
        	Par = parada;
        	
        } catch (Exception e) {
        	Id = 0;
		}
        

        AvaliacaoDAO dao = new AvaliacaoDAO(this);
        teste = dao.getLista();
		tam = teste.size();
		Log.i("tam", tam + "");
		dao.close();
		
		if (J==1) {
			Id=Par;
		} else if (J==2) {
			Id=Rand;
		}

		i = String.valueOf(teste.get(Id).getRes());
		
    	Log.i("Id", Id+"");
    	Log.i("teste getId", teste.get(Id).getId()+" ");
    	Log.i("teste getNroOpc", teste.get(Id).getNroOpc()+" ");
    	Log.i("teste getRes", teste.get(Id).getRes()+" ");
    	Log.i("teste getQuestao", teste.get(Id).getQuestao()+" ");
    	Log.i("teste getOp", teste.get(Id).getOp()+" ");
    	Log.i("teste getCorreto", teste.get(Id).getCorreto()+" ");
    	//////////////////////////////////////////////////////////////////////////
    	Long id = teste.get(Id).getId();
    	int nroOp=teste.get(Id).getNroOpc();
    	String questao2=teste.get(Id).getQuestao();
    	questao2.replaceAll("dq", " ");
    	String questao=questao2;
    	
    	String op=teste.get(Id).getOp();
    	
    	final int res=teste.get(Id).getRes();
    	///////////////////////////////////////////////////////////////////
    	setTitle("Question No: "+id);

    	if (nroOp==8) {
    		setContentView(R.layout.alternativas_8);
		} else if (nroOp==7) {
    		setContentView(R.layout.alternativas_7);
		} else if (nroOp==6) {
    		setContentView(R.layout.alternativas_6);
		} else if (nroOp==5) {
    		setContentView(R.layout.alternativas_5);
		} else if (nroOp==4) {
    		setContentView(R.layout.alternativas_4);
		}
    	
    	radioA=(CheckBox)findViewById(R.id.radioA);
        radioB=(CheckBox)findViewById(R.id.radioB);
        radioC=(CheckBox)findViewById(R.id.radioC);
        radioD=(CheckBox)findViewById(R.id.radioD);
        radioE=(CheckBox)findViewById(R.id.radioE);
        radioF=(CheckBox)findViewById(R.id.radioF);
        radioG=(CheckBox)findViewById(R.id.radioG);
        radioH=(CheckBox)findViewById(R.id.radioH);
    	
        test=(TextView)findViewById(R.id.test);
        tentativas=(TextView)findViewById(R.id.tentativas);
        answer = (Button)findViewById(R.id.answer);
        
        String [] vari = new String [nroOp];
        vari=op.split("@");
        String radA=vari[0].toString();
        String radB=vari[1].toString();
        String radC=vari[2].toString();
        String radD=vari[3].toString();
        String radE,radF,radG,radH;
        try {
        	radE=vari[4].toString();
		} catch (Exception e) {
			radE="";
		}
        try {
        	radF=vari[5].toString();
		} catch (Exception e) {
			radF="";
		}
        try {
        	radG=vari[6].toString();
		} catch (Exception e) {
			radG="";
		}
        try {
        	radH=vari[7].toString();
		} catch (Exception e) {
			radH="";
		}
        
        test.setText(questao);
        radioA.setText(radA);
        radioB.setText(radB);
        radioC.setText(radC);
        radioD.setText(radD);
        try {
        	radioE.setText(radE);
        	radioF.setText(radF);
        	radioG.setText(radG);
        	radioH.setText(radH);
		} catch (Exception e) {
			
		}
        
        next=(Button)findViewById(R.id.next);
        
//      next.setVisibility(View.GONE);
        
        next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				proximo=0;
				mais=0;
				Id++;
				if (Id>tam-1&&J==1) {
					termino();
				}else{
					Tela1();
				}
			}
		});
        answer.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				resposta(res);
				
				if (mais==1) {
					--mais;
					--proximo;
					++Id;
					Log.i("Id tam", Id+">"+tam);
					if (Id>tam-1&&J==1) {
						termino();
					}else{
						Tela1();
					}
					
				}
				
				if (proximo==1 && mais==0) {
					Toast.makeText(Principal.this, "Great!!!", Toast.LENGTH_SHORT).show();
					next.setVisibility(View.GONE);
					proximo=0;
					mais++;
					answer.setText("Next");
				}
				
				
				
			}
		});
    }
    
   

    protected void Tela1() {
    	Par+=1;
    	Intent i = new Intent();
        i.setClass(Principal.this, Principal.class);
        i.putExtra("j", J);
        if (J==2) {
			float valor=0;
			for (int k = 0; k < 2; k++) {
				valor=(float) (Math.round((Math.random())*100));
				if (valor>tam-1) {
					k=0;
				}else{
					k=1;
				}
				if (valor==Par) {
					k=0;
				}
			}
			int aux = (int) valor;
			i.putExtra("valor", aux);
		}
        i.putExtra("resultado", Id);
		i.putExtra("parada", Par);
        startActivity(i);
		
	}



	protected void resposta(int res) { // nro de alternativas ja assinaladas
		int x = i.length();// nro de alternativas ja respondidas
    	resp = 0; // valor das alternativas assinaladas
    	valor = 0; // nro de alternativas
		if (radioA.isChecked()) {
			incremento(1);
		}
		if (radioB.isChecked()) {
			incremento(2);
		}
		if (radioC.isChecked()) {
			incremento(3);
		}
		if (radioD.isChecked()) {
			incremento(4);
		}
		try {
			if (radioE.isChecked()) {
				incremento(5);
			}			
			if (radioF.isChecked()) {
				incremento(6);
			}
			if (radioG.isChecked()) {
				incremento(7);
			}
			if (radioH.isChecked()) {
				incremento(8);
			}
		} catch (Exception e) {
			
		}
		
		Log.i("resp", resp+"");
		Log.i("res", res+"");
		
		if (resp==0){
			Toast.makeText(Principal.this, "None answer selected", Toast.LENGTH_SHORT).show();
		} else {
			if (res==resp) {
				proximo=1;
//				Toast.makeText(Principal.this, "Acertou!!!", Toast.LENGTH_SHORT).show();
			}  else if (x==valor && res!=resp) {
				Toast.makeText(Principal.this, "Wrong", Toast.LENGTH_SHORT).show();
			} else{
				Toast.makeText(Principal.this, "Wrong nunber of answer", Toast.LENGTH_SHORT).show();
			}
		} 
		
		
	}
	private void incremento(int i) {
		valor++;
		if (resp==0) {
			resp+=i;
		} else{
			resp=resp*10;
			resp+=i;
		}
		
	}
	
	protected void termino() {
		AlertDialog.Builder builder = new AlertDialog.Builder(Principal.this);

		builder.setTitle("Congratulations! You have completed the questions !");

		builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent();
				  i.setClass(Principal.this, Begin.class);
				  startActivity(i);
			}
		});

		builder.create().show();

	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		  if (keyCode == KeyEvent.KEYCODE_BACK) {//Intent intent = new
			  Intent i = new Intent();
			  i.setClass(Principal.this, Begin.class);
			  i.putExtra("paradaS", Par);
			  startActivity(i);
		  return false; 
		  
	  	} 
	  return false;
	  
	  }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        
        switch (item.getItemId()) {
		case R.id.resposta:{
			
			if (i.length()==1) {
				espaco(1);
			} else if (i.length()==2) {
				espaco(2);
			}else if (i.length()==3) {
				espaco(3);
			}
			Toast.makeText(Principal.this,
								"" + j,
								Toast.LENGTH_LONG).show();
		}
			break;

		default:
			break;
		}
        return super.onOptionsItemSelected(item);
    }
	private void espaco(int z) {
		j = teste.get(Id).getRes()+"";
		if (z==1) {
			i=j.charAt(0)+"";	
		} else if (z==2) {
			i=j.charAt(0)+" " + j.charAt(1);	
		} else if (z==3) {
			i=j.charAt(0)+" " + j.charAt(1)+" " + j.charAt(2);		
		}
		String [] aa = new String[z];
		aa=i.split(" ");
		
		for (int k = 0; k < z; k++) {
		
			if (k>0) {
				j+=", ";
			} else{
				j="";
			}
			
			switch (aa[k]) {
			case "1":
				j+="A";	
				break;
			case "2":
				j+="B";	
				break;
			case "3":
				j+="C";	
				break;
			case "4":
				j+="D";	
				break;
			case "5":
				j+="E";	
				break;
			case "6":
				j+="F";	
				break;
			case "7":
				j+="G";	
				break;
			case "8":
				j+="H";	
				break;
				
			default:
				break;
			}
		}
	}
}
