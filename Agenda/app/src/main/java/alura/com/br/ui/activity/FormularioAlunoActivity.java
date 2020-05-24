package alura.com.br.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import alura.com.br.DAO.AlunoDAO;
import alura.com.br.R;
import alura.com.br.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set view content
        setContentView(R.layout.activity_formulario_aluno);

        // Change the title shown on the app bar
        setTitle("Novo aluno");

        final AlunoDAO dao = new AlunoDAO();

        // Creates views for the layout elements (button and text boxes)
        final EditText campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        final EditText campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        final EditText campoEmail = findViewById(R.id.activity_formulario_aluno_email);
        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);

        // Creates listener for button clicks (get click events)
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get text from the text boxes
                String nome = campoNome.getText().toString();
                String telefone = campoTelefone.getText().toString();
                String email = campoEmail.getText().toString();

                // Classe to store student information
                Aluno alunoCriado = new Aluno(nome, telefone, email);

                // Save new student to the DAO
                dao.salva(alunoCriado);

                // Finish the activity
                finish();
            }
        });
    }
}