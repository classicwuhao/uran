package examples.nqueen;

public final class ThreadCounter extends Thread{
	
	private NQueenSolver solver;
	private int timeout;
	private	int counter=0;
	public ThreadCounter(NQueenSolver solver, int timeout){
		this.solver = solver;
		this.timeout = (timeout < 30) ? 30 : timeout;
	}

	public void run(){
		try{
			while (counter++<=timeout){
				Thread.sleep(1000);
				if (!solver.isAlive()) this.interrupt();
			}
			if (solver.isAlive()) {
				System.err.println("Timed out.");
				solver.interrupt();
				System.exit(0);
			}
		}
		catch (InterruptedException e){
			
		}
	}


}
