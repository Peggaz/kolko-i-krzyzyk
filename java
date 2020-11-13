/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kulkoikrzyzyk;
//
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author pegga
 */
public class KulkoIKrzyzyk {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Gra G = new Gra();
        G.start();
    }
    
}

class Gra{
    Stan SA;
    //int[][] Tab;
    final int PUSTE=0, KOMP=1, ON=-1, MAX_POZIOM = 4;
    int Poziom;
    //int LK=5,LW=5;
    Gra(){
        SA = new Stan();
    }
    void start(){
        while(true){
            if(ruchGracza()){
                System.out.println("wygral gracz");
                break;
            }
            if(ruchKomp()){
                System.out.println("wygral komputer");
                break;
            }
            drukujPlansze();
        }
        
    }
    boolean ruchKomp(){
        Poziom = 0;
        ArrayList<Stan> Kandydaci = ekspandujStan(SA,Poziom);
        int Best = Integer.MIN_VALUE;
        int Best_Indeks = -1;
        for(int i = 0; i < Kandydaci.size(); i++){
            Stan s = Kandydaci.get(i);
            int v = minMax(s,Poziom+1);
            if(v>Best){
                Best=v;
                Best_Indeks = i;
            }
        }
        SA = Kandydaci.get(Best_Indeks);
        if(czyWygrana(SA, KOMP))
            return true;
        return false;
    }
    boolean czyWygrana(Stan s, int RUCH){
        boolean t=true;
        if(s.Plansza[0][0]==RUCH){
            for(int i=0; i<s.LW; i++){
                if(s.Plansza[i][i]!=RUCH)
                    t=false;

            }
            if(t)
                return true;
        }
        t=true;
        if(s.Plansza[0][s.LK-1]==RUCH){
            for(int i=0; i<s.LW; i++){
                if(s.Plansza[i][s.LK-1-i]!=RUCH)
                    t=false;
                
            }
            if(t)
                return true;
        }
        t=true;
        for(int i=0; i<s.LW; i++){
                if(s.Plansza[i][0]==RUCH){
                    for(int j=0; j<s.LK; j++)
                        if(s.Plansza[i][j]!=RUCH){
                            t=false;
                            break;
                        }
                    if(t)
                        return true;
                }
                t=true;
            }
        t=true;
        for(int i=0; i<s.LK; i++){
                if(s.Plansza[0][i]==RUCH){
                    for(int j=0; j<s.LW; j++)
                        if(s.Plansza[j][i]!=RUCH){
                            t=false;
                            break;
                        }
                    if(t)
                        return true;
                }
                t=true;
            }
        return false;
    }
    boolean ruchGracza(){
        Scanner sc = new Scanner(System.in);
        
        System.out.println();
        int i;
        int j;
        while(true){
        System.out.println("Podaj wiersz: ");
        i = sc.nextInt();
        System.out.println("Podaj kolumne: ");
        j = sc.nextInt();
        if(SA.Plansza[i][j]==PUSTE){
            break;
        }
        else
            System.out.println("POLE ZAJETE!");
        }
        SA.Plansza[i][j] = ON;
        if(czyWygrana(SA, ON))
            return true;
        return false;
    }
    void drukujPlansze(){
        for(int i=0; i<SA.LW; i++){
            
            System.out.println();
            if(i>0)System.out.println("-----------------");
            for(int j=0; j<SA.LK; j++){
                int c=SA.Plansza[i][j];
                if(c==KOMP) System.out.print("X");
                else if(c==ON) System.out.print("O");
                else System.out.print(" ");
                if(j<(SA.LK-1))System.out.print(" | ");
            } 
        }
    }

    private ArrayList<Stan> ekspandujStan(Stan SA, int level) {
        ArrayList<Stan> Potomkowie = new ArrayList<>();
        for(int i=0; i<SA.LW; i++)
            for(int j=0; j<SA.LK; j++){
                if(SA.Plansza[i][j]==PUSTE){
                    Stan S = new Stan(SA);
                    if(level%2 == 0) S.Plansza[i][j] = KOMP;
                    else S.Plansza[i][j] = ON;
                    Potomkowie.add(S);
                }
            }
        return Potomkowie;
    }
    
