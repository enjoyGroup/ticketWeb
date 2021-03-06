package th.go.ticket.app.enjoy.form;

import th.go.ticket.app.enjoy.main.ConfigFile;


public class UploadImageFieldForm {
	
	public final static String FILE_PATH 			= ConfigFile.getFilePath();
	public final static String FILE_NAME 			= ConfigFile.getFileName();
	public final static String FILE_EXT 			= ConfigFile.getFileExt();
	
	private String images;
	
	public UploadImageFieldForm(){
		this.images = "";
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
	
}
