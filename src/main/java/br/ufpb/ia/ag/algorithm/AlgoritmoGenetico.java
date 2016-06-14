package br.ufpb.ia.ag.algorithm;

import java.util.ArrayList;
import java.util.List;





import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufpb.ag.es.entity.Gerenciador;
import br.ufpb.ag.es.entity.Horario;
import br.ufpb.ag.es.entity.Disciplina;
import br.ufpb.ag.es.entity.Usuario;
import br.ufpb.ia.ag.entity.Dados;
import br.ufpb.ia.ag.entity.Populacao;
import br.ufpb.ia.ag.entity.Slot;

public class AlgoritmoGenetico {
	
	public static void main(String[] args) {
		Random random = new Random();
    	String[] nomesParticipantes = {"Odravison", "Paulo", "S�rgio J�nior", "Ricardo", "Luender", "J�o"};
    	String[] nomesTarefas = {"Varrer casa", "Lavar lou�a", "Lavar banheiro", "Lavar panos de prato",
    				"Limpar m�veis", "Comprar p�o", "Lavar panos de ch�o"}; //, "aaa", "bbb", "ccc", "ddd", "eee", "ffff", "ggg"
    	
    	try {
    		Integer horaInicio;
    		Integer horaFim;
    		String dia;
    		Disciplina tarefa;
    		ArrayList<Usuario> participantes = new ArrayList<Usuario>();
    		
    		Usuario participante = new Usuario();
        	participante.setNome("Luender");
        	Horario h1;
    		
    		//inicializa os outros usu�rios do grupo
    		for(String nome: nomesParticipantes) {
    			participante = new Usuario();
	        	participante.setNome(nome);
	        	for(int i=0; i<8; i++) {
	        		dia = Dados.getDiaDaSemana(random.nextInt(7));
	        		horaInicio = random.nextInt(Dados.getHorarioMaximo());
	        		horaFim = horaInicio+1;
	        		
	        		h1 = new Horario(horaInicio.toString(), horaFim.toString(), dia);
	        		
		        	participante.cadastrarHorarioDisponivel(h1);
		        	
		        	System.out.println("Cadastrou o hor�rio dispon�vel de "+nome+": dia: "+dia+", hora Inicio: "+horaInicio+", hora Fim: "+horaFim);
	        	}
	        	participantes.add(participante);
    		}
    		
    		//Cadastra a for�a-tarefa no gerenciador
    		Gerenciador.getInstance().cadastrarUsuario(participante);
			Gerenciador.getInstance().cadastrarForcaTarefa("Zorra do lulu", participante.getId(), "0", "23");
			
			String idForcaTarefa = Gerenciador.getInstance().getForcasTarefa().get(0).getId();
			//Cadastra tarefas e aloca seus encarregados
    		int j=0;
    		for(int i=0; i < nomesTarefas.length; i++) {
    			if(j==4){
    				j=0;
    			} else {
    				j++;;
    			}
    			tarefa = new Disciplina(nomesTarefas[i]);
    			tarefa.setEncarregado(participantes.get(j));
    			tarefa.setFrequencia(2);
    			Gerenciador.getInstance().cadastrarTarefa(idForcaTarefa, tarefa);
    		
    			System.out.println("Participante "+tarefa.getEncarregado().getNome()+" ser� o encarregado da tarefa "+tarefa.getNome());
    		}
    		
    		JSONObject object = new JSONObject();
    	    try {
    	        object.put("tarefas", Gerenciador.getInstance().getForcaTarefa(idForcaTarefa).getTarefas());
    	        object.append("lista tarefas", Gerenciador.getInstance().getForcaTarefa(idForcaTarefa).getTarefas());
    	        //System.out.println(object.toString());
    	        
    	        executarAlgoritmoGenetico(Gerenciador.getInstance().getForcaTarefa(idForcaTarefa).getTarefas());

    	    } catch (JSONException e) {
    	        e.printStackTrace();
    	    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    
    public static List<List<Slot>> executarAlgoritmoGenetico(List<Disciplina> tarefas) {

    	//Seta os valores para a execu��o do algoritmo
    	Algoritmo.setDisciplinas(tarefas);
    	//Define a taxa de crossover do algoritmo
        Algoritmo.setTaxaDeCrossover(Dados.getTaxaDeCrossover());
        //Define a taxa de muta��o do algoritmo
        Algoritmo.setTaxaDeMutacao(Dados.getTaxaDeMutacao());
        //Eltismo: Manuten��o do melhor indiv�duo nas pr�ximas gera��es
        boolean eltismo = true;
        //Tamanho da popula��o que ser� criada
        int tamanhoPopulacao = Dados.getTamanhoMaximoPopulacao();
        //N�mero m�ximo de gera��es do algoritmo
        int numMaxGeracoes = Dados.getNumeroMaximoGeracoes();
        //Define o n�mero de genes do indiv�duo baseado na quantidadde de tarefas que devem ser alocadas
        int numGenes = Algoritmo.getQuantidadeDisciplinas();
        
        //Cria a primeira popula��o aleatoriamente
        Populacao populacao = new Populacao(numGenes, tamanhoPopulacao);

        // Avalia a primeira popula��o gerada, verificando se a solu��o procurada foi encontrada
        boolean temSolucao = populacao.avaliarPopulacao(Algoritmo.getAptidaoMaxima());   
        int geracao = 0;

        System.out.println("Iniciando... Aptid�o da solu��o: "+numGenes);
        
        //Loop at� a solu��o ser encontrada ou at� atingir o n�mero m�ximo de gera��es
        while (!temSolucao && geracao < numMaxGeracoes) {
            geracao++;
            //Cria uma nova popula��o
            populacao = Algoritmo.novaGeracao(populacao, eltismo);
                        
            //Imprime o melhor indiv�duo da popula��o
            System.out.println("Gera��o " + geracao + " | Aptid�o: " + populacao.getIndividuo(0).getAptidao());

            //Avalia a nova gera��o criada, verificando se a soluu��o procurada foi encontrada 
            temSolucao = populacao.avaliarPopulacao(Algoritmo.getAptidaoMaxima());
        }
        
        return populacao.getMelhorIndividuo().getGenes();

    }
}