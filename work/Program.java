
public class Program {
    public static void main(String[] args){
        CoffeMachine bosh = new CoffeMachine();
        bosh.on();
        System.out.println(bosh.getState());
      //  bosh.off();
        bosh.print_menu();
        bosh.add_money(35);
        bosh.print_state();
        bosh.choice(4);
      //  bosh.cancel();
        bosh.cook();
      //  bosh.finish();
        bosh.print_state();
        bosh.add_money(15);
        bosh.choice(2);
        bosh.cook();

        bosh.finish();


        //bosh.showdep();

    }
}
