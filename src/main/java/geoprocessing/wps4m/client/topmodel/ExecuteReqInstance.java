package geoprocessing.wps4m.client.topmodel;

import geoprocessing.wps4m.client.DocUtil;
import net.opengis.wps.x20.DataInputType;
import net.opengis.wps.x20.ExecuteDocument;
import net.opengis.wps.x20.ExecuteRequestType;

public class ExecuteReqInstance {

	double rateVal,recessionVal,tmaxVal,interceptionVal,watershedAreaVal,timeStepVal;
	String startTimeVal,endTimeVal,topoIndexVal;
	String rateParam = "Rate",recessionParam="Recession",tmaxParam = "Tmax",interceptionParam = "Interception",watershedAreaParam="WatershedArea",
			startTimeParam = "StartTime",endTimeParam = "EndTime", timeStepParam = "TimeStep",topoIndexParam = "TopoIndex";
	
	private TopmodelRequestTemplate ReqTemplate = TopmodelRequestTemplate.getInstnace();
	public ExecuteReqInstance(String starttime,String endtime,double timestepinsec,double rate,double recession,double tmax, double interception,double waterarea,String topindex) {
		this.startTimeVal = starttime;
		this.endTimeVal = endtime;
		this.timeStepVal = timestepinsec;
		this.rateVal = rate;
		this.recessionVal = recession;
		this.tmaxVal = tmax;
		this.interceptionVal = interception;
		this.watershedAreaVal = waterarea;
		this.topoIndexVal = topindex;
	}
	
	public ExecuteDocument get() {
		ExecuteDocument execDoc = ReqTemplate.NewExecuteDoc();
		
		ExecuteRequestType execReq = execDoc.getExecute();
		DataInputType[] inputTypes =execReq.getInputArray();
		
		DataInputType rateInput = DocUtil.inputByName(rateParam, inputTypes);
		
		DocUtil.SetLiteralValue(rateInput, String.valueOf(rateVal));
		
		DataInputType recessInput = DocUtil.inputByName(recessionParam, inputTypes);
		DocUtil.SetLiteralValue(recessInput, String.valueOf(recessionVal));
		
		DataInputType tmaxInput = DocUtil.inputByName(tmaxParam, inputTypes);
		DocUtil.SetLiteralValue(tmaxInput, String.valueOf(tmaxVal));
		
		DataInputType interceptionInput = DocUtil.inputByName(interceptionParam, inputTypes);
		DocUtil.SetLiteralValue(interceptionInput, String.valueOf(interceptionVal));
		
		DataInputType watershedInput = DocUtil.inputByName(watershedAreaParam, inputTypes);
		DocUtil.SetLiteralValue(watershedInput, String.valueOf(watershedAreaVal));
		
		DataInputType startTimeInput = DocUtil.inputByName(startTimeParam, inputTypes);
		DocUtil.SetLiteralValue(startTimeInput, startTimeVal);
		
		DataInputType endTimeInput = DocUtil.inputByName(endTimeParam, inputTypes);
		DocUtil.SetLiteralValue(endTimeInput, String.valueOf(endTimeVal));
		
		DataInputType timeStepInput = DocUtil.inputByName(timeStepParam, inputTypes);
		DocUtil.SetLiteralValue(timeStepInput, String.valueOf(timeStepVal));
		
		DataInputType topIndexInput = DocUtil.inputByName(topoIndexParam, inputTypes);
		DocUtil.SetReferenceValue(topIndexInput, topoIndexVal);
		return execDoc;
	}
	
	public static void main(String[] args) {
		ExecuteReqInstance instance = new ExecuteReqInstance("2006-06-02 00:00:00", "2009-10-31 00:00:00", 86400, 9.66, 90, 240000, 3, 124800, "http://localhost:8080/data/TI_raster.txt");
		ExecuteDocument execDoc = instance.get();
		System.out.println(execDoc.xmlText());
	}
}
