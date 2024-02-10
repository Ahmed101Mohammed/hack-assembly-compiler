import java.util.HashMap;

public class LableCompiler implements ICompiler{
    public static void main(String[] args) {
        HashMap<String, Integer> some = new HashMap<>();
        new LableCompiler("(Loop)",66,some);
        new LableCompiler("(End)",032,some);
        new LableCompiler("(End_eg)",29322,some);
        new LableCompiler("(Mux)",636,some);

        System.out.println(some);
    }

    public LableCompiler(String lable, int count, HashMap<String,Integer> globalData)
    {
        String[] parsing = this.parsing(lable);
        globalData.put(parsing[0],count);
    }

    @Override
    public String[] parsing(String command) {
        String[] parsing = new String[1];
        String lableName = "";
        for(char c :command.toCharArray())
        {
            if(c !='(' && c != ')')
            {
               lableName += c; 
            }
        }
        parsing[0] = lableName;
        return parsing;
    }
    
    @Override
    public String convertToBinary() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getCommand() {
        // TODO Auto-generated method stub
        return null;
    }
}
