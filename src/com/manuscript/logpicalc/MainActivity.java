package com.manuscript.logpicalc;




import java.util.ArrayList;


import com.manuscript.logpicalc.lib.BadSyntaxException;
import com.manuscript.logpicalc.lib.EvalException;
import com.manuscript.logpicalc.lib.Expression;
import com.manuscript.logpicalc.lib.FunctionList;
import com.manuscript.logpicalc.lib.Lexer;
import com.manuscript.logpicalc.lib.ParsingException;
import com.manuscript.logpicalc.lib.PrecedenceList;
import com.manuscript.logpicalc.lib.Token;
import com.manuscript.logpicalc.lib.TokenList;
import com.manuscript.logpicalc.R;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	
	private ListView functionListView;
	private GridView keypad;
	private LinearLayout editor;
	private TextView currentEditor = null;
	private ScrollView topPanel = null;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		
		//CREATE FUNCTION LIST
		
		functionListView = (ListView) findViewById(R.id.functionsListView);
		functionListView.setAdapter(new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1,
				android.R.id.text1,
				FunctionList.list));
		functionListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView view = (TextView) arg1;
				String t = FunctionList.getFunction(view.getText().toString()).toString();
				appendToEditor(t);				
			}
			
		});
		
		keypad = (GridView) findViewById(R.id.keypad);
		keypad.setAdapter(new ButtonAdapter(this));
		
		editor = (LinearLayout) findViewById(R.id.editor);
		
		topPanel = (ScrollView) findViewById(R.id.topPanel);
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_about) {
			showHelp();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void addEditorView(View v){
		editor.addView(v);
		//editor.scrollTo(0, v.getBottom());
		//topPanel.fullScroll(View.FOCUS_DOWN);
		topPanel.post(new Runnable(){
			@Override
			public void run() {
				topPanel.fullScroll(View.FOCUS_DOWN);
			}			
		});
	}
	
	private void appendToEditor(String s){
		if(currentEditor == null){
			currentEditor = new TextView(this);
			currentEditor.setTextSize(20);
			addEditorView(currentEditor);
		}
		
		currentEditor.append(s);
	}
	
	private void setEditorText(String s){
		if(currentEditor == null){
			currentEditor = new TextView(this);
			currentEditor.setTextSize(20);
			addEditorView(currentEditor);
		}
		
		currentEditor.setText(s);
	}
	
	private String getEditorText(){
		if(currentEditor == null) return "";
		return currentEditor.getText().toString();
	}
	
	private void clearEditor(){
		currentEditor = null;
		editor.removeAllViews();
		topPanel.fullScroll(View.FOCUS_UP);
	}
	
	private void showAnswer(String s){
		currentEditor = null;
		TextView ans = new TextView(this);
		ans.setText(s);
		ans.setTextSize(22);
		ans.setTextColor(Color.argb(255, 100, 150, 88));
		addEditorView(ans);
	}
	
	private void showError(String s){
		currentEditor = null;
		TextView err = new TextView(this);
		err.setText(s);
		err.setTextSize(15);
		err.setTextColor(Color.argb(255, 150, 88, 100));
		addEditorView(err);
	}
	
	public void showHelp(){
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
	
	private void parse(){
		String source = currentEditor.getText().toString();
		try{
			ArrayList<Token> tokens = Lexer.lex(source, TokenList.getList());
			
			PrecedenceList parser = new PrecedenceList(tokens);
			
			Expression exp = parser.parse();
			if(exp == null){
				throw new ParsingException("Computation failed. Please verify your syntax.");
			}
			double res = exp.evaluate();
			FunctionList.ans = res;
			
			long ires = (long) res;
			
			if((double) ires == res){
				showAnswer(Long.toString(ires));
				
			}
			else {
				showAnswer(Double.toString(res));
			}
			
			
		}
		catch (BadSyntaxException | ParsingException | EvalException ex){
			showError(ex.getMessage());
		}		
		catch (Exception ex){
			showError("Error");
			ex.printStackTrace();
		}
		
	}
	
	class ButtonAdapter extends BaseAdapter {
		private Context context;
		
		private String keys[] = {
				"ans", "E", "CLS","DEL",
				"(", ")", ",", "^",
				"7", "8", "9", "+",
				"4", "5", "6", "-",
				"1", "2", "3", "*",
				".", "0", "=", "/",		
			};
		
		public ButtonAdapter(Context c){
			context = c;
		}

		@Override
		public int getCount() {
			return keys.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			Button button;
			if(convertView == null){
				button = new Button(context);
			}
			else {
				button = (Button) convertView;
				
			}
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					Button b = (Button) view;
					String t = b.getText().toString();
					if(t.equals("=")){
						if(getEditorText().equals("")){
							setEditorText("ans");
						}
						parse();
						return;
					}
					if(t.equals("/") || t.equals("*") || t.equals("^") || t.equals("+")){
						if(getEditorText().equals("")){
							setEditorText("ans");
						}
					}
					if(t.equals("DEL")){
						String cur = getEditorText();
						if(!cur.equals("")){
							cur = cur.substring(0, cur.length() - 1);
							setEditorText(cur);
						}
						return;
					}
					if(t.equals("CLS")){
						clearEditor();
						return;
					}
					appendToEditor(b.getText().toString());
				}
				
			});
			button.setText(keys[pos]);
			return button;
		}
		
		
	}

}
