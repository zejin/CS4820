import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FrameworkFlowTest
	{
	public class Edge
	{
		int headNode;
		int tailNode;
		int capacity = 0;
		int flow = 0;
		Edge originalEdge;
		boolean isForwardEdge;
		
		public Edge( int tN, int hN, int c )
		{
			tailNode = tN;
			headNode = hN;
			capacity = c;
		}
	}
	
	int n;
	int s; // the number of node s 
	int t; // the number of node t
	Edge adjacencyList[][];
	// for every node numbered i adjacencyList[i][.] contains all edges that have node numbered i as its tail
	int maxCapacity;

	void input(String input_name)
	{
		File file = new File(input_name);
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));

			String text = reader.readLine();
			n=Integer.parseInt( text );
			s=Integer.parseInt( reader.readLine() );
			t=Integer.parseInt( reader.readLine() );
			adjacencyList = new Edge[n][];
			String [] parts;
			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				parts=text.split(" ");
				adjacencyList[i]=new Edge[parts.length/2];
				for (int j=0;j<parts.length/2;j++)
				{
					adjacencyList[i][j] = new Edge( i, Integer.parseInt(parts[j*2]), Integer.parseInt(parts[j*2+1]) );
					if ( maxCapacity < adjacencyList[i][j].capacity )
						maxCapacity = adjacencyList[i][j].capacity;
				}
			}
			reader.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public FrameworkFlowTest(String []Args)
	{
		int sum1 = 0;
		int sum2 = 0;
		input(Args[0]);
		Edge e;
		for (int i = 0; i < adjacencyList[s].length;i++){
			e = adjacencyList[s][i];
			sum1 = sum1 + e.capacity;
		}
		input(Args[1]);
		for (int i = 0; i < adjacencyList[s].length;i++){
			e = adjacencyList[s][i];
			sum2 = sum2 + e.capacity;
		}
		System.out.println("Official Solution's Flow : " + sum1);
		System.out.println("    Your Solution's Flow : " + sum2);
		if (sum1 == sum2){	
			System.out.println("Your solution may be correct!");
		} else {
			System.out.println("Your solution is probably wrong. Sorry ;(");
		}
	}
	public static void main(String [] Args) //Strings in Args are the name of the input file followed by the name of the output file
	{
		new FrameworkFlowTest(Args);
	}
}
