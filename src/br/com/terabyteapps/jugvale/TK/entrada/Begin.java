package br.com.terabyteapps.jugvale.TK.entrada;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.com.terabyteapps.jugvale.TK.R;
import br.com.terabyteapps.jugvale.TK.dao.AvaliacaoDAO;
import br.com.terabyteapps.jugvale.TK.modelo.Avaliacao;
import br.com.terabyteapps.jugvale.TK.principal.Principal;
import br.com.terabyteapps.jugvale.TK.conexao.ConexaoHttpClient;;

public class Begin extends Activity {
	
	Button start, random;
	int sair,tam,IdS,IdR;
	private static AvaliacaoHelper helper;

	static String[] out;
	
	private static List<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin);
        
        Intent intent = getIntent();
        try {
        	final int paradaS = (int) intent
        			.getIntExtra("paradaS", 0);
        	IdS = paradaS;
        	final int paradaR = (int) intent
        			.getIntExtra("paradaR", 0);
        	IdR = paradaR;
        } catch (Exception e) {
		}
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy); 
    	
        helper = new AvaliacaoHelper(this);
        
        AvaliacaoDAO dao = new AvaliacaoDAO(this);
        avaliacoes = dao.getLista();
		tam = avaliacoes.size();
		Log.i("tam", tam + "");
		dao.close();
		
		carregarDadosAtivos(this);

        start=(Button)findViewById(R.id.start);
        random=(Button)findViewById(R.id.random);
        
        start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (IdS!=0) {
					questionario();
				} else{
//				int z=1;
//				if (z==1) {
					iniciar(1,false,IdS);
				}
				
				
			}
		});
        
        random.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				iniciar(2,true,IdR);
				
			}
		});
    }
    
    protected void questionario() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(Begin.this);

		builder.setTitle("Restart?");

		builder.setNeutralButton("Yes", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				IdS=0;
				iniciar(1,false,IdS);
			}
		});

		builder.setPositiveButton("No",
			new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				iniciar(1,false,IdS);
			}
		});

		builder.create().show();
		
	}

	public static void carregarDadosAtivos(Context context) {
		String resposta = "";
		String urlGet = "http://www.surferweb.com.br/testes/testKing/conexao_2.php";
		String respostaRetornada = null;
		try {
			respostaRetornada = ConexaoHttpClient.executaHttpGet(urlGet);
			resposta = respostaRetornada.toString();
			Log.i("logar", "resposta = " + resposta);
//			resposta = resposta.replaceAll("\\s+", "");
			if (resposta.equals(0)) {
				Toast.makeText(context, "Não entrou ", Toast.LENGTH_LONG)
						.show();
			} else {
				
			}
		} catch (Exception erro) {
			Log.i("erro", "erro = " + erro);
		}
//		Log.i("logar", "resposta = " + resposta.toString());
		out = resposta.split("!");
//		Log.i("logar", "resposta = " + out[0].toString() + " " + out.length);
		String frase = "";
		if (out.length == 1) {
			Toast.makeText(context, "Sem acesso a rede de dados",
					Toast.LENGTH_LONG).show();
		} else {
			for (int i = 0; i < out.length-1; i++) {

				String[] out2 = out[i].split("#");
				
				Avaliacao avaliacao = helper.pegaAlunoDoFormulario();
				AvaliacaoDAO dao = new AvaliacaoDAO(context);

//				long id = Integer.valueOf(out2[0]);
				String aux = out2[0];
				avaliacao.setId(Long.valueOf(aux));
				avaliacao.setNroOpc(Integer.valueOf(out2[1]));
				avaliacao.setRes(Integer.valueOf(out2[2]));
				avaliacao.setQuestao(out2[3].toString());
				avaliacao.setOp(out2[4].toString());
				avaliacao.setCorreto(out2[5].toString());

				try {
					if (avaliacoes.get(i) == avaliacao) {
						dao.salva(avaliacao);
					} else {
						dao.altera(avaliacao);
					}
				} catch (Exception e) {
					dao.salva(avaliacao);
				}
				
				dao.close();
			}
		}
		Log.i("frase", "" + frase);

	}
    
	protected void iniciar(int j,boolean rand,int id) {
		Intent i = new Intent();
		i.setClass(Begin.this, Principal.class);
		i.putExtra("j", j);
		if (rand) {
			float valor=0;
			for (int k = 0; k < 2; k++) {
				valor=(float) (Math.round((Math.random())*100));
				if (valor>tam-1) {
					k=0;
				}else{
					k=1;
				}
			}
			int aux = (int) valor;
			i.putExtra("valor", aux);
		}
		if (j==1) {
			i.putExtra("parada", id);
		}
		startActivity(i);
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_begin, menu);
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
        return super.onOptionsItemSelected(item);
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {// Intent intent = new

			if (sair == 0) {
				Toast.makeText(Begin.this,
						"Pressione novamente para sair do aplicativo",
						Toast.LENGTH_LONG).show();
				sair++;
			} else if (sair == 1) {
				sair = 0;
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
			return false;

		}
		return false;

	}

}
