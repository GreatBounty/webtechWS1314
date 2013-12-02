package models;



public class Mfg_Status extends Entity{
	

	
	public String _mfId;
	
    public String status;
	
	
	public Mfg_Status(){
		
	}
	
    public Mfg_Status(String mfId, String status){
    	this.status = status;
    	this._mfId = mfId;
    	this.IsDeleted = false;
    }
    public Mfg_Status(String mfId){
    	this.status = "neu";
    	this._mfId = mfId;
    }
	

}
