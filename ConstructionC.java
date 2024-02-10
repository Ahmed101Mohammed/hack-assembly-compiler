import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ConstructionC implements ICompiler{
    private String command;
    private ArrayList<String> predefinedDest = new ArrayList<>(Arrays.asList("", "M", "D", "MD", "A", "AM", "AD", "AMD"));
    private ArrayList<String> predefinedDestBinary = new ArrayList<>(Arrays.asList("000", "001", "010","011", "100", "101", "110", "111"));
    private ArrayList<String> predefinedJMP = new ArrayList<>(Arrays.asList("", "JGT", "JEQ", "JGE", "JLT", "JNE", "JLE", "JMP"));
    private ArrayList<String> predefinedJMPBinary = new ArrayList<>(Arrays.asList("000", "001", "010", "011", "100", "101", "110", "111"));
    private ArrayList<String> predefinedCompare = new ArrayList<>(Arrays.asList("0", "1", "-1", "D", "A", "!D", "!A", "-D", "-A", "D+1", "A+1", "D-1", "A-1", "D+A", "D-A", "A-D", "D&A", "D|A", "M", "!M", "-M", "M+1", "M-1", "D+M", "D-M", "M-D", "D&M", "D|M"));
    private ArrayList<String> predefinedCompareBinary = new ArrayList<>(Arrays.asList("0101010", "0111111", "0111010", "0001100", "0110000", "0001101", "0110001", "0001111", "0110011", "0011111", "0110111", "0001110", "0110010", "0000010", "0010011", "0000111", "0000000", "0010101", "1110000", "1110001", "1110011", "1110111", "1110010", "1000010", "1010011", "1000111", "1000000", "1010101"));
    private HashMap<String,String> predefinedDestHash = new HashMap<>();
    private HashMap<String,String> predefinedJMPHash = new HashMap<>();
    private HashMap<String,String> predefinedCompareHash = new HashMap<>();
    
    public static void main(String[] args) {
        ConstructionC command = new ConstructionC("D=D+A");
        System.out.println(command.convertToBinary());

    }

    public ConstructionC (String command) 
    {
        this.command = command;
        this.prepareDestHash();
        this.prepareJMPtHash();
        this.prepareCompareHash();
    }
    
    @Override
    public String[] parsing(String command) {
        String[] parses = {"", "", ""};
        String part = "";
        int index = 0;
        for(char c : command.toCharArray())
        {
            if(c != '=' && c != ';')
            {
                part += c;
            }

            if (c == '=')
            {
                parses[0] = part;
                part = "";
                index = 1;
            }
            else if (c == ';')
            {
                parses[1] = part;
                part = "";
                index = 2;
            }
        }

        if(index == 1)
        {
            parses[1] = part;
        }
        else
        {
            parses[2] = part;
        }

        return parses;
    }

    @Override
    public String convertToBinary() {
        String operationCode = "111";
        String[] parsingList = this.parsing(this.command);
        String dest = this.predefinedDestHash.get(parsingList[0]);
        String compare = this.predefinedCompareHash.get(parsingList[1]);
        String jmp = this.predefinedJMPHash.get(parsingList[2]);
        String binaryCode = operationCode + compare + dest + jmp;
        return binaryCode;
    }

    private void prepareDestHash()
    {
        for(int i = 0; i < this.predefinedDest.size(); i++)
        {
            this.predefinedDestHash.put(this.predefinedDest.get(i), this.predefinedDestBinary.get(i));
        }
    }

    private void prepareJMPtHash()
    {
        for(int i = 0; i < this.predefinedJMP.size(); i++)
        {
            this.predefinedJMPHash.put(this.predefinedJMP.get(i), this.predefinedJMPBinary.get(i));
        }
    }

    private void prepareCompareHash()
    {
        for(int i = 0; i < this.predefinedCompare.size(); i++)
        {
            this.predefinedCompareHash.put(this.predefinedCompare.get(i), this.predefinedCompareBinary.get(i));
        }
    }

    public String getCommand()
    {
        return this.command;
    }
}
