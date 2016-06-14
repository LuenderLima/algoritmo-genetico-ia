package br.ufpb.ia.ag.entity;

import java.util.List;

import br.ufpb.ia.ag.exception.HorarioExistenteException;
import br.ufpb.ia.ag.exception.HorarioInexistenteException;

public class Professor {
	
	private String matricula;
	private String nome;
	private String senha;
	private String email;
	private List<Horario> horariosPreferidos;
	
	public Professor(String matricula, String nome, String senha, String email, List<Horario> horariosPreferidos) {
		this.matricula = matricula;
		this.nome = nome;
		this.senha = senha;
		this.email = email;
		this.horariosPreferidos = horariosPreferidos;
	}
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Horario> getHorariosPreferidos() {
		return horariosPreferidos;
	}

	public void setHorariosPreferidos(List<Horario> horariosPreferidos) {
		this.horariosPreferidos = horariosPreferidos;
	}

	public void cadastrarHorarioPreferido(Horario horario) throws HorarioExistenteException {
		if(existsHorario(horario)) {
			
			throw new HorarioExistenteException("O hor�rio de prefer�ncia j� existe.");
			
		} 
		
		this.horariosPreferidos.add(horario);
	}

	public void removerHorarioPreferido(String idHorarioPreferido) throws HorarioInexistenteException {
		boolean removeu = false;
		for(Horario h: this.horariosPreferidos) {
			if(h.equals(idHorarioPreferido)) {
				this.horariosPreferidos.remove(h);
				removeu = true;
			}
		}
		if(!removeu) {
			throw new HorarioInexistenteException("O hor�rio de prefer�ncia n�o existe.");
		}
		
	}
	
	public boolean existsHorario(Horario horario) {
		// COMO VAI TRATAR A QUEST�O DOS HOR�RIOS JUNTOS AOS DIAS AQUI? O PROFESSOR POSSUI UMA LISTA DE HOR�RIOS PREFERIDOS,
				// MAS N�O TEM A INFORMA��O DOS DIAS EM QUE ESSES HOR�RIOS CORRESPONDEM
		// Atualizado com o novo design de um objeto Horario
		for(Horario h: horariosPreferidos) {
			if(h.getDiaDaSemana().equals(horario.getDiaDaSemana()) && h.getHorarioAula().equals(horario.getHorarioAula())) {
				return true;
			}
		}
		return false;
	}

}
