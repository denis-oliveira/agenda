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
    private Aluno aluno;

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
        this.aluno = (Aluno) dados.getSerializableExtra("aluno");
        this.campoNome.setText(this.aluno.getNome());
        this.campoTelefone.setText(this.aluno.getTelefone());
        this.campoEmail.setText(this.aluno.getEmail());
    }

    private void inicializacaoDosCampos() {
        this.campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        this.campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        this.campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);

        // Creates listener for button clicks (get click events)
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                // Create a student
//                Aluno alunoCriado = preencheAluno();
//
//                // Save student to the DAO and finish Activity
//                salva(alunoCriado);
                preencheAluno();
                dao.edita(aluno);
                // Finish the activity
                finish();
            }
        });
    }

    private void preencheAluno() {
        // Get text from the text boxes
        String nome = this.campoNome.getText().toString();
        String telefone = this.campoTelefone.getText().toString();
        String email = this.campoEmail.getText().toString();

        this.aluno.setNome(nome);
        this.aluno.setTelefone(telefone);
        this.aluno.setEmail(email);
    }

    private void salva(Aluno aluno) {
        // Save new student to the DAO
        this.dao.salva(aluno);
        // Finish the activity
        finish();
    }
}