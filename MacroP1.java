import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MacroP1 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("macro_input.asm"));

        FileWriter mnt = new FileWriter("mnt.txt");
        FileWriter mdt = new FileWriter("mdt.txt");
        FileWriter ir = new FileWriter("intermediate.txt");
        
        String line;
        String Macroname = null;
        int mdtp = 1, paramNo = 1, pp = 0, flag = 0;

        while ((line = br.readLine()) != null) {
            String parts[] = line.split("\\s+");
            
            if (parts[0].equalsIgnoreCase("MACRO")) {
                flag = 1;
                line = br.readLine();
                parts = line.split("\\s+");
                Macroname = parts[0];
                if (parts.length > 1) {
                    for (int i = 1; i < parts.length; i++) {
                        parts[i] = parts[i].replaceAll("[&,]", "");
                        if (parts[i].contains("=")) {
                            // Process keyword parameters if needed
                        } else {
                            pp++;
                        }
                    }
                }
                mnt.write(parts[0] + "\t" + pp + "\t" + mdtp + "\n");

            } else if (parts[0].equalsIgnoreCase("MEND")) {
                mdt.write(line + "\n");
                flag = pp = 0;
                mdtp++;
            } else if (flag == 1) {
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].contains("&")) {
                        parts[i] = parts[i].replaceAll("[&,]", "");
                        mdt.write("(P," + paramNo++ + ")\t");
                    } else {
                        mdt.write(parts[i] + "\t");
                    }
                }
                mdt.write("\n");
                mdtp++;
            } else {
                ir.write(line + "\n");
            }
        }

        br.close();
        mdt.close();
        mnt.close();
        ir.close();
        System.out.println("Macro Pass 1 Processing done. :)");
    }
}















































































































MACRO
M1	&X, &Y, &A=AREG, &B=
MOVER	&A, &X
ADD	&A, ='1'
MOVER	&B, &Y
ADD	&B, ='5'
MEND
MACRO
M2	&P, &Q, &U=CREG, &V=DREG
MOVER	&U, &P
MOVER	&V, &Q
ADD	&U, ='15'
ADD	&V, ='10'
MEND
START	100
M1	10, 20, &B=CREG
M2	100, 200, &V=AREG, &U=BREG
END





