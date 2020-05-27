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

import static alura.com.br.ui.activity.ConstantesActivities.CHAVE_ALUNO;

public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITULO_APP_BAR_NOVO_ALUNO = "Novo Aluno";
    private static final String TITULO_APP_BAR_EDITA_ALUNO = "Edita Aluno";
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
        // Creates views for the layout text boxes
        inicializacaoDosCampos();
        // Set up save button and handle click events
        configuraBotaoSalvar();
        // Load student information
        carregaAluno();
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
                finalizaFormulario();
            }
        });
    }

    private void finalizaFormulario() {
        // Fill up student attribute with information of the text boxes
        preencheAluno();
        // Check if student ID is valid
        if(aluno.temIdValido()) {
            // Edit an existing student
            dao.edita(aluno);
        } else {
            // Save a new student
           dao.salva(aluno);
        }
        // Finish the activity
        finish();
    }

    // Fill up student attribute with information of the text boxes
    private void preencheAluno() {
        // Get text from the text boxes
        String nome = this.campoNome.getText().toString();
        String telefone = this.campoTelefone.getText().toString();
        String email = this.campoEmail.getText().toString();

        // Fill up student attribute with information of the text boxes
        this.aluno.setNome(nome);
        this.aluno.setTelefone(telefone);
        this.aluno.setEmail(email);
    }

    private void carregaAluno() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_ALUNO)) {
            // Change the title shown on the app bar
            setTitle(FormularioAlunoActivity.TITULO_APP_BAR_EDITA_ALUNO);
            this.aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            preencheCampos();
        } else {
            // Change the title shown on the app bar
            setTitle(FormularioAlunoActivity.TITULO_APP_BAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

    private void preencheCampos() {
        this.campoNome.setText(this.aluno.getNome());
        this.campoTelefone.setText(this.aluno.getTelefone());
        this.campoEmail.setText(this.aluno.getEmail());
    }
}

