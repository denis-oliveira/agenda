package alura.com.br.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import alura.com.br.DAO.AlunoDAO;
import alura.com.br.R;

// Using AppCompatActivity to load App Bar in the app and give support to older versions of Android
public class ListaAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Run super class onCreate() method (it is mandatory to do that)
        super.onCreate(savedInstanceState);

        // Load the layout
        setContentView(R.layout.activity_lista_alunos);

        // Change the title shown on the app bar
        setTitle("Lista de Alunos");

        // get fab for new student button
        FloatingActionButton botaoNovoAluno =
                findViewById(R.id.activity_lista_alunos_fab_novo_aluno);

        // Creates listener for button clicks (get click events)
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(
                        ListaAlunosActivity.this,
                        FormularioAlunoActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Class to store the list of students
        AlunoDAO dao = new AlunoDAO();

        // Get view of the List View created in the layout using its ID
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);

        // Load the list of students in the ListView
        listaDeAlunos.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                dao.todos()));
    }
}
