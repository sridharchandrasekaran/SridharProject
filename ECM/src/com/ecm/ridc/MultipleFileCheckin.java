package com.ecm.ridc;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.DataObject;
import oracle.stellent.ridc.model.DataResultSet;
import oracle.stellent.ridc.model.TransferFile;
import oracle.stellent.ridc.protocol.ServiceResponse;


public class MultipleFileCheckin {
		
	
	
	

	
    public MultipleFileCheckin() {
        super();
    }
    
    public void CheckinCustom()
      throws IdcClientException
    {
        
        ServiceResponse myServiceResponse = null;
        try{
            
                      
             Generic_JavaCall file = new Generic_JavaCall();
             file.getProperty();
             file.getClient();
             
             
            
             
              InputStream fileStream = null;
              
              DataBinder myRequestDataBinder =  file.myRequestDataBinder;
              myServiceResponse = file.myServiceResponse;
              IdcClient clientconnection = file.getClient();              
              		try {
              			//String replace = file.folderPath.replaceAll("\", );
              			
              			for(int i=0; i<file.folderPath.split("\\|").length;i++) 
              				{
              						//String replace = file.folderPath.split("\\|")[i];;
              						String fullPath = file.folderPath.split("\\|")[i];
              						String orgFile = fullPath;
              						int index = orgFile.lastIndexOf("\\");
              						String fileName = orgFile.substring(index + 1);
              						String Ofile = fileName.split("\\.")[0];
              						fileStream = new FileInputStream(orgFile);
					                long fileLength = new File(orgFile).length();
					                myRequestDataBinder = clientconnection.createBinder();
					                
					                myRequestDataBinder.putLocal("IdcService", "CHECKIN_UNIVERSAL");
					                myRequestDataBinder.putLocal("dDocType", "Application");
					            // Title of the Uploaded file
					                myRequestDataBinder.putLocal("dDocTitle", Ofile);
					            // Name of Author
					                myRequestDataBinder.putLocal("dDocAuthor", "weblogic");
					            // Security for the content (Group and Account)
					                myRequestDataBinder.putLocal("xIdcProfile", "SridharProject");
					                myRequestDataBinder.putLocal("dSecurityGroup", "Public");
					                myRequestDataBinder.putLocal("dDocAccount", "");
					               
					                myRequestDataBinder.addFile("primaryFile", new TransferFile(fileStream, Ofile, fileLength));
					                myServiceResponse = file.myIdcClient.sendRequest(file.myIdcContext, myRequestDataBinder);
					                
					                 
					                InputStream myInputStream = myServiceResponse.getResponseStream();
					                String myResponseString = myServiceResponse.getResponseAsString();
					                
					                
					              
					                DataBinder myResponseDataBinder = myServiceResponse.getResponseAsBinder();
					              
					                System.out.println("File uploaded successfully"+myResponseDataBinder.getLocalData().get("dDocName"));
              				  		}
              			
					              } catch (IdcClientException idcce) {
					                System.out.println("IDC Client Exception occurred. Unable to upload file. Message: " + idcce.getMessage() + ", Stack trace: ");
					                idcce.printStackTrace();
					              } catch (IOException ioe) {
					                System.out.println("IO Exception occurred. Unable to upload file. Message: " + ioe.getMessage() + ", Stack trace: ");
					                ioe.printStackTrace();
					              } catch (Exception e) {
					                System.out.println("Exception occurred. Unable to upload file. Message: " + e.getMessage() + ", Stack trace: ");
					                e.printStackTrace();
					              } finally {
					                if (myServiceResponse != null) {
					                  myServiceResponse.close();
					                }
					                if (fileStream != null) {
					                  try {
					                    fileStream.close();
					                  }catch(Exception e) {
					                    e.printStackTrace();
					                  }    
					                }
					             }
              					
					             
					        }
					            catch(Exception e){
					            e.printStackTrace();
					        }
					    }
    
    public static void main(String[] args) {
        MultipleFileCheckin s=new MultipleFileCheckin();
        try{
           s.CheckinCustom();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}