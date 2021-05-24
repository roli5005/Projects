import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MathChallenge {
    public static void main(String[] args) {

        String input = "26712";
        int size = input.length();
        List<String> listOne = Arrays.asList("-", "+");
        List<List<String>> totalList =  new ArrayList<>();
        int[] digits = new int[size];
        for(int i=0;i<size;i++) {
            digits[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
        }

        for(int i=0;i<size-1;i++)
        {
            totalList.add(listOne);
        }

        new MathChallenge().findSolution(digits,totalList);
    }


    public void findSolution(int[] digits, List<List<String>> totalList) {
        List<String> result = new ArrayList<String>(totalList.get(0));

        for(int index = 1; index < totalList.size(); index++) {
            result = combineTwoLists(result, totalList.get(index));
        }
        int max = 0;
        String solution ="";
        for(String s: result) {
            if(sum(digits,s)==0 && numberOfMinus(s)>max) {
                solution = s;
                max = numberOfMinus(s);
            }
        }
        System.out.print(solution);
    }

    public int numberOfMinus(String s){
        int nr=0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='-')nr++;
        }
        return nr;
    }

    public int sum(int[] number, String operations){
        int s = number[0];
        for(int i=0;i<number.length-1;i++){
            if(operations.charAt(i)=='+')s += number[i+1];
            else s -= number[i+1];
        }
        return s;
    }

    private List<String> combineTwoLists(List<String> list1, List<String>   list2) {
        List<String> result = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for(String s1 : list1) {
            for(String s2: list2) {
                sb.setLength(0);
                sb.append(s1).append(s2);
                result.add(sb.toString());
            }
        }
        return result;
    }
}
