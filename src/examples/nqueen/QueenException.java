/* An NQueen Solver
 * 
 * APR-2016	
 * Written by: Hao Wu
 * bugs report to: haowu@cs.nuim.ie
 */

package examples.nqueen;
import java.lang.RuntimeException;

public final class QueenException extends RuntimeException{
	private String message;
	
	public QueenException(String m){
		message = m;
	}

	public String getMessage(){
		return message;
	}
	
}
