import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class FrameworkKnapsack
{
    int n;
    int v[];
    int w[];
    int W;
    double beta;
    boolean picked[];
    void input(String input_name)
    {
        File file = new File(input_name);
        BufferedReader reader = null;

        try 
        {
            reader = new BufferedReader(new FileReader(file));

            String text = reader.readLine();
            String parts[];
            parts=text.split(" ");
            n=Integer.parseInt(parts[0]);
            W=Integer.parseInt(parts[1]);
            v=new int[n];
            w=new int[n];
            picked=new boolean[n];
            for (int i=0;i<n;i++)
            {
                text=reader.readLine();
                parts=text.split(" ");
                v[i]=Integer.parseInt(parts[0]);
                w[i]=Integer.parseInt(parts[1]);
            }
            reader.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    //writing the output
    void output(String output_name)
    {
        try
        {
            PrintWriter writer = new PrintWriter(output_name, "UTF-8");

            int total_v=0;
            for (int i=0;i<n;i++)
              if (picked[i])
                total_v += v[i];
            writer.println(total_v);
            for (int i=0;i<n;i++)
              if (picked[i])
                writer.println("1");
              else 
                writer.println("0");

            writer.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Integer[] sortedIndices( final double[] a )
    {
        Integer[] idx = new Integer[ n ];
        for ( int i = 0 ; i < a.length ; i++ )
        	idx[ i ] = new Integer( i );
        Arrays.sort
        ( idx, new Comparator<Integer>() 
	        {
			    @Override public int compare( final Integer i1, final Integer i2) 
			    {
			        return Double.compare( a[ i1 ], a[ i2 ] );
			    }
		    }
	    );
	    return idx;
    }
    
    public Integer[] sortedIndices( final int[] a )
    {
        Integer[] idx = new Integer[ n ];
        for ( int i = 0 ; i < a.length ; i++ )
        	idx[ i ] = new Integer( i );
        Arrays.sort
        ( idx, new Comparator<Integer>() 
	        {
			    @Override public int compare( final Integer i1, final Integer i2) 
			    {
			        return Integer.valueOf(a[i1]).compareTo(Integer.valueOf(a[i2]));
				    //Integer.compare( a[ i1 ], a[ i2 ] );
			    }
		    }
	    );
	    return idx;
    }

    public FrameworkKnapsack(String []Args)
    {
        beta = Double.parseDouble( Args[ 0 ] );
        input(Args[ 1 ]);

// YOUR CODE STARTS HERE
     // max value
        int vMax = v[0];
        for (int i=1; i<n; i++) {
        	if (v[i] > vMax) {
        		vMax = v[i];
        	}
        }
        
        // D
        double eps = 1 / beta - 1;
        double D = eps * vMax / n;
        
        // hat value
        for (int i=0; i<n; i++) {
        	v[i] = (int) Math.ceil(v[i] / D);
        }   
        
        // calculate density
        double[] dd = new double[n];
        for (int i=0; i<n; i++) {
        	dd[i] = (double) -v[i] / w[i];
        }
        
        int[] vv = new int[n];
        for (int i=0; i<n; i++) {
        	vv[i] = -v[i];
        }
        
//        for (int i=0; i<n; i++) {
//        	System.out.println(dd[i]);
//        }
        
        // sort density and value
        Integer[] dOrder = sortedIndices(dd);
        Integer[] vOrder = sortedIndices(vv);
        
//        for (int i=0; i<n; i++) {
//        	System.out.println(dOrder[i]);
//        }
//        for (int i=0; i<n; i++) {
//        	System.out.println(vOrder[i]);
//        }
        
        int k;
        
        // run GA 
        int wGA = 0;
        int vGA = 0;
        k = 0;
        while (k < n && wGA + w[dOrder[k]] <= W) {
        	wGA += w[dOrder[k]];
        	vGA += v[dOrder[k]];
        	k += 1;
        }
        
        // run EMGA
        int wEMGA = 0;
        int vEMGA = 0;
        k = 0;
        while (k < n && wEMGA + w[vOrder[k]] <= W) {
        	wEMGA += w[vOrder[k]];
        	vEMGA += v[vOrder[k]];
        	k += 1;
        }
        
//        System.out.println(wGA);
//        System.out.println(vGA);
//        System.out.println(wEMGA);
//        System.out.println(vEMGA);
        
        // take max of GA and EMGA
        int vAPT;
        if (vGA >= vEMGA) {
        	vAPT = vGA;
        } else {
        	vAPT = vEMGA;
        }
        
//        System.out.println(vAPT);
        
        // DP
        int[][] M = new int[n+1][2*vAPT+1];
        
        for (int i=0; i<n+1; i++) {
        	M[i][0] = 0;
        }
        
        for (int j=1; j<2*vAPT+1; j++) {
        	M[0][j] = W + 1;
        }
        
//	    for (int i=0; i<n+1; i++) {
//	    	for (int j=0; j<2*vAPT+1; j++) {
//	    		System.out.println(M[i][j]);
//	    	}	
//	    }
        
        int first;
        int second;
        for (int i=1; i<n+1; i++) {
        	for (int j=1; j<2*vAPT+1; j++) {
//        		System.out.println(i);
//        		System.out.println(j);
        		
            	if (j < v[i-1]) {
//            		System.out.println(w[i-1]);
//            		System.out.println(i-1);
//            		System.out.println(j-v[i-1]);
            		
            		M[i][j] = M[i-1][j];
            	} else {
            		// first term in min
            		first = M[i-1][j]; 
            		
            		// second term in min
            		second = M[i-1][j-v[i-1]] + w[i-1];
            		
            		if (first <= second) {
            			M[i][j] = first;
            		} else {
            			M[i][j] = second;
            		}
            	}
            }
        }
        
//        for (int i=0; i<n+1; i++) {
//        	for (int j=0; j<2*vAPT+1; j++) {
//        		System.out.println(M[i][j]);
//        	}	
//        }
        
        // find optimal
        int vOPT = 0;
        for (int j=2*vAPT; j>0; j--) {
        	if (M[n][j] <= W) {
        		vOPT = j;
        		break;
        	}
        }

//        System.out.println(vOPT);
        
        // backtrack solution
        int j = vOPT;
        for (int i=n; i>0; i--) {
        	if (M[i][j] != M[i-1][j]) {
//        		System.out.println(i);
        		picked[i-1] = true;
        		j -= v[i-1];
        	}
        }
        
// YOUR CODE ENDS HERE

        output(Args[ 2 ]);
    }

    public static void main(String [] Args) //Strings in Args are 1. beta, 2. the name of the input file and 3. the name of the output file
    {
        new FrameworkKnapsack(Args);
    }
}
