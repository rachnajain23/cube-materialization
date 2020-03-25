import javax.swing.plaf.synth.SynthLookAndFeel;
import java.io.IOException;

public class Main {
    String baseCuboid[][];
    int r, c;
    String dimensions[];
    public static void main(String[] args) throws IOException {
        Input x = new Input();
        System.out.println(true==x.readFromExcel("/home/gauri/Desktop/IIITB/DM/Project/cubematerialization/src/main/java/sample1.xlsx", "sheet1")
                ? "successful base cube creation": "unsuccessful base cube creation");
//        for(int i=0; i<x.r;++i){
//            for(int j=0; j<x.c; ++j){
//                System.out.print(x.baseCuboid[i][j]);
//            }
//            System.out.println();
//        }

    }
}
