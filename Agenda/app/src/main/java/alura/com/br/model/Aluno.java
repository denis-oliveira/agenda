package alura.com.br.model;

import androidx.annotation.NonNull;

// Class to store student information
public class Aluno {
    private final String nome;
    private final String telefone;
    private final String email;

    public Aluno(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nome;
    }
}
