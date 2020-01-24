package geoprocessing.wps4m.client.topmodel;

import geoprocessing.wps4m.client.DocUtil;
import net.opengis.wps.x20.DataInputType;
import net.opengis.wps.x20.PerformStepDocument;
import net.opengis.wps.x20.PerformStepRequestType;

public class PerformStepReqInstance {
	private String timeParam = "Time", valueParam = "Value", preciParam="Precipitation",petParam ="Evapotranspiration";
	private String timeValue,jobId;
	private double preciValue,petValue;
	
	public PerformStepReqInstance(String jobId,String time,double precipitation,double evapotranspiration) {
		this.timeValue = time;
		this.preciValue = precipitation;
		this.petValue = evapotranspiration;
		this.jobId = jobId;
	}
	
	public PerformStepDocument get() {
		PerformStepDocument stepDoc = TopmodelRequestTemplate.getInstnace().NewPerformStepDoc();
		PerformStepRequestType requestType = stepDoc.getPerformStep();
		
		requestType.setJobID(jobId);
		DataInputType[] inputTypes = requestType.getInputArray();
		
		DataInputType preciInput = DocUtil.inputByName(preciParam, inputTypes);
		DataInputType[] preciInputs = preciInput.getInputArray();
		DataInputType preciTimeInput = DocUtil.inputByName(timeParam, preciInputs);
		DocUtil.SetLiteralValue(preciTimeInput, timeValue);
		DataInputType preciValueInput = DocUtil.inputByName(valueParam, preciInputs);
		DocUtil.SetLiteralValue(preciValueInput, String.valueOf(preciValue));
		
		DataInputType petInput = DocUtil.inputByName(petParam, inputTypes);
		DataInputType[] petNestedInputs = petInput.getInputArray();
		DataInputType petTimeInput = DocUtil.inputByName(timeParam, petNestedInputs);
		DocUtil.SetLiteralValue(petTimeInput, timeValue);
		DataInputType petValueInput = DocUtil.inputByName(valueParam, petNestedInputs);
		DocUtil.SetLiteralValue(petValueInput, String.valueOf(petValue));
		
		return stepDoc;
	}
	
	public static void main(String[] args) {
		PerformStepReqInstance instance = new PerformStepReqInstance("jobiddddd", "2012-03-04 12:00:00", 12, 16);
		System.out.println(instance.get().xmlText());
	}
}
