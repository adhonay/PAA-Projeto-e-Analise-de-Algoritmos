import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.StringTokenizer;

public class tp1 {

   static int NPratos, Orcamento, NDias, frequencia;
   static int [] melhorPrato;
   static int[] custo, lucro;
   static Prato[] vetPratos;
   static double peso; 
   static boolean erro;
        
   public void guloso(){
      double resultado = 0;
        //ordena vetor pratos
      Arrays.sort(vetPratos);
      melhorPrato = new int [NDias];
      for (int i =0; i < NDias; i++){           
          // System.out.println(vetPratos[i].getCusto()+" - "+vetPratos[i].getLucro()+" - "+vetPratos[i].getFrequencia()+" - "+vetPratos[i].getPeso()+" - "+vetPratos[i].getPosicao());
         if(vetPratos[0].getCusto() <= Orcamento){
            Orcamento  = Orcamento - vetPratos[0].getCusto();
                
            if(vetPratos[0].getUsado() == 0){
               melhorPrato[i] = vetPratos[0].getPosicao();
               resultado += vetPratos[0].getLucroAtual();
               vetPratos[0].setPesoAtual(vetPratos[0].getPesoAtual()/2.0);
               vetPratos[0].setLucroAtual( vetPratos[0].getLucroAtual()/2.0);
            }
            if(vetPratos[0].getUsado() >= 1){
               melhorPrato[i] = vetPratos[0].getPosicao();
               resultado += vetPratos[0].getLucroAtual();
               vetPratos[0].setPesoAtual(0);
               vetPratos[0].setLucroAtual(0);
            }
                //seta todos usados como zero menos a posicao inicial , para verificaзгo no proximo loop caso nao seja reutilizado
                //mesmo raciocionio pro lucroAtual e pesoAtual que se nao for utilizado 1 dia apos o outro entгo tera lucroAtual novamente
            for (int j =1; j < vetPratos.length; j++){ 
               vetPratos[j].setUsado(0);
               vetPratos[j].setPesoAtual(vetPratos[j].getPesoInicial());
               vetPratos[j].setLucroAtual(vetPratos[j].getLucroInicial());
            }
                
            vetPratos[0].setUsado(vetPratos[0].getUsado()+1);
                //ordena vetor novamente
            Arrays.sort(vetPratos);
                
         }
         else{
            erro = true;
         }     
      }
      if(erro == false){
         System.out.println("\n"+resultado); 
         for (int i =0; i < melhorPrato.length; i++){  
            System.out.print(melhorPrato[i]+" ");
         }
      }
      else{
         System.out.println("0.0");
         erro = false; // seta false para  a proxima vez que entrar 
      }
   }
    
   public static void main(String[] args) throws IOException {
        // TODO code application logic here
               // int mb = 1024;
   //                long memoria_inicial  = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
   //                long tempo_inicial =  System.currentTimeMillis();
      Instant start = Instant.now();
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      tp1 gu =  new tp1();
      String ler;
      while(true)
      {  
         ler = br.readLine();
                    
         if(!ler.equals("")){
                        
            StringTokenizer st = new StringTokenizer(ler);
                        
            NDias = Integer.parseInt(st.nextToken()); 
            if(NDias == 0){
               break;
            } 
            else{
               NPratos = Integer.parseInt(st.nextToken());
               Orcamento = Integer.parseInt(st.nextToken());
            
               custo = new int[NPratos];
               lucro = new int[NPratos];
               vetPratos = new Prato[NPratos];
                        
               for(int i = 0; i < NPratos; i++)
               {       //verificar se esta lendo corretamente.
                  st = new StringTokenizer(br.readLine());
                  custo[i] = Integer.parseInt(st.nextToken());
                  lucro[i] = Integer.parseInt(st.nextToken());
                                //calculo pesoAtual
                                //preenchendo obejto prato
                  vetPratos[i] = new Prato(custo[i],lucro[i],i);
               }
                       
               gu.guloso();
            }
         }       
      }
   //                long memoria_final  = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
   //               long tempo_final =  System.currentTimeMillis();
   //               System.out.println("Memoria usada: " + (memoria_final - memoria_inicial) + "kb");
   //               System.out.println("Tempo gasto: " + ((tempo_final - tempo_inicial) - 1000) + "ms");
   //               System.out.println("Dias: "+ NDias +"--- Pratos: "+NPratos);
   //               Instant end = Instant.now();
   //               System.out.println(Duration.between(start, end)); 
   }  
}
    
class Prato implements Comparable<Prato>
{
   private int custo, usado,posicao;
   private double pesoAtual,lucroAtual,pesoInicial,lucroInicial;

   Prato(int c, double l,int p)
   {
      custo = c;
      lucroAtual = l;
      lucroInicial = lucroAtual;
      usado = 0;
      posicao = p + 1;// printar pratos usados 
      pesoAtual = (double)l/(double)c;
      pesoInicial = pesoAtual;
   }
        // ordenar vetor por lucroAtual 
   @Override
        public int compareTo(Prato p){
        //ordenar vetor por pesoAtual ordem decrescente    
      final int xcomp = Double.compare(p.getPesoAtual(),this.getPesoAtual());
      return xcomp;
      
   }
        
   public void setCusto(int custo){
      this.custo = custo;
   }
        
   public void setLucroAtual(double lucroAtual){
      this.lucroAtual = lucroAtual;
   }
        
   public void setLucroInicial(double lucroInicial){
      this.lucroInicial = lucroInicial;
   }
        
   public void setUsado(int usado){
      this.usado = usado;
   }

   public void setPosicao(int posicao){
      this.posicao = posicao;
   }
        
   public void setPesoAtual(double pesoAtual){
      this.pesoAtual = pesoAtual;
   }
        
   public void setPesoInicial(double pesoInicial){
      this.pesoInicial = pesoInicial;
   }

        
   public double getLucroAtual(){
      return this.lucroAtual;
   }
        
   public double getLucroInicial(){
      return this.lucroInicial;
   }
        
   public int getCusto(){
      return this.custo;
   }
        
   public int getUsado(){
      return this.usado;
   }
        
   public int getPosicao(){
      return this.posicao;
   }
        
   public double getPesoAtual(){
      return this.pesoAtual;
   }
   public double getPesoInicial(){
      return this.pesoInicial;
   }
        
}