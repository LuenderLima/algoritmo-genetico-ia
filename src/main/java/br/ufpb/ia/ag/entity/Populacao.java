package br.ufpb.ia.ag.entity;


public class Populacao {

    private Individuo[] individuos;
    private int tamanhoPopulacao;

    //Cria uma popula��o com indiv�duos aleat�rios
    public Populacao(int numGenes, int tamanhoPopulacao) { // tirar o numGenes
        this.tamanhoPopulacao = tamanhoPopulacao;
        individuos = new Individuo[tamanhoPopulacao];
        for (int i = 0; i < individuos.length; i++) {
            individuos[i] = new Individuo(numGenes);
        }
    }

    //Cria uma popula��o com indiv�duos sem valor, que ser�o compostos posteriormente
    public Populacao(int tamanhoPopulacao) {
        this.tamanhoPopulacao = tamanhoPopulacao;
        individuos = new Individuo[tamanhoPopulacao];
        for (int i = 0; i < individuos.length; i++) {
            individuos[i] = null;
        }
    }
    
    //Coloca um indiv�duo em uma certa posi��o da popula��o
    public void setIndividuo(Individuo individuo, int posicao) {
        individuos[posicao] = individuo;
    }

    //Adiciona um indiv�duo na pr�xima posi��o dispon�vel da popula��o
    public void adicionarIndividuo(Individuo individuo) {
        for (int i = 0; i < individuos.length; i++) {
            if (individuos[i] == null) {
                individuos[i] = individuo;
                return;
            }
        }
    }

    /** ordena a popula��o pelo valor de aptid�o de cada indiv�duo, do maior valor para o menor, assim se eu quiser obter o melhor indiv�duo desta popula��o, 
     * acesso a popula��o 0 do array de indiv�duos 
     */
    public void ordenarPopulacao() {
        boolean trocou = true;
        while (trocou) {
            trocou = false;
            for (int i = 0; i < individuos.length - 1; i++) {
                if (individuos[i].getAptidao() < individuos[i + 1].getAptidao()) {
                    Individuo temp = individuos[i];
                    individuos[i] = individuos[i + 1];
                    individuos[i + 1] = temp;
                    trocou = true;
                }
            }
        }
    }
    
    /**
     * verifica se algum indiv�duo da popula��o possui a solu��o
     */
    public boolean avaliarPopulacao(int aptidaoMaxima) {
    	for(Individuo individuo: this.individuos) {
    		if(individuo.getAptidao() == aptidaoMaxima) {
    			return true;
    		}
    	}
    	return false;
   
    }

    /**
     * N�mero de indiv�duos existentes na popula��o
     */
    public int getNumIndividuos() {
        int num = 0;
        for (int i = 0; i < individuos.length; i++) {
            if (individuos[i] != null) {
                num++;
            }
        }
        return num;
    }
    
    public Individuo getMelhorIndividuo() {
    	return this.individuos[0];
    }

    public int getTamPopulacao() {
        return tamanhoPopulacao;
    }

    public Individuo getIndividuo(int pos) {
        return individuos[pos];
    }
    
}