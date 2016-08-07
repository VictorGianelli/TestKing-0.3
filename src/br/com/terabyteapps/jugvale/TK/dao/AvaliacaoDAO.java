package br.com.terabyteapps.jugvale.TK.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.terabyteapps.jugvale.TK.modelo.Avaliacao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AvaliacaoDAO extends SQLiteOpenHelper {

	private static final String DATABASE = "BancoAlunos";
	private static final int VERSAO = 1;
	
	public AvaliacaoDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db2) {
		String ddl = "CREATE TABLE Avaliacao (id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "nroOpc INTEGER NOT NULL,res INTEGER NOT NULL,questao TEXT NOT NULL," +
				" op TEXT NOT NULL, correto TEXT);";

		db2.execSQL(ddl);
	}

	public void salva(Avaliacao avaliacao) {
		ContentValues values = new ContentValues();

		Log.i("salva", "id=" + avaliacao.getId());
		values.put("id", avaliacao.getId());
		values.put("nroOpc", avaliacao.getNroOpc());
		values.put("res", avaliacao.getRes());
		values.put("questao", avaliacao.getQuestao());
		values.put("op", avaliacao.getOp());
		values.put("correto", avaliacao.getCorreto());

		try {
			getWritableDatabase().insert("Avaliacao", null, values);
		} catch (Exception e) {
			values.put("id", avaliacao.getId());
		}

	}


	@Override
	public void onUpgrade(SQLiteDatabase db2, int oldVersion, int newVersion) {
		String ddl = "DROP TABLE IF EXISTS Avaliacao;";
		db2.execSQL(ddl);

		this.onCreate(db2);
	}

	public List<Avaliacao> getLista() {
		String[] colunas = { "id","nroOpc","res","questao", "op", "correto" };

		Cursor cursor = getWritableDatabase().query("Avaliacao", colunas, null,
				null, null, null, null);

		ArrayList<Avaliacao> avaliacaos = new ArrayList<Avaliacao>();

		int i=0;
		
		while (cursor.moveToNext()) {

			Avaliacao avaliacao = new Avaliacao();

			avaliacao.setId(cursor.getLong(0));
			avaliacao.setNroOpc(cursor.getInt(1));
			avaliacao.setRes(cursor.getInt(2));
			avaliacao.setQuestao(cursor.getString(3));
			avaliacao.setOp(cursor.getString(4));
			avaliacao.setCorreto(cursor.getString(5));

			avaliacaos.add(avaliacao);
			i++;
		}
		Log.i("avaliacaos.size"+i, avaliacaos.size() + " ");
		int tam = avaliacaos.size();

		String nomes[] = new String[tam];

		return avaliacaos;
	}
	
	public void deletar(Avaliacao avaliacao) {
		try {
			String[] args = { avaliacao.getId().toString() };
			Log.i("nro", avaliacao.getId().toString() + " " + args.toString());
			getWritableDatabase().delete("Avaliacao", "id=?", args);
		} catch (Exception e) {
			Log.i("Exp", e + "");
		}
	}

	public void altera(Avaliacao avaliacao) {
		ContentValues values = new ContentValues();
		
		values.put("id", avaliacao.getId());
		values.put("nroOpc", avaliacao.getNroOpc());
		values.put("res", avaliacao.getRes());
		values.put("questao", avaliacao.getQuestao());
		values.put("op", avaliacao.getOp());
		values.put("correto", avaliacao.getCorreto());
		

		String[] args = { avaliacao.getId().toString() };
		getWritableDatabase().update("Avaliacao", values, "id=?", args);
		Log.i("test", "id=" + avaliacao.getId());
	}
	
	
}
