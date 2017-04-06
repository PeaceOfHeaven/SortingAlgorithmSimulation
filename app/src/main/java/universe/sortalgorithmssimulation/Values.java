package universe.sortalgorithmssimulation;

public class Values {
	public int i , j ;
	public int solansosanh,solanhoanvi;
	public String state;
	
	public boolean isRotated = false;
	public int ballIndexertoRotate = 0;
	public float degreeLeft = 0;
	public float degreeRight = 0;
	
	//Selection Sort
	int min = 0;
	
	//Binary and Quick Sort
	boolean MLR = false;
	public int mid = -1;
	public int left = -1;
	public int right = -1;
	
	//Insertion Sort
	public int pos = 0;
	
	
	//Merge Sort
	public int count = 1;
	public boolean isLeft = true;
	public float y;
	
	//public int temp = 0;
	//public int n = 0;
	
	public Values(int i,int j,int solansosanh,int solanhoanvi,String state)
	{
		this.i = i;
		this.j = j;
		this.solanhoanvi = solanhoanvi;
		this.solansosanh = solansosanh;
		this.state = state;
	}
	public void setValues(int i,int j,int solansosanh,int solanhoanvi,String state)
	{
		this.i = i;
		this.j = j;
		this.solanhoanvi = solanhoanvi;
		this.solansosanh = solansosanh;
		this.state = state;
	}
	public void setValues(int i,int mid,int left,int right,int solansosanh,int solanhoanvi,String state)
	{
		this.i = i;
		this.mid = mid;
		this.left = left;
		this.right = right;
		this.solanhoanvi = solanhoanvi;
		this.solansosanh = solansosanh;
		this.state = state;
	}
}
