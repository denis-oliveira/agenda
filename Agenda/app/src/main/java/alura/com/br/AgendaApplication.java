package alura.com.br;

import android.app.Application;

import alura.com.br.DAO.AlunoDAO;
import alura.com.br.model.Aluno;

public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        criaAlunosDeTeste();
    }

    private void criaAlunosDeTeste() {
        AlunoDAO dao = new AlunoDAO();
        // Create students instances to be shown on main view
        dao.salva(new Aluno(
                "Dênis Silva Oliveira",
                "+55 47 99683-6675",
                "dns.oliv@gmail.com"));
        dao.salva(new Aluno(
                "Michel Pereira",
                "+55 47 99875-4582",
                "michel@gmail.com"));
    }
}
