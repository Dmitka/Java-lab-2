import java.io.*;

import static java.lang.Thread.sleep;

public class AutomataDemo {
    public enum State{ OFF, WAIT, ACCEPT, CHECK, COOK};
    int cash; //для хранения текущей суммы
    String menu[]; //массив строк названий напитков (может подгружаться из файла)
    int prices[]; // массив цен напитков (соответствует массиву menu)
    State state;
    int numbOfKinds;
    public AutomataDemo(){
        cash=0;
        menu=new String[10];
        prices=new int[10];
        state = State.OFF;
        File file1 = new File("menu.txt");
        File file2 = new File("prices.txt");
        String line;
        int i=0;

        FileReader reader=null;
        FileReader reader2=null;
        try {
            reader = new FileReader(file1);
            BufferedReader breader = new BufferedReader(reader);
            while ((line = breader.readLine()) != null) {
                menu[i] = line;
                i++;
            }
        }
        catch(IOException e){
            System.out.println("Ошибка");
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Ошибка");
                }
            }
        }
        numbOfKinds=i;
        i=0;
        try {
            reader2 = new FileReader(file2);
            BufferedReader breader2 = new BufferedReader(reader2);
            while ((line = breader2.readLine()) != null) {
                prices[i] = Integer.valueOf(line);
                i++;
            }
        }
        catch(IOException e){
            System.out.println("Ошибка");
        }
        finally {
            if (reader2 != null) {
                try {
                    reader2.close();
                } catch (IOException e) {
                    System.out.println("Ошибка");
                }
            }
        }
    }
    public void on(){
        if (state==State.OFF) {
            state=State.WAIT;
            printMenu();
            printState();
        }
    }
    public void off(){
        if (state==State.WAIT){
            state=State.OFF;
            printState();
        }
    }
    public void coin(int value){
        if (state==State.WAIT){
            state=State.ACCEPT;
            printState();
            cash+=value;
            System.out.printf("You put %d rub\n",value);
        }
        else if (state==State.ACCEPT) {
            cash+=value;
            System.out.printf("You put %d rub\n",value);
            System.out.printf("Money: %-2d\n",cash);
        }
    }
    public void printMenu(){
        System.out.printf("       Menu:      \n");
        for (int i=0;i<numbOfKinds;i++) {
            System.out.printf("%2d) %-12s  %3d rubles\n",i+1,menu[i],prices[i]);
        }
    }
    public void printState(){
        System.out.printf("My state is %s\n",state);
    }
    public void choice(int number){
        if (state==State.ACCEPT) {
            number--;
            if ((number >= 0) && (number < numbOfKinds))
            {
                System.out.printf("Your choice is: %-12s\n",menu[number]);
                state = State.CHECK;
                printState();
                if (check(number)) cook(number);
                else {
                    System.out.printf("It's not enough money. Your drink costs %d rub, but you put %d rub\n",prices[number],cash);
                    cancel();
                }
            }
        }
    }
    private boolean check(int number){
        if (state==State.CHECK) if (cash>=prices[number]) return true;
        else return false;
        return false;
    }
    public void cancel(){
        if ((state==State.ACCEPT)||(state==State.CHECK)){
            cash=0;
            state=State.WAIT;
            printState();
        }
    }
    private void cook(int number){
        if (state==State.CHECK) {
            state=State.COOK;
            printState();
            System.out.printf("Wait,please. %s is cooking", menu[number]);
            for (int i = 1; i <= 10; i++) {
                System.out.print(".");
                this.sleep(1000);
            }
            System.out.println();
            finish();
        }
    }
    private void finish(){
        if (state==State.COOK) {
            cash = 0;
            state = State.WAIT;
            printState();
        }
    }

    private static void sleep(int time){
        try{
            Thread.sleep(time);
        }catch(Exception e){System.out.println("It's some problems with timer");}
    }
}
