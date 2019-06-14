import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class tp1 {

	
   static int NPratos, Orcamento, NDias;
   static int[] lucro, custo;
   static Prato[][][][] tabela;
   static StringBuilder posicaoprato = new StringBuilder();
   static final int custoInicial = 1000000000;//iniciar com maior valor possivelpara comparar
                                                  // com outro melhor e setar valor correto.
        
   static Prato dinamico(int ultimoPrato, int frequencia, int dia, int custoTotal)
   {
          
            
            
      if(custoTotal < 0){ 
         return new Prato(0, custoInicial);
      }
      if(dia == NDias){ 
         return new Prato(0,Orcamento - custoTotal);
      }
      if(tabela[ultimoPrato][frequencia][dia][custoTotal] != null){ 
         return tabela[ultimoPrato][frequencia][dia][custoTotal];
      }
   //		 for (int i = 0; i <= orcamento; i++)
   //                    for (int j = 0; j < numeroDePratos; j++)
   //                        for (int k = 0; k < 2; k++)
   //                            tabela[0, i, j, k] = 0;
   //
   //                for (int i = 1; i < numeroDeDias; i++)
   //                    for (int j = 0; j < numeroDePratos; j++)
   //                        for (int k = 0; k < 2; k++)
   //                            tabela[i, 0, j, k] = -1;
   //
   //                for (int i = 1; i < numeroDeDias; i++)
   //                    for (int j = 1; j <= orcamento; j++)
   //                        for (int k = 0; k < numeroDePratos; k++)
   //                            for (int l = 0; l < 2; l++)
   //                            {
   //                                tabela[i, j, k, l] = -1;
   //                                for (int item = 0; item < numeroDePratos; item++)
   //                                {
      Prato melhorPrato = new Prato(0,custoInicial);
      for(int i = 1; i <= NPratos; i++){
      
         int freqx;
         if(i == ultimoPrato){
            freqx = 1;
         }
         else{
            freqx = 0;
         }
                        
         Prato proximoPrato = dinamico(i, freqx, dia+1, custoTotal-custo[i]);
                        /* prato recebe = soma sobre todos os itens de tabela[i, frequencia, dia + 1, orcamento atual] */
                        
                        //enquanto o custo 
         if(proximoPrato.custo == custoInicial){
            continue;
         }
                        
         double lucroAtual;
         if(i == ultimoPrato){
                            
            if(frequencia == 0){// Se frequencia й igual ao prato anterior mais repetiu uma vez sу, entгo й metade.
               lucroAtual = 0.5*lucro[i];
            }
            else{  
               lucroAtual = 0*lucro[i];// Senao se frequencia  й igual ao prato anterior e ainda sim repetiu denovo, recebe 0.
            }       
         }
         else{
            lucroAtual = 1*lucro[i]; 
         }
                        
      //                           double lucroMaxPratoAtual = 0;
      //
      //                                    if (k == item) // Se k não for o mesmo prato anterior
      //                                    {
      //                                        if (l == 1) // Se k nao for igual ao prato anterior e ainda sim repetiu denovo, recebe 0.
      //                                            lucroMaxPratoAtual = 0;
      //                                        else if (l == 0) // Se k nao for igual ao prato anterior mais repetiu uma vez sу, entгo й metade.
      //                                            lucroMaxPratoAtual = (0.5 * lucros[item]);
      //                                    }
      //                                    else // k nao for diferente (prato novo)
      //                                        lucroMaxPratoAtual = lucros[item]; // Recebe lucro normalmente.
      //
      //                                    if (prox + lucroMaxPratoAtual > tabela[i, j, k, l]) // Se o lucro prox mais o lucro do prato atual for maior que o valor da tabela
      //                                        tabela[i, j, k, l] = prox + lucroMaxPratoAtual; // então tabela recebe o novo lucro maximo. 
      //                                }
      
         if(proximoPrato.lucro + lucroAtual> melhorPrato.lucro || Math.abs(proximoPrato.lucro + lucroAtual - melhorPrato.lucro) <= 10e-5){
                            
                                //verificando valor absoluto do lucro e menor que um valor grande setadado para subistirir custo melhor prato
                                 //  Math.Abs para pegar o valor absoluto.                 
            if(Math.abs(proximoPrato.lucro + lucroAtual - melhorPrato.lucro) <= 10e-9){// Se o lucro prox mais o lucro do prato atual й maior que o valor da tabela
               melhorPrato.custo = Math.min(melhorPrato.custo, proximoPrato.custo);
            }
            else{
               melhorPrato.lucro = proximoPrato.lucro + lucroAtual;
               melhorPrato.custo = proximoPrato.custo;
            }
         } 
      }
                
      return tabela[ultimoPrato][frequencia][dia][custoTotal] = melhorPrato;	//entгo tabela recebe o novo lucro maximo. 
                
   }
	
   static void printPosicaoPrato(int ultimoPrato, int frequencia, int dia, int custoTotal)
   {
      if(custoTotal < 0){ 
         return;
      }
      if(dia == NDias){
         return;
      }
   	
      Prato otimo = dinamico(ultimoPrato, frequencia, dia, custoTotal);
                
      for(int i = 1; i <= NPratos; i++)
      {
      	
         int freqx;
         if(i == ultimoPrato){
            freqx = 1;
         }
         else{
            freqx = 0;
         }
                        
         Prato proximoPrato = dinamico(i, freqx, dia+1, custoTotal-custo[i]);
                        /* prato recebe = soma sobre todos os itens de tabela[i, frequencia, dia + 1, orcamento atual] */
                        
                        //enquanto o custo 
         if(proximoPrato.custo == custoInicial){
            continue;
         }
                        
         double lucroAtual;
         if(i == ultimoPrato){
                            
            if(frequencia == 0){// Se frequencia й igual ao prato anterior mais repetiu uma vez sу, entгo й metade.
               lucroAtual = 0.5*lucro[i];
            }
            else{  
               lucroAtual = 0*lucro[i];// Senao se frequencia  й igual ao prato anterior e ainda sim repetiu denovo, recebe 0.
            }       
         }
         else{
            lucroAtual = 1*lucro[i]; 
         }
                        
         if(Math.abs(proximoPrato.lucro + lucroAtual - otimo.lucro) <= 10e-9 && otimo.custo == proximoPrato.custo)
         {
            posicaoprato.append(i).append(dia==NDias-1?"\n":" ");
            int ultimo;
            if(i == ultimoPrato){
               ultimo = 1;
            }
            else{
               ultimo =0;
            }
                                
            printPosicaoPrato(i,ultimo, dia + 1, custoTotal - custo[i]);
            return;
         }
      }
   	
   		
   }
	
   public static void main(String[] args) throws IOException {
   //		int mb = 1024;
   //                long memoria_inicial  = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/mb;
   //                long tempo_inicial =  System.currentTimeMillis();
   //                Instant start = Instant.now();
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                
      String ler;
      while(true)
      {
                    
                     // ler aquivo 
         ler = br.readLine();
                        
         if(!ler.equals("")){
                            
            StringTokenizer st = new StringTokenizer(ler);
            NDias = Integer.parseInt(st.nextToken());
                            
            if(NDias == 0){
               break;
            }
            else{// atribuir valores de acordo com entrada 
               NPratos = Integer.parseInt(st.nextToken());
               Orcamento = Integer.parseInt(st.nextToken());
            
               custo = new int[NPratos+1];
               lucro = new int[NPratos+1];
            
            
               for(int i = 1; i <= NPratos; i++)
               {
                  st = new StringTokenizer(br.readLine());
                  custo[i] = Integer.parseInt(st.nextToken());
                  lucro[i] = Integer.parseInt(st.nextToken());				
               }
            
               tabela = new Prato[NPratos+1][2][NDias+1][Orcamento+1];
            
                               // inicializando algoritimo dinamico com orcamento 
               Prato prato = dinamico(0,0,0,Orcamento);
            
            
               if(prato.custo > Orcamento){
                  posicaoprato.append("0.0\n\n");
                                    
               }
               else
               {
                  posicaoprato.append(new DecimalFormat("0.0").format(prato.lucro).replace(",",".")).append("\n");
                  printPosicaoPrato(0,0,0,Orcamento);
               }
                             
            }
                           
         }  	
      } 
      System.out.print(posicaoprato);  
                
   //               long memoria_final  = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/mb;
   //               long tempo_final =  System.currentTimeMillis();
   //               System.out.println("Memoria usada: " + (memoria_final - memoria_inicial) + "kb");
   //               System.out.println("Tempo gasto: " + ((tempo_final - tempo_inicial) - 1000) + "ms");
   //               System.out.println("Dias: "+ NDias +"--- Pratos: "+NPratos);
   //               Instant end = Instant.now();
   //               System.out.println(Duration.between(start, end)); 
   }
}
// objeto de pratos 
class Prato
{
   double lucro;
   int custo;
   int frequencia;
        
   Prato(double x, int y)
   {
      lucro = x;
      custo = y;
   }
}