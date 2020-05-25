package alura.com.br.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import alura.com.br.DAO.AlunoDAO;
import alura.com.br.R;
import alura.com.br.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITULO_APP_BAR = "Informações do Aluno";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private final AlunoDAO dao = new AlunoDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set view content
        setContentView(R.layout.activity_formulario_aluno);

        // Change the title shown on the app bar
        setTitle(TITULO_APP_BAR);

        // Creates views for the layout text boxes
        inicializacaoDosCampos();

        // Set up save button and handle click events
        configuraBotaoSalvar();

        Intent dados = getIntent();
        Aluno aluno = (Aluno) dados.getSerializableExtra("aluno");
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoEmail.setText(aluno.getEmail());
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);

        // Creates listener for button clicks (get click events)
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a student
                Aluno alunoCriado = criaAluno();

                // Save student to the DAO
                salva(alunoCriado);
            }
        });
    }

    private Aluno criaAluno() {
        // Get text from the text boxes
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        // Classe to store student information
        return new Aluno(nome, telefone, email);
    }

    private void salva(Aluno aluno) {
        // Save new student to the DAO
        dao.salva(aluno);

        // Finish the activity
        finish();
    }
}