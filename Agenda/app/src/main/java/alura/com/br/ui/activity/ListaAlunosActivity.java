package alura.com.br.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import alura.com.br.DAO.AlunoDAO;
import alura.com.br.R;
import alura.com.br.model.Aluno;

import static alura.com.br.ui.activity.ConstantesActivities.CHAVE_ALUNO;

// Using AppCompatActivity to load App Bar in the app and give support to older versions of Android
public class ListaAlunosActivity extends AppCompatActivity {

    private static final String TITULO_APP_BAR = "Lista de Alunos";
    private final AlunoDAO dao = new AlunoDAO(); // Class to store the list of students
    private ArrayAdapter<Aluno> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Run super class onCreate() method (it is mandatory to do that)
        super.onCreate(savedInstanceState);

        // Load the layout
        setContentView(R.layout.activity_lista_alunos);
        // Change the title shown on the app bar
        setTitle(TITULO_APP_BAR);
        // Set up FAB (floating action button) for new student
        configuraFabNovoAluno();
        // Set up list view 
        configuraLista();
        // Create students instances to be shown on main view
        dao.salva(new Aluno(
                "Dênis Silva Oliveira",
                "+55 47 99683-6675",
                "dns.oliv@gmail.com"));
        dao.salva(new Aluno(
                "Nadia Silva Oliveira",
                "+55 19 3879-2656",
                "nadia@gmail.com"));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Remover");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
        remove(alunoEscolhido);
        return super.onContextItemSelected(item);
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
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1);
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
        // Removes all elements from the ListView adapter
        adapter.clear();
        // Adds all elements to the ListView adapter
        adapter.addAll(dao.todos());
    }

}
