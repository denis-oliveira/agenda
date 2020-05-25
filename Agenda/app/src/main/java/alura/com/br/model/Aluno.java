package alura.com.br.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

// Class to store student information
public class Aluno implements Serializable {
    private final String nome;
    private final String telefone;
    private final String email;

    public Aluno(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return this.nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public String getEmail() {
        return this.email;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nome;
    }
}