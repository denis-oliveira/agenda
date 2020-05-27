package alura.com.br.DAO;

import java.util.ArrayList;
import java.util.List;

import alura.com.br.model.Aluno;

public class AlunoDAO {

    private final static List<Aluno> alunos = new ArrayList<>();
    private static int contadorDeIds = 1;

    public void salva(Aluno aluno) {
        aluno.setId(AlunoDAO.contadorDeIds);
        AlunoDAO.alunos.add(aluno);
        atualizaIds();
    }

    private void atualizaIds() {
        AlunoDAO.contadorDeIds++;
    }

    public void edita(Aluno aluno) {
        Aluno alunoEncontrado = buscaAlunoPeloId(aluno);
        if (alunoEncontrado != null) {
            int posicaoDoAluno = AlunoDAO.alunos.indexOf(alunoEncontrado);
            AlunoDAO.alunos.set(posicaoDoAluno, aluno);
        }
    }

    private Aluno buscaAlunoPeloId(Aluno aluno) {
        for (Aluno a : AlunoDAO.alunos) {
            if (a.getId() == aluno.getId()) {
                return a;
            }
        }
        return null;
    }

    public List<Aluno> todos() {
        return new ArrayList<>(AlunoDAO.alunos);
    }
}
