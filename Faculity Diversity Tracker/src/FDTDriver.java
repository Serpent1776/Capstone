//import java.sql.*;
public class FDTDriver {
    public static void main(String[] args) throws Exception {
        HomeGUI gui = new HomeGUI();
        //SQLProcessor SQLpro = new SQLProcessor();
        gui.open();
        //igui.open();
        gui.showDataType("Faculty Member");
        boolean open = true;
        while(open) {
            System.out.print("");
            if(gui.isClosed()) {
                    open = false;
            }
        }
        System.exit(0);
    }
}
