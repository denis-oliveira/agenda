package alura.com.br;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Using AppCompatActivity to load App Bar in the app and give support to older versions of Android
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Run super class onCreate() method (it is mandatory to do that)
        super.onCreate(savedInstanceState);
        // Load the layout
        setContentView(R.layout.activity_main);
        // Change the title shown on the app bar
        setTitle("Lista de Alunos");
        // Create collection of students
        List<String> alunos = new ArrayList<>(
                Arrays.asList("Alex", "Fran", "José", "Maria", "Ana"));
        // Get view of the List View created in the layout using its ID
        ListView listaDeAlunos = findViewById(R.id.activity_main_lista_de_alunos);
        // Load the list of students in the ListView
        listaDeAlunos.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                alunos));
    }
}
