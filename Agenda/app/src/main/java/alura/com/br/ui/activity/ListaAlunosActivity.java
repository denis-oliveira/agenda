package alura.com.br.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import alura.com.br.DAO.AlunoDAO;
import alura.com.br.R;
import alura.com.br.model.Aluno;
import alura.com.br.ui.adapter.ListaAlunosAdapter;

import static alura.com.br.ui.activity.ConstantesActivities.CHAVE_ALUNO;

// Using AppCompatActivity to load App Bar in the app and give support to older versions of Android
public class ListaAlunosActivity extends AppCompatActivity {

    private static final String TITULO_APP_BAR = "Lista de Alunos";
    private final AlunoDAO dao = new AlunoDAO(); // Class to store the list of students
    private ListaAlunosAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Run super class onCreate() method (it is mandatory to do that)
        super.onCreate(savedInstanceState);

        // Load the layout
        setContentView(R.layout.activity_lista_alunos);
        // Change the title shown on the app bar
        setTitle(TITULO_APP_BAR);
        // Set up app bar (action bar) color
        configuraCorDaAppBar();
        // Set up FAB (floating action button) for new student
        configuraFabNovoAluno();
        // Set up list view 
        configuraLista();
    }

    private void configuraCorDaAppBar() {
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        // Define ColorDrawable object and parse color using parseColor method with color hash code
        // as its parameter
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#CF1A0D"));
        // Se BackgroundDrawwable
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    private void remove(Aluno aluno) {
        // Removes student from the DAO
        dao.remove(aluno);
        // Removes student from ListView adapter view
        adapter.remove(aluno);
    }

    private void configuraFabNovoAluno() {
        // Gets FAB for new student addition button
        FloatingActionButton botaoNovoAluno =
                findViewById(R.id.activity_lista_alunos_fab_novo_aluno);

        // Creates listener for button clicks (get click events)
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreFormularioModoInsereAluno();
            }
        });
    }

    private void abreFormularioModoInsereAluno() {
        startActivity(new Intent(
                ListaAlunosActivity.this,
                FormularioAlunoActivity.class));
    }

    private void configuraLista() {
        // Gets view of the List View created in the layout using its ID
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        // Set up the ListView adapter
        configuraAdapter(listaDeAlunos);
        // Set up listener of ListView item click
        configuraListenerDeCliquePorItem(listaDeAlunos);
        // Register context menu for ListView
        registerForContextMenu(listaDeAlunos);
    }

    private void configuraAdapter(ListView listaDeAlunos) {
        adapter = new ListaAlunosAdapter(this);
        listaDeAlunos.setAdapter(adapter);
    }

    private void configuraListenerDeCliquePorItem(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(posicao);
                abreFormularioModoEditaAluno(alunoEscolhido);
            }
        });
    }

    private void abreFormularioModoEditaAluno(Aluno aluno) {
        Intent vaiParaFormularioActivity = new Intent(
                this,
                FormularioAlunoActivity.class);
        vaiParaFormularioActivity.putExtra(CHAVE_ALUNO, aluno);
        startActivity(vaiParaFormularioActivity);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Updates ListView adapter with updated students list
        atualizaAlunos();
    }

    private void atualizaAlunos() {
        // Removes all elements from the ListView adapter and then adds all elements to the
        // ListView adapter
        adapter.atualiza(dao.todos());
    }

    // Creates context menu for long click
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    // Handles context menu option click
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_lista_alunos_menu_remover) {
            AdapterView.AdapterContextMenuInfo menuInfo =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
            remove(alunoEscolhido);
        }
        return super.onContextItemSelected(item);
    }

}
