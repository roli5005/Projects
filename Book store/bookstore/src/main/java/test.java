import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String args[])
    {
        String input = "sskfssbbb9bbb";
        int k = 2;
        HashMap<String,Integer> patterns = new HashMap<>();
        while(k<input.length()-1){
            for(int i=0;i<input.length()-k+1;i++){
                String key = input.substring(i,i+k);
                if(patterns.containsKey(key))
                    patterns.put(key,patterns.get(key)+1);
                else
                    patterns.put(key,1);
            }
            k++;
        }
        System.out.println(patterns);
        int max =0, maxlength = 0;
        String key = "";
        for (Map.Entry<String,Integer> x:patterns.entrySet()
             ) {
                if(x.getValue()>1 && x.getValue()>max && x.getKey().length()>maxlength) {key = x.getKey(); max = x.getValue(); maxlength = key.length();}
        }
        if(max != 0)
        System.out.print("yes "+key);
        else
            System.out.print("no null");
    }

}
