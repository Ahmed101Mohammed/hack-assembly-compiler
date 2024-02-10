import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class main {
    static ArrayList<String> predefinedVars = new ArrayList<>(Arrays.asList("R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10", "R11", "R12", "R13", "R14", "R15", "SCREEN", "KBD", "SP", "LCL", "ARG", "THIS", "THAT"));
    static ArrayList<Integer> predefinedVarsBinary = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16384, 24576, 0, 1, 2, 3, 4));
    static HashMap<String,Integer> predefinedVarsHash = new HashMap<>();
    static ArrayList<ICompiler> commands = new ArrayList<>();
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Write file name without extension: ");
        String fileName = userInput.nextLine();
        BufferedReader reader;
        try
        {
            prepareHash();
            reader = new BufferedReader(new FileReader(fileName+".asm"));
            String line = reader.readLine();
            while(line != null)
            {
                line = line.trim();
                char[] lineArr = line.toCharArray();
                if(line.length() != 0 && lineArr[0] != '/')
                {
                    if(lineArr[0] == '(')
                    {
                        new LableCompiler(line, commands.size(), predefinedVarsHash);
                    }
                    else if (lineArr[0] == '@')
                    {
                        ConstructionA newA = new ConstructionA(line, predefinedVarsHash);
                        commands.add(newA);
                    }
                    else
                    {
                        ConstructionC newC = new ConstructionC(line);
                        commands.add(newC);
                    }
                }                
                line = reader.readLine();
            }
            reader.close();

            for (ICompiler command : commands)
            {
                System.out.println(command.getCommand());
            }
            System.out.print(predefinedVarsHash);
        }
        catch(IOException e)
        {
            System.out.println("Error: "+ e.getMessage());
        }

        try
        {
            File hack = new File(fileName+".hack");
            if(hack.createNewFile())
            {
                System.out.println(fileName+".hack"+ " is created.");
            }
            else
            {
                System.out.println(fileName+".hack"+ " is allready Exist.");
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            FileWriter hack = new FileWriter(fileName+ ".hack");
            hack.write(fileContetnPrepare());
            hack.close();
        }   
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    static void prepareHash()
    {
        for(int i = 0; i < predefinedVars.size(); i++)
        {
            predefinedVarsHash.put(predefinedVars.get(i), predefinedVarsBinary.get(i));
        }
    }

    static String fileContetnPrepare()
    {
        String result = "";
        for(ICompiler command: commands)
        {
            result += command.convertToBinary()+"\n";
        }
        return result;
    }
}