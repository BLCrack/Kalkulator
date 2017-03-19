package pl.lorenc.calc;

import org.apache.commons.lang3.StringUtils;
import org.mariuszgromada.math.mxparser.Expression;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Kalkulator
{
    private double wynik;
    private String dzialanie;

    public String getDzialanie()
    {
        return dzialanie;
    }

    public void setDzialanie(String dzialanie)
    {
        this.dzialanie = dzialanie;
    }

    public double getWynik()
    {
        return wynik;
    }
    public void setWynik(double wynik)
    {
        this.wynik = wynik;
    }

    public Kalkulator()
    {
        this.wynik=0;
        this.dzialanie="";
    }

    public void oblicz()        //metoda napisana ręcznie obliczająca wynik bez poprawnej kolejnosci wykonywania dzialan
    {
        StringTokenizer tokenizer = new StringTokenizer(this.dzialanie,"+-/*",true);
        String token="";
        String znak="";
        double liczba=0;
        boolean pierwsza = true;

        while(tokenizer.hasMoreTokens())
        {
            token = tokenizer.nextToken();
            if(!token.equals("+") && !token.equals("-") && !token.equals("*") && !token.equals("/"))
            {
                liczba = Double.parseDouble(token);
                if(znak.equals("+"))
                    this.wynik+=liczba;
                else if(znak.equals("-"))
                    this.wynik-=liczba;
                else if(znak.equals("*"))
                    this.wynik*=liczba;
                else if(znak.equals("/"))
                    this.wynik/=liczba;
            }
            else
            {
                znak = token;
            }
            if(pierwsza)
            {
                this.wynik=liczba;
                pierwsza=false;
            }
        }
    }

    public void obliczZKolejnoscia()        //metoda z poprawnym priorytetem działan
    {
        Expression obliczenie = new Expression(dzialanie);
        this.wynik=obliczenie.calculate();
    }

    public void wczytujZKonsoli()
    {
        Scanner in = new Scanner(System.in);

        while(true)
        {
            System.out.println("Podaj dzialanie: ");
            this.dzialanie = in.nextLine();
            if(this.dzialanie.equals("koniec"))
                break;
            else if(!StringUtils.containsOnly(this.dzialanie,"0123456789+-/*"))
            {
                System.out.println("Bledne znaki w dzialaniu!!!");
                continue;
            }
            else
            {
                this.wynik=0;
                //oblicz();
                obliczZKolejnoscia();
            }
            System.out.println(this.dzialanie+"="+this.wynik);
        }

    }

    public void wczytujZPliku() throws FileNotFoundException
    {
        Scanner inConsole = new Scanner(System.in);
        String nazwaPliku="";

        System.out.println("Podaj nazwe pliku: ");
        nazwaPliku=inConsole.nextLine();

        File plik = new File(nazwaPliku);
        Scanner in = new Scanner(plik);

        while(true)
        {
            this.dzialanie = in.nextLine();
            if(this.dzialanie.equals("koniec"))
                break;
            else if(!StringUtils.containsOnly(this.dzialanie,"0123456789+-/*"))
            {
                System.out.println("Bledne znaki w dzialaniu!!!");
                continue;
            }
            else
            {
                this.wynik=0;
                //oblicz();
                obliczZKolejnoscia();
            }
            System.out.println(this.dzialanie+"="+this.wynik);
        }
    }

    public void menuGlowne()
    {
        Scanner in = new Scanner(System.in);
        int wybor=0;

        System.out.println("KALKULATOR.");
        while(true)
        {
            System.out.println("Czytanie z konsoli - wcisnij 1");
            System.out.println("Czytanie z pliku - wcisnij 2");
            System.out.println("Koniec programu - wcisnij 3");
            wybor = in.nextInt();
            if(wybor==1)
            {
                wczytujZKonsoli();
            }
            else if(wybor==2)
            {
                try
                {
                    wczytujZPliku();
                }
                catch (FileNotFoundException e)
                {
                    System.out.println("Problem z plikiem");
                }
            }
            else if(wybor==3)
                break;
            else
                System.out.println("Nieprawidlowy znak!!!");
        }
    }
}
