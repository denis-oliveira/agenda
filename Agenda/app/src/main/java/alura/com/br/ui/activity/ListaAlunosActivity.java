package alura.com.br.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    // Class to store the list of students
    private final AlunoDAO dao = new AlunoDAO();
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

    private void configuraFabNovoAluno() {
        // get fab for new student button
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
        // Get view of the List View created in the layout using its ID
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        // Set up the ListView adapter
        configuraAdapter(listaDeAlunos);
        // Set up listener of ListView item click
        configuraListenerDeCliquePorItem(listaDeAlunos);

        configuraListenerDeCliqueLongoPorItem(listaDeAlunos);
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

    private void configuraListenerDeCliqueLongoPorItem(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(
                    AdapterView<?> adapterView,
                    View view,
                    int posicao,
                    long id) {

                Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(posicao);

                remove(alunoEscolhido);
                // false return indicates you will not consume the event and wil allow other events
                // to happen (i.e. normal click). true returns indicate you will consume the event.
                return true;
            }
        });
    }

    private void remove(Aluno aluno) {
        // Removes student from the DAO
        dao.remove(aluno);
        // Removes student from ListView adapter view
        adapter.remove(aluno);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Removes all elements from the list
        atualizaViewDeAlunos();
    }

    private void atualizaViewDeAlunos() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

}
