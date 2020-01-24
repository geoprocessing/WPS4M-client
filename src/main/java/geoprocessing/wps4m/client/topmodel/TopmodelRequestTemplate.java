package geoprocessing.wps4m.client.topmodel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.xmlbeans.XmlException;

import net.opengis.wps.x20.DismissDocument;
import net.opengis.wps.x20.ExecuteDocument;
import net.opengis.wps.x20.FinishDocument;
import net.opengis.wps.x20.GetResultDocument;
import net.opengis.wps.x20.GetStatusDocument;
import net.opengis.wps.x20.PerformStepDocument;

public class TopmodelRequestTemplate {
	private static TopmodelRequestTemplate instance;
	
	private TopmodelRequestTemplate() {}
	private ExecuteDocument executeDoc;
	private PerformStepDocument performStepDoc;
	private GetStatusDocument getStatusDoc;
	private FinishDocument finishDoc;
	private GetResultDocument getResultDoc;
	private DismissDocument dismissDoc;
	
	public static TopmodelRequestTemplate getInstnace() {
		if(instance == null)
			instance = new TopmodelRequestTemplate();
		return instance;
	}
	
	public ExecuteDocument ExecuteDoc() {
		if(executeDoc!=null)
			return executeDoc;
		
		 InputStream is = TopmodelRequestTemplate.class.getClassLoader().getResourceAsStream("Execute.xml");
		try {
			executeDoc = ExecuteDocument.Factory.parse(is);
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	     return executeDoc;
	}
	
	//create a new one
	public ExecuteDocument NewExecuteDoc() {
		if(executeDoc == null)
			ExecuteDoc();
		
		String xmlText = executeDoc.xmlText();
		
		try {
			return ExecuteDocument.Factory.parse(xmlText);
		} catch (XmlException e) {
			e.printStackTrace();
		} 
	     return null;
	}
	
	public PerformStepDocument NewPerformStepDoc() {
		if(performStepDoc==null)
			PerformStepDoc();
		
		String xmlText = this.performStepDoc.xmlText();
		
		try {
			return PerformStepDocument.Factory.parse(xmlText);
		} catch (XmlException e) {
			e.printStackTrace();
		} 
	     return null;
	}
	
	public PerformStepDocument PerformStepDoc() {
		if(performStepDoc!=null)
			return performStepDoc;
		
		 InputStream is = TopmodelRequestTemplate.class.getClassLoader().getResourceAsStream("PerformStep.xml");
		try {
			performStepDoc = PerformStepDocument.Factory.parse(is);
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	     return performStepDoc;
	}
	
	public GetStatusDocument GetStatusDoc() {
		if(getStatusDoc !=null)
			return getStatusDoc;
		
		 InputStream is = TopmodelRequestTemplate.class.getClassLoader().getResourceAsStream("GetStatus.xml");
		try {
			getStatusDoc = GetStatusDocument.Factory.parse(is);
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	     return getStatusDoc;
	}
	
	public FinishDocument FinishDoc() {
		if(finishDoc!=null)
			return finishDoc;
		
		 InputStream is = TopmodelRequestTemplate.class.getClassLoader().getResourceAsStream("Finish.xml");
		try {
			finishDoc = FinishDocument.Factory.parse(is);
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	     return finishDoc;
	}
	
	public GetResultDocument GetResultDoc() {
		if(getResultDoc!=null)
			return getResultDoc;
		
		 InputStream is = TopmodelRequestTemplate.class.getClassLoader().getResourceAsStream("GetResult.xml");
		try {
			getResultDoc = GetResultDocument.Factory.parse(is);
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	     return getResultDoc;
	}
	
	public DismissDocument DismissDoc() {
		if(dismissDoc!=null)
			return dismissDoc;
		
		InputStream is = TopmodelRequestTemplate.class.getClassLoader().getResourceAsStream("Dismiss.xml");
		try {
			dismissDoc = DismissDocument.Factory.parse(is);
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	     return dismissDoc;
	}
}
