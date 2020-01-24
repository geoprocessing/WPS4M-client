package geoprocessing.wps4m.client.topmodel;

import java.util.Calendar;

import geoprocessing.wps4m.client.TimeConverter;
import geoprocessing.wps4m.data.DataQuery;
import geoprocessing.wps4m.data.DataQuery.Observation;
import net.opengis.wps.x20.ExecuteDocument;
import net.opengis.wps.x20.FinishDocument;
import net.opengis.wps.x20.GetStatusDocument;
import net.opengis.wps.x20.StatusInfoDocument;
import net.opengis.wps.x20.StatusInfoDocument.StatusInfo;

public class TOPMODELClient {
	private double rate,recession,tmax,interception,watershedArea,timeStepInSec;
	private String topoIndexl;
	String startTime,endTime;
	private String serviceUrl;
	private static TopmodelRequestTemplate ReqTemplate = TopmodelRequestTemplate.getInstnace();
	
	public TOPMODELClient() {}
	public TOPMODELClient(double rate, double recession,double tmax, double interception, double watersheldarea,String topindex, double timestep,String starttime,String endtime) {
		this.rate = rate;
		this.recession = recession;
		this.tmax = tmax;
		this.interception = interception;
		this.watershedArea = watersheldarea;
		this.topoIndexl = topindex;
		this.startTime = starttime;
		this.endTime = endtime;
		this.timeStepInSec = timestep;
	}
	
	public void setServiceUrl(String url) {
		this.serviceUrl = url;
	}
	
	public String run() throws Exception {
        ExecuteReqInstance instance = new ExecuteReqInstance(this.startTime, this.endTime, timeStepInSec, rate, recession, tmax, interception, watershedArea, topoIndexl);
        ExecuteDocument execDoc = instance.get();
        //ExecuteRequestType execReq = execDoc.getExecute();
        String params = execDoc.xmlText();
        
		String result = HttpUtil.doPost(serviceUrl, params);
		//System.out.println(result);
		        
        StatusInfoDocument stsDoc = StatusInfoDocument.Factory.parse(result);
        StatusInfo stsInfo = stsDoc.getStatusInfo();
        String stsStr = stsInfo.getStatus();
        String jobId = stsInfo.getJobID();
        
        //wait until the status changes to initialized
        GetStatusDocument statusRequestDoc = ReqTemplate.GetStatusDoc();
        do {
        	statusRequestDoc.getGetStatus().setJobID(jobId);
			result = HttpUtil.doPost(serviceUrl, statusRequestDoc.xmlText());
	        stsDoc = StatusInfoDocument.Factory.parse(result);
	        stsInfo = stsDoc.getStatusInfo();
	        stsStr = stsInfo.getStatus();
	        
	        Thread.sleep(10);
	        
	        if(stsStr.equalsIgnoreCase("initialized")) 
	        	break;
	        
	        if(stsStr.equalsIgnoreCase("failed")) 
	        	return null;
	       
		}while (stsStr.equalsIgnoreCase("initializing"));
        
        Calendar currCalendar = TimeConverter.str2Calendar(startTime, TimeConverter.TimeFormat);
        Calendar endCalendar = TimeConverter.str2Calendar(endTime, TimeConverter.TimeFormat);
        DataQuery query = new DataQuery();
        String currTime;
        while (currCalendar.before(endCalendar) || currCalendar.equals(endCalendar)) {
			Observation observ = query.select(currCalendar);
			currTime = TimeConverter.calendar2Str(currCalendar, TimeConverter.TimeFormat);
			PerformStepReqInstance stepInstance = new PerformStepReqInstance(jobId, currTime, observ.getPrecipitation(), observ.getEvapotranspiration());
			System.out.println(stepInstance.get().xmlText());
			HttpUtil.doPost(serviceUrl, stepInstance.get().xmlText());
			currCalendar.add(Calendar.SECOND, (int)timeStepInSec);
		}
        
        /**
        PerformStepReqInstance stepInstance = new PerformStepReqInstance(jobId, "2006-01-01 12:00:00", 0, 0.7178);
        //System.out.println(stepInstance.get().xmlText());
        result = HttpUtil.doPost(serviceUrl, stepInstance.get().xmlText());
        System.out.println(result);
        
        stepInstance = new PerformStepReqInstance(jobId, "2006-01-02 12:00:00", 28.96, 0.5958);
        System.out.println(stepInstance.get().xmlText());
        result = HttpUtil.doPost(serviceUrl, stepInstance.get().xmlText());
        System.out.println(result);
        
        stepInstance = new PerformStepReqInstance(jobId, "2006-01-03 12:00:00", 0, 0.5119);
        System.out.println(stepInstance.get().xmlText());
        result=HttpUtil.doPost(serviceUrl, stepInstance.get().xmlText());
        System.out.println(result);
        **/
        FinishDocument finishReqDoc = ReqTemplate.FinishDoc();
        finishReqDoc.getFinish().setJobID(jobId);
        result = HttpUtil.doPost(serviceUrl, finishReqDoc.xmlText());
        System.out.println(result);
        return result;
	}
	
	public static void main(String[] args) {
		TOPMODELClient client = new TOPMODELClient(9.66, 90, 240000, 3, 124800, "http://localhost:8080/data/TI_raster.txt", 86400, "2006-01-01 12:00:00", "2006-12-31 12:00:00");
		client.setServiceUrl("http://localhost:8080/webapp/service");
		try {
			client.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
