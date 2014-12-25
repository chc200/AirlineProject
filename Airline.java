import java.util.*;
import java.io.*;
import java.util.Iterator;
import java.io.PrintWriter;
public class Airline{
	
public static int totalEdges=0;

	public static void main(String [] args) throws IOException
	{

		Scanner sc = new Scanner(System.in);
		System.out.println("What file are you using? ");
		String file = sc.next();



		Scanner scFile = new Scanner(new File(file));
		
		int len = Integer.parseInt(scFile.next());
		
		//Node [] arr = new Node[len];

		LinkedList<Node> adList = new LinkedList<Node>();

		for(int i = 0; i <len; i++)
		{
			String temp = scFile.next();
			Node tempNode = new Node(temp);
			adList.add(tempNode);
		}

		int start=0;
		int dest=0; 
		int weight=0;
		double cost=0;
		int index=0;
		while(scFile.hasNext())
		{
			for(int i=0; i<4; i++)
			{
				String line = scFile.next();
				if(i==0)
					start=Integer.parseInt(line);
				if(i==1)
					dest=Integer.parseInt(line);
				if(i==2)
					weight=Integer.parseInt(line);
				if(i==3)
					cost=Double.parseDouble(line);

			}
			Edge tempEdge = new Edge(start,dest,weight,cost);
			Node tempNode = new Node(tempEdge);

			index=start-1;
			Node nextNode = adList.get(index);
			while(nextNode.next!=null)
			{
				nextNode=nextNode.next;
			}
			nextNode.next=tempNode;
			totalEdges++;
			//New Object

			//add to link list
		}

		/*for(int i=0; i<len; i++)
		{
			System.out.println(adList.get(i).name);
			while(adList.get(i).next!=null)
				System.out.println()
		}*/
	while(true){
		System.out.println("");
		System.out.println("1 - Distances and Prices");
		System.out.println("2 - Min Span Tree");
		System.out.println("3 - Short path on miles");
		System.out.println("4 - Short path on price");
		System.out.println("5 - Shorted path on hops");
		System.out.println("6 - All paths that cost less than: ");
		System.out.println("7 - add");
		System.out.println("8 - remove");
		System.out.println("9 - exit");




		int input = Integer.parseInt(sc.next());
		switch(input){

		case 1:
			showGraph(adList);
			break;
		case 2: 
			mst(adList, len);
			break;
		case 3:
			ewdg(adList, len);
			break;
		case 4:
			ewdgCost(adList, len);
			break;
		case 5:
			mstHops(adList,len, totalEdges);
			break;
		case 6:
			rec(adList,len);
			break;
		case 7:
			addRoute(adList,file);
			len = adList.size();
			break;
		case 8:
			removeRoute(adList,file);
			len = adList.size();
			break;

		case 9:
			System.exit(0);
			break;

		}
	}
}

public static void rec(LinkedList<Node> adList, int len)
{
	Scanner scanPrice = new Scanner(System.in);

	System.out.print("Enter the price: ");
	double price = scanPrice.nextDouble();

	StringBuilder build = new StringBuilder();
	boolean[] beenTo = new boolean[len];

	EdgeWeightedGraph priceRec = new EdgeWeightedGraph(1, len, adList);

	System.out.println("---------------------------------------------");
	System.out.println("ALL PATHS OF COST " + price+ " OR LESS");
	System.out.println("Note duplicate paths are duplicated from one city's end point to anothers");
	System.out.println("---------------------------------------------");

	for(int v = 0; v< len; v++){
		build = build.append(adList.get(v).name);
		beenTo[v]=true;
		recursionForRec(v, price, priceRec, build, beenTo, len, adList);
		
		for(int i=0;i<len; i++)
		{
			beenTo[i]=false;
		}
		build.delete(0, build.length());
	}


}

public static void recursionForRec(int current, double budget, EdgeWeightedGraph graph, StringBuilder buildPath, boolean[] beenTo, int len, LinkedList<Node> adList)
{
		double cost=0;

		Iterable<Edge> edges = graph.adj(current);

		for(Edge e: edges)
		{
			cost = e.cost();
			
			if(budget>=cost && !beenTo[e.other(current)])
			{
				beenTo[e.other(current)]= true;
				String add = " "+ cost+ " " + adList.get(e.other(current)).name;
				buildPath= buildPath.append(add);
				System.out.println(buildPath);

				recursionForRec(e.other(current), budget-cost, graph, buildPath, beenTo, len, adList);
				beenTo[e.other(current)]=false;
				buildPath.delete(buildPath.length()-add.length(), buildPath.length());

			}
			/*else
			{
				buildPath.delete(0,buildPath.length());
			}*/

		}

		//recursionForRec(v, price-cost, graph, buildPath, beenTo, len);
}


public static void mstHops(LinkedList<Node> adList, int len, int numEdges)
{
	
	Scanner ewdgTemp = new Scanner(System.in);
	System.out.println("Please enter your starting city: ");
	String start = ewdgTemp.nextLine();

	System.out.println("Please enter your ending city: ");
	String end = ewdgTemp.nextLine();
	
	boolean startFound = false;
	boolean endFound = false;
	int startCityVal=0;
	int endCityVal=0;


	System.out.println("-----------------------------------------------");
	System.out.println("FEWEST HOPS from " + start + " to " + end);
	System.out.println("-----------------------------------------------");

	while((startFound==false) && (endFound==false)){
	for(int i =0; i < len; i++)
	{
		if(adList.get(i).name.equalsIgnoreCase(start))
		{
			startCityVal = i;
			startFound=true;

		}
		if(adList.get(i).name.equalsIgnoreCase(end))
		{
			endCityVal= i;
			endFound=true;

		}
	}

	}

	//System.out.println(endCityVal);
	//System.out.println(startCityVal);
	//System.out.println(len);
	//System.out.println(numEdges);

	Graph distGraph = new Graph(len, adList, numEdges);

	BreadthFirstPaths leastAmtOfHops = new BreadthFirstPaths(distGraph, startCityVal);

	System.out.println("Fewest hops from " + start + " to " + end + " is " + leastAmtOfHops.distTo(endCityVal));
	System.out.println("Path: ");

	int city;
	int finish1;
	boolean first=true;

	Iterable leastH = leastAmtOfHops.pathTo(endCityVal);
	Iterator<Integer> hop = leastH.iterator();

	while(hop.hasNext())
	{
		city=hop.next();
		System.out.print(adList.get(city).name + " ");
	}



}


public static void ewdgCost(LinkedList<Node> adList, int len)
{
	Scanner ewdgTemp = new Scanner(System.in);
	System.out.println("Please enter your starting city: ");
	String start = ewdgTemp.nextLine();

	System.out.println("Please enter your ending city: ");
	String end = ewdgTemp.nextLine();

	System.out.println("-----------------------------------------------");
	System.out.println("SHORTEST COST PATH from" + start + " to " + end);
	System.out.println("-----------------------------------------------");

	boolean startFound = false;
	boolean endFound = false;
	int startCityVal=0;
	int endCityVal=0;

while((startFound==false) && (endFound==false)){
	for(int i =0; i < len; i++)
	{
		if(adList.get(i).name.equalsIgnoreCase(start))
		{
			startCityVal = i;
			startFound=true;

		}
		if(adList.get(i).name.equalsIgnoreCase(end))
		{
			endCityVal= i;
			endFound=true;

		}
	}

	}


	EdgeWeightedDigraph distGraph = new EdgeWeightedDigraph(1, len, adList);

	int miles = 0;
	DijkstraSP mst = new DijkstraSP(distGraph, startCityVal);

	System.out.println("Shortest cost from " + start + " to " + end + " is " + mst.distTo(endCityVal));
	System.out.println("Path with edges: ");

	int cost1;
	int start1;
	int finish1;
	boolean first=true;

	Iterable shortP = mst.pathTo(endCityVal);
	Iterator<DirectedEdge> dEdge = shortP.iterator();

	while(dEdge.hasNext())
	{
		DirectedEdge tempEdge= dEdge.next();
		cost1 = (int) tempEdge.weight();
		start1 = tempEdge.from();
		finish1= tempEdge.to();

		if(first)
		{
			System.out.print(adList.get(start1).name+ " " + cost1 + " " + adList.get(finish1).name);
			first= false;
		}
		else
		System.out.print(" " + cost1 + " " + adList.get(finish1).name);
	}
	System.out.println();


	//System.out.println(mst.weight());

}



public static void ewdg(LinkedList<Node> adList, int len)
{
	Scanner ewdgTemp = new Scanner(System.in);
	System.out.println("Please enter your starting city: ");
	String start = ewdgTemp.nextLine();

	System.out.println("Please enter your ending city: ");
	String end = ewdgTemp.nextLine();

	boolean startFound = false;
	boolean endFound = false;
	int startCityVal=0;
	int endCityVal=0;

	System.out.println("-----------------------------------------------");
	System.out.println("SHORTEST DISTANCE PATH from " + start + " to " + end);
	System.out.println("-----------------------------------------------");


while((startFound==false) && (endFound==false)){
	for(int i =0; i < len; i++)
	{
		if(adList.get(i).name.equalsIgnoreCase(start))
		{
			startCityVal = i;
			startFound=true;

		}
		if(adList.get(i).name.equalsIgnoreCase(end))
		{
			endCityVal= i;
			endFound=true;

		}
	}

	}


	EdgeWeightedDigraph distGraph = new EdgeWeightedDigraph(0, len, adList);

	int miles = 0;
	DijkstraSP mst = new DijkstraSP(distGraph, startCityVal);

	System.out.println("Shortest distance from " + start + " to " + end + " is " + mst.distTo(endCityVal));
	
	int miles1;
	int start1;
	int finish1;
	boolean first=true;

	Iterable shortP = mst.pathTo(endCityVal);
	Iterator<DirectedEdge> dEdge = shortP.iterator();

	while(dEdge.hasNext())
	{
		DirectedEdge tempEdge= dEdge.next();
		miles1 = (int) tempEdge.weight();
		start1 = tempEdge.from();
		finish1= tempEdge.to();

		if(first)
		{
			System.out.print(adList.get(start1).name+ " " + miles1 + " " + adList.get(finish1).name);
			first= false;
		}
		else
		System.out.print(" " + miles1 + " " + adList.get(finish1).name);
	}
	System.out.println();




	//System.out.println(mst.weight());

}



public static void removeRoute(LinkedList<Node> adList, String file)throws IOException
{
	BufferedReader br= new BufferedReader(new FileReader(file));
	
	File tmp = File.createTempFile("tmp", "");
	PrintWriter pw = new PrintWriter(tmp);
	
	int startCity=0;
	int endCity=0;
	
	Scanner fc = new Scanner(System.in);

	int size = Integer.parseInt(br.readLine());
	pw.println(size);
	
	for(int i = 0; i <size; i ++)
		pw.println(br.readLine());

	System.out.println("Enter the start city (int) : ");
	startCity= fc.nextInt();
	System.out.println("Enter the end city (int) : ");
	endCity=fc.nextInt();

	String line= null;

	int curStart=0;
	int curDest=0;
	int countEdges=0;
	while((line = br.readLine()) !=null)
	{
		countEdges++;
		if(line.substring(0,2).contains(" "))
		{
			curStart=Integer.parseInt(line.substring(0,1));

			if(line.substring(2,4).contains(" "))
				curDest=Integer.parseInt(line.substring(2,3));
			else
				curDest=Integer.parseInt(line.substring(2,4));
		}
		else{
		curStart=Integer.parseInt(line.substring(0,2));
			if(line.substring(3,5).contains(" "))
				curDest=Integer.parseInt(line.substring(3,4));
			else
				curDest=Integer.parseInt(line.substring(3,5));
		}

		if(((curStart!=startCity) && (endCity != curDest)) || ((curStart!= startCity) && (endCity== curDest)) || ((curStart==startCity) && (endCity!= curDest)))
			if(countEdges<totalEdges)
			pw.println(line);
			else
			pw.print(line);


	}
	
	pw.close();
	br.close();
	File oldFile=new File(file);
	oldFile.delete();
    tmp.renameTo(oldFile);

    Node startNode = adList.get(startCity-1);
    Node previousNode = startNode;
    Node curNode = startNode.next;

	boolean tester = false;

    while(curNode.next !=null){
    	if(curNode.e.end()==endCity){
    		tester=true;
    		break;
    	}
    	else{
    		previousNode=previousNode.next;
    		curNode=curNode.next;
    	}
    }

    if(tester)
   	{
   		previousNode.next=previousNode.next.next;
   		curNode=null;
   	}
   	else{
    curNode=null;
	}
}


public static void addRoute(LinkedList<Node> adList, String file) throws IOException 
{
	int startCity=0;
	int endCity=0;
	int miles= 0;
	double cost=0;
	Scanner fc = new Scanner(System.in);

	FileWriter pw = new FileWriter(file, true);
	System.out.println("Enter the start city (int) : ");
	startCity= fc.nextInt();
	System.out.println("Enter the end city (int): ");
	endCity=fc.nextInt();
	System.out.println("Enter the miles: ");
	miles = Integer.parseInt(fc.next());
	System.out.println("Enter the cost: ");
	cost = Double.parseDouble(fc.next());
	pw.write("\n"+ startCity + " " + endCity + " "+ miles + " " + cost );

	pw.close();

	Node tempNode = adList.get(startCity-1);
	while(tempNode.next!=null)
	{
		tempNode= tempNode.next;
	}
	Edge currEdge = new Edge(startCity,endCity,miles,cost);
	Node newNode = new Node(currEdge);
	tempNode.next=newNode;
}

public static void mst(LinkedList<Node> adList, int len)
{

	System.out.println("----------------------");
	System.out.println("MINIMUM SPANNING TREE:");
	System.out.println("----------------------");

	EdgeWeightedGraph distGraph = new EdgeWeightedGraph(0, len, adList);

	String startCity = null;
	String endCity = null;
	int miles = 0;
	PrimMST mst = new PrimMST(distGraph);

	Iterable<Edge> edge = mst.edges();
	Iterator<Edge> edgeIt = edge.iterator();

	while(edgeIt.hasNext()){
		Edge tempEdge=edgeIt.next();
		Node currNode = new Node(tempEdge);
		startCity=adList.get(currNode.e.end()).name;
		endCity=adList.get(currNode.e.start()).name;
		miles = currNode.e.miles();
		System.out.println(startCity+ ", " + endCity+ ", " + miles);

	}


	//System.out.println(mst.weight());

}

public static void showGraph(LinkedList<Node> adList)
		{
			String startCity;
			String endCity;
			double cost;
			int weight;

			System.out.println("----------------------");
			System.out.println("ALL ROUTES:");
			System.out.println("----------------------");


			for(int i = 0; i< adList.size(); i++)
			{
				Node currNode = adList.get(i);
				startCity=currNode.name;


				while(currNode.next!=null)
				{	
					currNode = currNode.next;
					int numCity = currNode.e.end();
					endCity=adList.get(numCity-1).name;
					weight = currNode.e.miles();
					cost = currNode.e.cost();
					System.out.println(startCity +" to " + endCity + " is " + weight + " miles and costs $" + cost);
				}
				//System.exit(0);
			}	


		}

}