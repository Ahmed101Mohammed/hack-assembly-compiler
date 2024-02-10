import java.util.HashMap;

public class ConstructionA implements ICompiler{
    private String command;
    private HashMap<String,Integer> globalData;
    static int lastVarLocation = 15;    

    public static void main(String[] args) {
        HashMap<String,Integer> some = new HashMap<>();
        ConstructionA command = new ConstructionA("@345", some);
        ConstructionA command1 = new ConstructionA("@3495", some); 
        ConstructionA command2 = new ConstructionA("@ui8", some);
        ConstructionA command3 = new ConstructionA("@nui", some);
        ConstructionA command4 = new ConstructionA("@ui8", some);
        System.out.println(command.convertToBinary());
        System.out.println(command1.convertToBinary());
        System.out.println(command2.convertToBinary());
        System.out.println(command3.convertToBinary());
        System.out.println(command4.convertToBinary());
        System.out.println(some.get("hi"));
    }
    
    public ConstructionA(String command, HashMap<String,Integer> data)
    {
        this.command = command;
        this.globalData = data;
    }

    @Override
    public String[] parsing(String command) {
        String varOrNum = "";
        for(char c : command.toCharArray())
        {
            if(command.indexOf(c) != 0)
            {
                varOrNum += c;
            }
        }
        String[] result = new String[1];
        result[0] = varOrNum;
        return result;
    }

    @Override
    public String convertToBinary() {
        String constructionCode = "0";
        String addressInBinary = this.getNumberInBinary(this.parsing(this.command)[0]);
        String result = constructionCode + addressInBinary;
        return result;
    }

    private String getNumberInBinary(String number)
    {
        try
        {
            int decimalNum = Integer.parseInt(number);
            String binary = Integer.toBinaryString(decimalNum);
            return this.fillRestWithZeros(binary);
        }
        catch(NumberFormatException e)
        {
            int varValue;
            if(this.globalData.get(number) == null)
            {
                this.globalData.put(number, lastVarLocation + 1);
                lastVarLocation += 1;
                varValue = this.globalData.get(number);
            }
            else
            {
                varValue = this.globalData.get(number);
            }
            
            String binary = Integer.toBinaryString(varValue);
            return this.fillRestWithZeros(binary);
        }
    }

    private String fillRestWithZeros(String binaryCode)
    {
        int rest = 15 - binaryCode.length();
        String restPart = "";
        for(int i = 0; i < rest; i++)
        {
            restPart += "0";
        }
        String result = restPart+binaryCode;
        return result;
    }

    public String getCommand()
    {
        return this.command;
    }
    
}
