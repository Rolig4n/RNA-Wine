/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnawine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import static rnawine.GeraCSV.geraCSV;

/**
 *
 * @author hiago
 */
public class RnaWine {

    //Atributos gerais
    static public List<Wine> baseDados = new ArrayList<Wine>();
    static public List<Wine> resultado = new ArrayList<Wine>();

    //Base individual para cada neuronio - classe 1,2,3
    static public double[][] baseTreina;
    static public double[][] baseValida;
    static public double[][] baseTeste;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //padrões de execução da rede neural
        double bias = 1;
        double taxaAprendizado = 0.01;
        double[] pesoW = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int nroIteracoes = 20000;
        boolean geraBaseValidacao = false;
        boolean normalizaBase = false;
        List<Estatistica> lstEstatistica = new ArrayList<Estatistica>();
        //dados arquivo
        String arquivo = "C:/Users/hiago/Downloads/BaseWine/wine.data";
        List<Wine> base = readFile(arquivo);

        if (normalizaBase) {
            baseDados = normalizaBase(base);
        } else {
            baseDados = base;
        }

        //laço de N vezes
        for (int nroTestes = 0; nroTestes <= 999; nroTestes++) {
            double[] wc1;
            double[] wc2;
            double[] wc3;
            //TREINANDO o neuronio
            //identificar classe 1
            Perceptron oRnaC1 = new Perceptron();
            oRnaC1.setAlfa(taxaAprendizado);
            oRnaC1.setBias(bias);
            oRnaC1.setNET(bias);
            oRnaC1.setW(pesoW);
            oRnaC1.setMaxInt(nroIteracoes);

            for (int k = 0; k <= 100; k++) {
                /*if (geraBaseValidacao) {
                wc1 = oRnaC1.treinar(baseTreina, baseValida);
                } else {
                wc1 = oRnaC1.treinar(baseTreina);
                }*/
                sorteiaBase(baseDados, geraBaseValidacao, 1);
                wc1 = oRnaC1.treinar(baseTreina);
                Estatistica oEstatistica = oRnaC1.testarTreinamento(baseTeste, wc1);
                lstEstatistica.add(oEstatistica);
            }
            System.out.println("Teste!");
        }
        try {
            geraCSV(lstEstatistica, "estat_C1.csv");
        } catch (IOException ex) {
            Logger.getLogger(RnaWine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodo de leitura do arquivo
    static private List<Wine> readFile(String arquivo) {
        List<Wine> lstBase = new ArrayList<Wine>();
        try {
            BufferedReader base = new BufferedReader(new FileReader(arquivo));
            while (base.ready()) {
                //lendo linha a linha
                String linhaBase = base.readLine();
                //valores entre linha ","
                String[] valueFilds = linhaBase.split(",");
                Wine oWine = new Wine();
                oWine.setClasseWine(Integer.parseInt(valueFilds[0]));
                oWine.setA1(Double.parseDouble(valueFilds[1]));
                oWine.setA2(Double.parseDouble(valueFilds[2]));
                oWine.setA3(Double.parseDouble(valueFilds[3]));
                oWine.setA4(Double.parseDouble(valueFilds[4]));
                oWine.setA5(Double.parseDouble(valueFilds[5]));
                oWine.setA6(Double.parseDouble(valueFilds[6]));
                oWine.setA7(Double.parseDouble(valueFilds[7]));
                oWine.setA8(Double.parseDouble(valueFilds[8]));
                oWine.setA9(Double.parseDouble(valueFilds[9]));
                oWine.setA10(Double.parseDouble(valueFilds[10]));
                oWine.setA11(Double.parseDouble(valueFilds[11]));
                oWine.setA12(Double.parseDouble(valueFilds[12]));
                oWine.setA13(Double.parseDouble(valueFilds[13]));

                lstBase.add(oWine);
            }
            base.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstBase;
    }

    //Metodo normalizador
    static private List<Wine> normalizaBase(List<Wine> lstWine) {
        List<Wine> listaNormalizada = new ArrayList<Wine>();
        double[] a1 = new double[lstWine.size()];
        double[] a2 = new double[lstWine.size()];
        double[] a3 = new double[lstWine.size()];
        double[] a4 = new double[lstWine.size()];
        double[] a5 = new double[lstWine.size()];
        double[] a6 = new double[lstWine.size()];
        double[] a7 = new double[lstWine.size()];
        double[] a8 = new double[lstWine.size()];
        double[] a9 = new double[lstWine.size()];
        double[] a10 = new double[lstWine.size()];
        double[] a11 = new double[lstWine.size()];
        double[] a12 = new double[lstWine.size()];
        double[] a13 = new double[lstWine.size()];
        //gerar vetores
        int contElementos = 0;
        for (Wine oWine : lstWine) {
            a1[contElementos] = oWine.getA1();
            a2[contElementos] = oWine.getA2();
            a3[contElementos] = oWine.getA3();
            a4[contElementos] = oWine.getA4();
            a5[contElementos] = oWine.getA5();
            a6[contElementos] = oWine.getA6();
            a7[contElementos] = oWine.getA7();
            a8[contElementos] = oWine.getA8();
            a9[contElementos] = oWine.getA9();
            a10[contElementos] = oWine.getA10();
            a11[contElementos] = oWine.getA11();
            a12[contElementos] = oWine.getA12();
            a13[contElementos] = oWine.getA13();
            contElementos++;
        }
        //calcular desvio padrão e sua media
        double dvA1 = calcularDesvioPadrao(a1);
        double mediaA1 = calcularMedia(a1);
        double dvA2 = calcularDesvioPadrao(a2);
        double mediaA2 = calcularMedia(a2);
        double dvA3 = calcularDesvioPadrao(a3);
        double mediaA3 = calcularMedia(a3);
        double dvA4 = calcularDesvioPadrao(a4);
        double mediaA4 = calcularMedia(a5);
        double dvA5 = calcularDesvioPadrao(a5);
        double mediaA5 = calcularMedia(a5);
        double dvA6 = calcularDesvioPadrao(a6);
        double mediaA6 = calcularMedia(a6);
        double dvA7 = calcularDesvioPadrao(a7);
        double mediaA7 = calcularMedia(a7);
        double dvA8 = calcularDesvioPadrao(a8);
        double mediaA8 = calcularMedia(a8);
        double dvA9 = calcularDesvioPadrao(a9);
        double mediaA9 = calcularMedia(a9);
        double dvA10 = calcularDesvioPadrao(a10);
        double mediaA10 = calcularMedia(a10);
        double dvA11 = calcularDesvioPadrao(a11);
        double mediaA11 = calcularMedia(a11);
        double dvA12 = calcularDesvioPadrao(a12);
        double mediaA12 = calcularMedia(a12);
        double dvA13 = calcularDesvioPadrao(a13);
        double mediaA13 = calcularMedia(a13);

        for (Wine oWine : lstWine) {
            Wine oWineNorm = new Wine();
            oWineNorm.setA1(oWine.getA1() - mediaA1 / dvA1);
            oWineNorm.setA2(oWine.getA2() - mediaA2 / dvA2);
            oWineNorm.setA3(oWine.getA3() - mediaA3 / dvA3);
            oWineNorm.setA4(oWine.getA4() - mediaA4 / dvA4);
            oWineNorm.setA5(oWine.getA5() - mediaA5 / dvA5);
            oWineNorm.setA6(oWine.getA6() - mediaA6 / dvA6);
            oWineNorm.setA7(oWine.getA7() - mediaA7 / dvA7);
            oWineNorm.setA8(oWine.getA8() - mediaA8 / dvA8);
            oWineNorm.setA9(oWine.getA9() - mediaA9 / dvA9);
            oWineNorm.setA10(oWine.getA10() - mediaA10 / dvA10);
            oWineNorm.setA11(oWine.getA11() - mediaA11 / dvA11);
            oWineNorm.setA12(oWine.getA12() - mediaA12 / dvA12);
            oWineNorm.setA13(oWine.getA13() - mediaA13 / dvA13);
            listaNormalizada.add(oWineNorm);
        }

        return listaNormalizada;
    }

    //COMEÇO Metodos matematicos0
    static private double calcularMedia(double[] listaValores) {
        double resultadoMedia = 0;
        double somatorio = 0;
        int tamanhoVetor = listaValores.length;
        for (int i = 0; i < tamanhoVetor; i++) {
            somatorio = somatorio + listaValores[i];
        }
        resultadoMedia = somatorio / tamanhoVetor;
        return resultadoMedia;
    }

    static private double calcularDesvioPadrao(double[] listaValores) {
        double media = calcularMedia(listaValores);
        double somatorio = 0;
        int tamanhoVetor = listaValores.length;
        for (int i = 0; i < tamanhoVetor; i++) {
            somatorio = somatorio + Math.pow(listaValores[i] - media, 2);
        }
        //soma dos quadrados da diferença entre cada valor em sua media aritimetica divida pela quantida de elementos do vetor
        double valorVariancia = somatorio / tamanhoVetor;
        //desvio padrao - raiz quadrada da variancia
        double valorDesvioPadrao = Math.sqrt(valorVariancia);
        return valorDesvioPadrao;
    }
    //FIM Metodos matematicos

    //Metodo de sorteio da base
    static private void sorteiaBase(List<Wine> lstWine, boolean valida, int classe) {
        //Percentual de rateio das bases
        double percTreina = 0, percValida = 0, percTeste = 0;
        if (valida) {
            percTreina = 0.5;
            percValida = 0.2;
            percTeste = 0.3;
        } else {
            percTreina = 0.7;
            percTeste = 0.3;
        }
        //conta num casos
        int nroC1 = 0, nroC2 = 0, nroC3 = 0;
        int nroBaseTreinaC1 = 0, nroBaseTreinaC2 = 0, nroBaseTreinaC3 = 0;
        int nroBaseValidaC1 = 0, nroBaseValidaC2 = 0, nroBaseValidaC3 = 0;
        int nroBaseTesteC1 = 0, nroBaseTesteC2 = 0, nroBaseTesteC3 = 0;

        //contar nro ocorrencias na base
        for (Wine oWine : lstWine) {
            if (oWine.getClasseWine() == 1) {
                nroC1++;
            } else if (oWine.getClasseWine() == 2) {
                nroC2++;
            } else if (oWine.getClasseWine() == 3) {
                nroC3++;
            }
        }
        //Definir tamanho de cada base
        //treina
        nroBaseTreinaC1 = (int) (nroC1 * percTreina);
        nroBaseTreinaC2 = (int) (nroC2 * percTreina);
        nroBaseTreinaC3 = (int) (nroC3 * percTreina);
        //valida
        nroBaseValidaC1 = (int) (nroC1 * percValida);
        nroBaseValidaC2 = (int) (nroC2 * percValida);
        nroBaseValidaC3 = (int) (nroC3 * percValida);
        //teste
        nroBaseTesteC1 = (nroC1 - (nroBaseTreinaC1 + nroBaseValidaC1));
        nroBaseTesteC2 = (nroC2 - (nroBaseTreinaC2 + nroBaseValidaC2));
        nroBaseTesteC3 = (nroC3 - (nroBaseTreinaC3 + nroBaseValidaC3));
        //declara o tamanho de cada base
        int tamBaseTreina = 0, tamBaseValida = 0, tamBaseTeste = 0;
        if (nroBaseValidaC1 > 0) {
            //com validacao
            tamBaseTreina = nroBaseTreinaC1 + nroBaseTreinaC2 + nroBaseTreinaC3;
            tamBaseValida = nroBaseValidaC1 + nroBaseValidaC2 + nroBaseValidaC3;
            tamBaseTeste = lstWine.size() - (tamBaseTreina + tamBaseValida);
            baseTreina = new double[tamBaseTreina][15];
            baseValida = new double[tamBaseValida][15];
            baseTeste = new double[tamBaseTeste][15];
        } else {
            //sem validacao
            tamBaseTreina = nroBaseTreinaC1 + nroBaseTreinaC2 + nroBaseTreinaC3;
            tamBaseValida = 0;
            tamBaseTeste = lstWine.size() - (tamBaseTreina + tamBaseValida);
            baseTreina = new double[tamBaseTreina][15];
            baseTeste = new double[tamBaseTeste][15];
        }
        int sorteia = 0;
        int ctaBase1Classe1 = 0, ctaBase1Classe2 = 0, ctaBase1Classe3 = 0;
        int ctaBase2Classe1 = 0, ctaBase2Classe2 = 0, ctaBase2Classe3 = 0;
        int ctaBase3Classe1 = 0, ctaBase3Classe2 = 0, ctaBase3Classe3 = 0;
        int linhaBase1 = 0, linhaBase2 = 0, linhaBase3 = 0;

        Random randBase = new Random();
        int contaElemento = -1;
        for (Wine oSepara : lstWine) {
            //0 ou 1 se for a classe deinteresse
            int valorD = 0;
            if (oSepara.getClasseWine() == classe) {
                valorD = 1;
            }
            /*
                 sorteia qual base vai receboer o elemento proprocional
                 tamanha da base respeitando o tamanho de casa base
             */
            boolean passa;
            do {
                passa = true;
                int nroRandomico = randBase.nextInt(99);
                if (tamBaseValida > 0) {
                    if (nroRandomico <= 69) {
                        sorteia = 1;
                    } else if (nroRandomico > 69 && nroRandomico <= 84) {
                        sorteia = 2;
                    } else if (nroRandomico > 84 && nroRandomico <= 99) {
                        sorteia = 3;
                    }
                } else {
                    if (nroRandomico <= 69) {
                        sorteia = 1;
                    } else if (nroRandomico > 69 && nroRandomico <= 99) {
                        sorteia = 2;
                    }
                }
                int classeElemento = oSepara.getClasseWine();
                //valida sorteio
                if (sorteia == 1 && linhaBase1 < tamBaseTreina
                        && ((oSepara.getClasseWine() == 1 && ctaBase1Classe1 < nroBaseTreinaC1)
                        || (oSepara.getClasseWine() == 2 && ctaBase1Classe2 < nroBaseTreinaC2)
                        || (oSepara.getClasseWine() == 3 && ctaBase1Classe3 < nroBaseTreinaC3))) {
                    passa = false;
                } else if (sorteia == 2 && linhaBase2 < tamBaseTeste
                        && ((oSepara.getClasseWine() == 1 && ctaBase2Classe1 < nroBaseTesteC1)
                        || (oSepara.getClasseWine() == 2 && ctaBase2Classe2 < nroBaseTesteC2)
                        || (oSepara.getClasseWine() == 3 && ctaBase2Classe3 < nroBaseTesteC3))) {
                    passa = false;
                } else if (sorteia == 3 && linhaBase3 < tamBaseValida
                        && ((oSepara.getClasseWine() == 1 && ctaBase3Classe1 < nroBaseValidaC1)
                        || (oSepara.getClasseWine() == 2 && ctaBase3Classe2 < nroBaseValidaC2)
                        || (oSepara.getClasseWine() == 3 && ctaBase3Classe3 < nroBaseValidaC3))) {
                    passa = false;
                }
            } while (passa);

            if (sorteia == 1) {
                //base treinamento
                baseTreina[linhaBase1][0] = oSepara.getA1();
                baseTreina[linhaBase1][1] = oSepara.getA2();
                baseTreina[linhaBase1][2] = oSepara.getA3();
                baseTreina[linhaBase1][3] = oSepara.getA4();
                baseTreina[linhaBase1][4] = oSepara.getA5();
                baseTreina[linhaBase1][5] = oSepara.getA6();
                baseTreina[linhaBase1][6] = oSepara.getA7();
                baseTreina[linhaBase1][7] = oSepara.getA8();
                baseTreina[linhaBase1][8] = oSepara.getA9();
                baseTreina[linhaBase1][9] = oSepara.getA10();
                baseTreina[linhaBase1][10] = oSepara.getA11();
                baseTreina[linhaBase1][11] = oSepara.getA12();
                baseTreina[linhaBase1][12] = oSepara.getA13();
                baseTreina[linhaBase1][13] = oSepara.getClasseWine();
                baseTreina[linhaBase1][14] = valorD;
                linhaBase1++;
                if (oSepara.getClasseWine() == 1) {
                    ctaBase1Classe1++;
                } else if (oSepara.getClasseWine() == 2) {
                    ctaBase1Classe2++;
                } else if (oSepara.getClasseWine() == 3) {
                    ctaBase1Classe3++;
                }
            } else if (sorteia == 2) {
                //base teste
                baseTeste[linhaBase2][0] = oSepara.getA1();
                baseTeste[linhaBase2][1] = oSepara.getA2();
                baseTeste[linhaBase2][2] = oSepara.getA3();
                baseTeste[linhaBase2][3] = oSepara.getA4();
                baseTeste[linhaBase2][4] = oSepara.getA5();
                baseTeste[linhaBase2][5] = oSepara.getA6();
                baseTeste[linhaBase2][6] = oSepara.getA7();
                baseTeste[linhaBase2][7] = oSepara.getA8();
                baseTeste[linhaBase2][8] = oSepara.getA9();
                baseTeste[linhaBase2][9] = oSepara.getA10();
                baseTeste[linhaBase2][10] = oSepara.getA11();
                baseTeste[linhaBase2][11] = oSepara.getA12();
                baseTeste[linhaBase2][12] = oSepara.getA13();
                baseTeste[linhaBase2][13] = oSepara.getClasseWine();
                baseTeste[linhaBase2][14] = valorD;
                linhaBase2++;
                if (oSepara.getClasseWine() == 1) {
                    ctaBase2Classe1++;
                } else if (oSepara.getClasseWine() == 2) {
                    ctaBase2Classe2++;
                } else if (oSepara.getClasseWine() == 3) {
                    ctaBase2Classe3++;
                }
            } else if (sorteia == 3) {
                //base valida
                baseValida[linhaBase3][0] = oSepara.getA1();
                baseValida[linhaBase3][1] = oSepara.getA2();
                baseValida[linhaBase3][2] = oSepara.getA3();
                baseValida[linhaBase3][3] = oSepara.getA4();
                baseValida[linhaBase3][4] = oSepara.getA5();
                baseValida[linhaBase3][5] = oSepara.getA6();
                baseValida[linhaBase3][6] = oSepara.getA7();
                baseValida[linhaBase3][7] = oSepara.getA8();
                baseValida[linhaBase3][8] = oSepara.getA9();
                baseValida[linhaBase3][9] = oSepara.getA10();
                baseValida[linhaBase3][10] = oSepara.getA11();
                baseValida[linhaBase3][11] = oSepara.getA12();
                baseValida[linhaBase3][12] = oSepara.getA13();
                baseValida[linhaBase3][13] = oSepara.getClasseWine();
                baseValida[linhaBase3][14] = valorD;
                linhaBase3++;
                if (oSepara.getClasseWine() == 1) {
                    ctaBase3Classe1++;
                } else if (oSepara.getClasseWine() == 2) {
                    ctaBase3Classe2++;
                } else if (oSepara.getClasseWine() == 3) {
                    ctaBase3Classe3++;
                }
            }
        }
    }

    static private void atualizaBases(int classe) {
        int tamBaseTreina = baseTreina.length;
        int tamBaseTeste = baseTeste.length;
        int tamBaseValida = 0;

        if (!(baseValida == null)) {
            tamBaseValida = baseValida.length;
        }

        int valorD = 1;
        for (int x = 0; x < tamBaseTreina; x++) {
            if (baseTreina[x][13] == classe) {
                baseTreina[x][14] = valorD;
            } else {
                baseTreina[x][14] = 0;
            }
        }

        for (int x = 0; x < tamBaseTeste; x++) {
            if (baseTeste[x][13] == classe) {
                baseTeste[x][14] = valorD;
            } else {
                baseTeste[x][14] = 0;
            }
        }

        for (int x = 0; x < tamBaseValida; x++) {
            if (baseValida[x][13] == classe) {
                baseValida[x][14] = valorD;
            } else {
                baseValida[x][14] = 0;
            }
        }
    }
}
