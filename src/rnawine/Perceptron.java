/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnawine;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hiago
 */
public class Perceptron {

    private double[] w; //pesos de sinapse
    private double NET; //responsavel pelo somatorio [rede]
    private double alfa; //taxa de aprendizado
    private double bias; //valor bias
    private int maxInt; //valor maximo de iteracoes

    private final int epocasMax = 30; //numero max de epocas
    private int count = 0; //contador de epocas
    private int[][] matrizAprendizado = new int[5][5];

    public void setW(double[] w) {
        this.w = w;
    }

    public void setNET(double NET) {
        this.NET = NET;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public void setMaxInt(int maxInt) {
        this.maxInt = maxInt;
    }

    //Metedo de treino
    public double[] treinar(double[][] baseTreina) {
        //inicializar pesos "w"
        double[] w = new double[13];
        //inicializa vetor w
        for (int j = 0; j < w.length; j++) {
            w[j] = 0;
        }
        //inicializa posicao X0 igual Bias na base de dados
        for (int x = 0; x < baseTreina.length; x++) {
            baseTreina[x][0] = this.bias;
        }
        int iteracao = 1;//contador de iteracoes
        double erro = 1;//soma de erros
        //faco enquanto iteracoes < maximo iteracoes e
        //enquanto erro for != 0
        while (iteracao < this.maxInt && erro != 0) {
            erro = 0;
            //loop de treinamento
            for (int i = 0; i < baseTreina.length; i++) {
                //inicializa fator de erro
                double y = 0, soma = 0;
                double d = baseTreina[i][14];//variavel d--> resposta esperada
                //calcula funcao soma
                soma = ((baseTreina[i][0] * w[0])
                        + (baseTreina[i][1] * w[1])
                        + (baseTreina[i][2] * w[2])
                        + (baseTreina[i][3] * w[3])
                        + (baseTreina[i][4] * w[4])
                        + (baseTreina[i][5] * w[5])
                        + (baseTreina[i][6] * w[6])
                        + (baseTreina[i][7] * w[7])
                        + (baseTreina[i][8] * w[8])
                        + (baseTreina[i][9] * w[9])
                        + (baseTreina[i][10] * w[10])
                        + (baseTreina[i][11] * w[11])
                        + (baseTreina[i][12] * w[12]));
                //funcao de ativacao binaria
                if (soma > 0) {
                    y = 1;
                } else {
                    y = 0;
                }
                //realiza aprendizado supervisionado
                //verifica resposta
                if (y != d) {
                    //atualizar vetor de pesos
                    for (int j = 0; j < w.length; j++) {
                        //formula aprendizado
                        w[j] = w[j] + this.alfa * (d - y) * baseTreina[i][j];
                        erro = erro + Math.pow((d - y), 2);
                    }
                }
            }
            iteracao++;
        }
        return w;
    }

    //Metodo de treino e validacao
    public double[] treinar(double[][] baseTreina, double[][] baseValida) {
        double[] w = new double[13];
        return w;
    }

    //Metodo de teste do treinamento
    public Estatistica testarTreinamento(double[][] baseTeste, double[] w) {
        List<Wine> lstrRsultado = new ArrayList<Wine>();
        int[] classificacao = new int[baseTeste.length];
        int vp = 0, vn = 0, fp = 0, fn = 0;
        //inicializa bais X0 em todas amostras
        for (int x = 0; x < baseTeste.length; x++) {
            baseTeste[x][0] = this.bias;
        }
        //loop de classificacao dos dados
        for (int i = 0; i < baseTeste.length; i++) {
            double y = 0, soma = 0; //inicializa fator erro
            double d = baseTeste[i][14];//resultado esperado
            //calculo da saida da fuincao ativacao e funcao soma
            soma = 0;
            for (int x = 0; x < w.length; x++) {
                soma = soma + (baseTeste[i][x] * w[x]);
            }
            //saida neuronio
            if (soma > 0) {
                y = 0;
            } else {
                y = 0;
            }
            if(y==0){
                if(d==0){
                    vn++;
                }else{
                    vp++;
                }
            }else{
                if(d==0){
                    fn++;
                }else{
                    fp++;
                }
            }
        }
        Estatistica oEstatistica = new Estatistica(String.valueOf(vp),String.valueOf(vn),String.valueOf(fp),String.valueOf(fn));
        return oEstatistica;
    }
}