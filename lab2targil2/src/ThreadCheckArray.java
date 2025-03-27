/**
 * This class represents a thread that checks if there is a subset in the array
 * whose sum equals a specific target value 'b'.
 * The search is done recursively, and multiple threads may run this check in parallel.
 * 
 * @author DanielAgas
 */
public class ThreadCheckArray implements Runnable 
{
    /**
     * Flag indicating if a valid subset has been found by this thread.
     */
    private boolean flag;
    
    /**
     * Boolean array that indicates which elements are part of the valid subset found.
     */
    private boolean[] winArray;
    
    /**
     * Shared data object used for synchronizing between threads.
     */
    SharedData sd;
    
    /**
     * The array to search for subset sums.
     */
    int[] array;
    
    /**
     * The target sum to search for.
     */
    int b;

    /**
     * Constructs a new ThreadCheckArray with the shared data.
     * Initializes the array and target sum from the shared data object.
     * 
     * @param sd The shared data object containing the array and the target sum.
     */
    public ThreadCheckArray(SharedData sd) 
    {
        this.sd = sd;    
        synchronized (sd) 
        {
            array = sd.getArray();
            b = sd.getB();
        }        
        winArray = new boolean[array.length];
    }

    /**
     * Recursively checks whether a subset of the array up to index n can sum to b.
     * If a valid subset is found, updates the shared flag and the winning array.
     * 
     * @param n Current index in the array to consider.
     * @param b Remaining sum to achieve.
     */
    void rec(int n, int b)
    {
        synchronized (sd) 
        {
            if (sd.getFlag())
                return;
        }    
        if (n == 1)
        {
            if (b == 0 || b == array[n - 1])
            {
                flag = true;
                synchronized (sd) 
                {
                    sd.setFlag(true);
                }            
            }
            if (b == array[n - 1])
                winArray[n - 1] = true;
            return;
        }
        
        rec(n - 1, b - array[n - 1]);
        if (flag)
            winArray[n - 1] = true;
        
        synchronized (sd) 
        {
            if (sd.getFlag())
                return;
        }    
        rec(n - 1, b);
    }

    /**
     * This method is executed when the thread starts.
     * It runs the recursive subset sum check depending on the thread's name.
     * If a valid subset is found, updates the shared win array accordingly.
     */
    public void run() {
        if (array.length != 1)
        {
            if (Thread.currentThread().getName().equals("thread1"))
                rec(array.length - 1, b - array[array.length - 1]);
            else 
                rec(array.length - 1, b);
        }
        if (array.length == 1)
        {
            if (b == array[0] && !flag)
            {
                winArray[0] = true;
                flag = true;
                synchronized (sd) 
                {
                    sd.setFlag(true);
                }
            }
        }
        if (flag)
        {
            if (Thread.currentThread().getName().equals("thread1"))
                winArray[array.length - 1] = true;
            synchronized (sd) 
            {
                sd.setWinArray(winArray);
            }    
        }
    }
}