    int minMax(Stan s, int p){
        if(p==MAX_POZIOM) return ocenStan(s);
        else if(p%2==0) {
            int Best = Integer.MIN_VALUE;
            ArrayList<Stan> Potomkowie = ekspandujStan(s,p);
            for(Stan S : Potomkowie){
                int val = minMax(s,p+1);
                if(val>Best)
                    Best = val;
            }
            return Best;
        }
        else{
            int Best = Integer.MAX_VALUE;
            ArrayList<Stan> Potomkowie = ekspandujStan(s,p);
            for(Stan S : Potomkowie){
                int val = minMax(s,p+1);
                if(val<Best)
                    Best = val;
            }
            return Best;
        }
    }

    int ocenStan(Stan s){
    int r_KOMP=1;
    int r_ON=-1;
    int wartoscKOMP=1;
    int wartoscON=-1;
    
    int wartoscKOMP1=1;
    int wartoscON1=-1;
    
    
    int[] wartoscKOMP2 = new int[s.LK];
    int[] wartoscON2 = new int[s.LK];
    int[] wartoscKOMP3 = new int[s.LK];
    int[] wartoscON3 = new int[s.LK];
    for(int i=0; i<s.LK; i++)
    {
       wartoscKOMP2[i]=1;
       wartoscKOMP3[i]=1;
       wartoscON2[i]=-10;
       wartoscON3[i]=-1;
    }
    
    int wartosc=0;
        boolean t=true;
            for(int i=0; i<s.LW; i++){
                if(s.Plansza[i][i]!=KOMP){
                    if(s.Plansza[i][i]==ON){
                        wartoscKOMP*=0;
                        break;
                    }
                }
                else
                    wartoscKOMP*=10;
            }
            
            for(int i=0; i<s.LW; i++){
                if(s.Plansza[i][i]!=ON){
                    if(s.Plansza[i][i]==KOMP){
                        wartoscON*=0;
                        break;
                    }
                }
                else
                    wartoscON*=10;
            }
        
            for(int i=0; i<s.LW; i++){
                if(s.Plansza[i][s.LK-1-i]!=KOMP){
                    if(s.Plansza[i][s.LK-1-i]==ON){
                        wartoscKOMP1*=0;
                        break;
                    }
                }
                else
                    wartoscKOMP1*=10;
            }
            
            for(int i=0; i<s.LW; i++){
                if(s.Plansza[i][s.LK-1-i]!=ON){
                    if(s.Plansza[i][s.LK-1-i]==KOMP){
                        wartoscON1*=0;
                        break;
                    }
                }
                else
                    wartoscON1*=10;
            }
        
        
        
        
        for(int i=0; i<s.LW; i++){
            for(int j=0; j<s.LK; j++){
                if(s.Plansza[i][j]!=KOMP){
                    if(s.Plansza[i][j]==ON){
                       wartoscKOMP2[i]=1;
                        break; 
                    }
                }
                    else
                        wartoscKOMP2[i]*=10;
            }
                
        }
        
        for(int i=0; i<s.LW; i++){
            for(int j=0; j<s.LK; j++){
                if(s.Plansza[i][j]!=ON){
                    if(s.Plansza[i][j]==KOMP){
                       wartoscON2[i]=-1;
                        break; 
                    }
                }
                    else
                        wartoscON2[i]*=10;
            }
                
        }
        
        
        for(int i=0; i<s.LK; i++){
            for(int j=0; j<s.LW; j++){
                if(s.Plansza[j][i]!=KOMP){
                    if(s.Plansza[j][i]==ON){
                       wartoscKOMP3[i]=1;
                        break; 
                    }
                }
                    else
                        wartoscKOMP3[i]*=10;
            }
                
        }
        
        for(int i=0; i<s.LK; i++){
            for(int j=0; j<s.LW; j++){
                if(s.Plansza[j][i]!=ON){
                    if(s.Plansza[j][i]==KOMP){
                       wartoscON3[i]=-1;
                        break; 
                    }
                }
                    else
                        wartoscON3[i]*=10;
            }
                
        }
        
           wartosc = wartoscKOMP + wartoscON + wartoscKOMP1 + wartoscON1;
           for(int i=0; i<s.LK; i++){
            wartosc += wartoscKOMP2[i] + wartoscON2[i] + wartoscKOMP3[i] + wartoscON3[i];
           }
        return wartosc;
}
}
class Stan{
    int[][] Plansza;
    final int LK=5, LW=5;//LW liczba Wierszy; LK liczba wierszy
    Stan(){
    Plansza = new int[LK][LW];
}
    Stan(Stan S){
        Plansza = new int[LK][LW];
        for(int i=0; i<LW; i++)
            for(int j=0; j<LK; j++)
                Plansza[i][j]=S.Plansza[i][j];
    }
}
