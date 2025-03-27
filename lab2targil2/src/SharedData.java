

public class SharedData 
{
	private int [] array;
	private boolean [] winArray;
	private boolean flag;
	private final int b;
	
	/** the function SharedData is sharing data.
	 * @param array of integers. 
	 * ......
	 * @param b a value of intger
	 * @return jkbkjjkbl
	 */
	public SharedData(int[] array, int b) {
		
		this.array = array;
		this.b = b;
	}

	public boolean[] getWinArray() 
	{
		return winArray;
	}


	public void setWinArray(boolean [] winArray) 
	{
		this.winArray = winArray;
	}


	public int[] getArray() 
	{
		return array;
	}


	public int getB() 
	{
		return b;
	}
	

	public boolean getFlag() 
	{
		return flag;
	}
	


	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
