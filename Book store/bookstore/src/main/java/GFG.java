import java.util.Stack;

public class GFG
{

    public int DFS(int[][] grid) {
        int found = 0;
        int h = grid.length;
        int l = grid[0].length;

        boolean[][] visited = new boolean[h][l];

        Stack<String> stack = new Stack<>();

        stack.push(0 + "," + 0);

        //System.out.println("Depth-First Traversal: ");
        while (stack.empty() == false) {

            String x = stack.pop();
            int row = Integer.parseInt(x.split(",")[0]);
            int col = Integer.parseInt(x.split(",")[1]);

            if(row == h-1 && col== l-1){
                found = 1;
                //System.out.print("found");
                continue;

            }
            if(row<0 || col<0 || row>=h || col>=l || visited[row][col] || grid[row][col]==0)
                continue;

            visited[row][col]=true;
            //System.out.println(row+"/"+col+"-> " + grid[row][col] + " ");
            stack.push(row + "," + (col-1)); //go left
            stack.push(row + "," + (col+1)); //go right
            stack.push((row-1) + "," + col); //go up
            stack.push((row+1) + "," + col); //go down


        }
        return found;
    }

    public String possibilities(int[][] grid){
        int h = grid.length;
        int l = grid[0].length;
        int nr = 0;
        for(int i = 0;i<h;i++)
            for (int j=0;j<l;j++)
            {
                if(grid[i][j]==0){
                    grid[i][j] = 1;
                    if(DFS(grid)==1){nr++;
                    System.out.println(i+"/"+j);}
                    grid[i][j] = 0;
                }
            }
        if(nr==0) return "not possible";
        else return ""+nr;
    }

    public int[][] createArray(String[] input){
        int row = input.length;
        int col = input[0].length();
        int[][] arr = new int[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++)
            {
                arr[i][j] = Integer.parseInt(String.valueOf(input[i].charAt(j)));
            }
        }
        return arr;
    }


    public static void main(String[] args) {

        String[] input = {"1000001","1001111","1010101"};
        GFG d = new GFG();
        int [][] grid = d.createArray(input);
        //System.out.println(d.DFS(grid));
        System.out.print(d.possibilities(grid));
    }
}
// This code is contributed
// by prerna saini
