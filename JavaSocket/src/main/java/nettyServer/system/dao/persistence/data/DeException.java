package nettyServer.system.dao.persistence.data;

public class DeException extends Exception {
	
    private static final long serialVersionUID = 1L;

	public DeException(String message){
		super(message);
	}
	
	public DeException(){
		super("");
	}
	

}
