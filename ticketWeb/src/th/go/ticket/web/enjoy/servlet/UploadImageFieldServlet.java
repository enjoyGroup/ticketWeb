package th.go.ticket.web.enjoy.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;

import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.UploadImageFieldForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

public class UploadImageFieldServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(UploadImageFieldServlet.class);
	
    private static final String FORM_NAME = "uploadImageFieldForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private UploadImageFieldForm		form						= null;
    
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("[execute][Begin]");
		
         String pageAction = null; 
 		
 		try{
 			 pageAction 				= EnjoyUtil.nullToStr(request.getParameter("pageAction"));
 			 this.enjoyUtil 			= new EnjoyUtil(request, response);
 			 this.request            	= request;
             this.response           	= response;
             this.session            	= request.getSession(false);
             this.userBean           	= (UserDetailsBean)session.getAttribute("userBean");
             this.form               	= (UploadImageFieldForm)session.getAttribute(FORM_NAME);
//             this.dao					= new SeatZoneDao();
 			
             logger.info("[execute][Begin] : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new UploadImageFieldForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/UploadImageFieldScn.jsp");
 			}else if( pageAction.equals("upload")){
 				this.lp_upload();
 			}
 			
 			session.setAttribute(FORM_NAME, this.form);
 			
 		}catch(EnjoyException e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}catch(Exception e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}finally{
 			logger.info("[execute][End]");
 		}
	}
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		String images = null;
		
		try{
			
			images = UploadImageFieldForm.FILE_NAME + "." + UploadImageFieldForm.FILE_EXT;
			
			logger.info("[onLoad] images :: " + images);
			
			this.form.setImages(images);
			
		}catch(Exception e){
			throw new EnjoyException("onLoad :: " + e.getMessage());
		}finally{
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void lp_upload() throws EnjoyException{
		logger.info("[lp_upload][Begin]");
		
		boolean                         isMultipart             = ServletFileUpload.isMultipartContent(this.request);
		List                            items                   = null;
		Iterator                        iterator                = null;
		String                          fileName                = null;
        File                            uploadedFile            = null;
        long                            fileSize                = 0;
        long                            limitSize               = 2048000;//2 MB
        JSONObject 						obj 					= null;
		
		try{
			
			obj 				= new JSONObject();
			
			if (isMultipart) {
                items                   = (List) this.request.getAttribute(Constants.LIST_FILE);
                iterator                = items.iterator();
                logger.info("[lp_upload] items :: " + items.size());
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    
                    logger.info("[lp_upload] item.isFormField() :: " + item.isFormField());
                    
                    if (!item.isFormField()) {
                        fileName                        = new File(item.getName()).getName();
                        fileName                        = UploadImageFieldForm.FILE_NAME + "." + UploadImageFieldForm.FILE_EXT;
                        fileSize                        = item.getSize();
                        
                        if(fileSize > limitSize){
                            throw new EnjoyException("Total size limit 2 MB");
                        }
                        
                        uploadedFile = new File(UploadImageFieldForm.FILE_PATH + "\\" + fileName);
                        item.write(uploadedFile);
                        
                     }
                }
                
                obj.put(STATUS, 			SUCCESS);
                
            }
			
		}catch(EnjoyException e){
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			"Upload Error");
			e.printStackTrace();
			throw new EnjoyException("lp_upload :: " + e.getMessage());
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[lp_upload][End]");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}
